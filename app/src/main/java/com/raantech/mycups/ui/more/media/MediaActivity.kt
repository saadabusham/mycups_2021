package com.raantech.mycups.ui.more.media

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.paginate.Paginate
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.ImageSelectorType
import com.raantech.mycups.data.enums.MediaTypesEnum
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.databinding.ActivityMediaBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.base.dialogs.selectphoto.SelectPhotoBottomSheet
import com.raantech.mycups.ui.dataview.viewimage.ViewImageActivity
import com.raantech.mycups.ui.more.media.adapters.MediaRecyclerAdapter
import com.raantech.mycups.ui.more.media.viewmodel.MediaViewModel
import com.raantech.mycups.utils.ContentUriUtils.getFilePathFromURI
import com.raantech.mycups.utils.FileUtil.getFileNameFromUri
import com.raantech.mycups.utils.ImagePickerUtil.Companion.TAKE_USER_IMAGE_REQUEST_CODE
import com.raantech.mycups.utils.extensions.*
import com.raantech.mycups.utils.pickImages
import com.raantech.mycups.utils.recycleviewutils.VerticalSpaceDecoration
import com.raantech.mycups.utils.showSelectPhotoSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.row_image_view.view.*
import java.io.File

@AndroidEntryPoint
class MediaActivity : BaseBindingActivity<ActivityMediaBinding, Nothing>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener,
    SelectPhotoBottomSheet.PhotoSelectorCallBack {

    private val viewModel: MediaViewModel by viewModels()

    var selectMedia: Boolean? = null

    private val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var isFinished = false

    private lateinit var mediaAdapter: MediaRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_media,
            hasToolbar = true,
            hasBackButton = true,
            toolbarView = toolbar,
            hasTitle = true,
            showBackArrow = true,
            title = R.string.media
        )
        selectMedia = intent.getBooleanExtra(Constants.BundleData.SELECT_MEDIA, false)
        setUpListeners()
        setUpRecyclerView()
        observeLoading()
        loadMedia()
    }

    private fun setUpListeners() {
        binding?.btnAddMedia?.setOnClickListener {
            if (intent.getStringExtra(Constants.BundleData.MEDIA_TYPE) == MediaTypesEnum.ALL.value) {
                showSelectPhotoSheet(
                    requestCode = requestCode,
                    this,
                    ImageSelectorType.ALL
                )
            } else if (intent.getStringExtra(Constants.BundleData.MEDIA_TYPE) == MediaTypesEnum.DESIGN.value) {
                openDocumentPicker(requestCode)
            } else {
                pickImages(
                    requestCode = requestCode
                )
            }
        }
    }

    override fun gallery(requestCode: Int) {
        super.gallery(requestCode)
        pickImages(
            requestCode = requestCode
        )
    }

    override fun camera(requestCode: Int) {
        super.camera(requestCode)
        pickImages(
            requestCode = requestCode
        )
    }

    override fun file(requestCode: Int) {
        super.file(requestCode)
        openDocumentPicker(requestCode)
    }

    private fun openDocumentPicker(requestCode: Int) {
        val intent = Intent("com.sec.android.app.myfiles.PICK_DATA")
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        val mimetypes = arrayOf("*/*")
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.putExtra("CONTENT_TYPE", "*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        frontImageFileResultLauncher.launch(Intent.createChooser(intent, "Choose File"))
    }

    var frontImageFileResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                var realPath = data?.data?.getFilePathFromURI(this)
                File(realPath).name.let {
                    if (it.endsWith(MediaTypesEnum.PDF.value) || it.endsWith(MediaTypesEnum.PSD.value) || it.endsWith(
                            MediaTypesEnum.IL.value
                        )
                    ) {
                        realPath?.let {
                            viewModel.uploadMedia(
                                realPath.createPsdFileMultipart(
                                    "file"
                                )
                            ).observe(this, uploadMediaObserver())
                        }
                    } else {
                        showErrorAlert(
                            title = getString(R.string.select_file),
                            message = getString(R.string.please_choose_file_type_psd)
                        )
                        return@registerForActivityResult
                    }
                }
            }
        }

    private fun uploadMediaObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    mediaAdapter.clear()
                    loadMedia()
                }
            }, showError = false
        )
    }

    private fun deleteMediaObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    mediaAdapter.clear()
                    loadMedia()
                }
            }, showError = false
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            requestCode -> {
                val fileUri = data?.data
                fileUri?.path?.let {
                    viewModel.uploadMedia(it.createImageMultipart("file"))
                        .observe(this, uploadMediaObserver())
                }
            }
        }
    }


    private fun loadMedia() {
        viewModel.getMedia(
            intent.getStringExtra(Constants.BundleData.MEDIA_TYPE) ?: MediaTypesEnum.ALL.value
        ).observe(this, mediaObserver())
    }

    private fun setUpRecyclerView() {
        mediaAdapter = MediaRecyclerAdapter(this)
        binding?.recyclerView?.adapter = mediaAdapter
        binding?.recyclerView?.setOnItemClickListener(this)
        binding?.recyclerView?.addItemDecoration(
            VerticalSpaceDecoration(
                0, resources.getDimension(R.dimen._10sdp).toInt()
            )
        )
        Paginate.with(binding?.recyclerView, object : Paginate.Callbacks {
            override fun onLoadMore() {
                if (loading.value == false && mediaAdapter.itemCount > 0 && !isFinished) {
                    loadMedia()
                }
            }

            override fun isLoading(): Boolean {
                return loading.value ?: false
            }

            override fun hasLoadedAllItems(): Boolean {
                return isFinished
            }

        })
            .setLoadingTriggerThreshold(1)
            .addLoadingListItem(false)
            .build()
    }

    private fun hideShowNoData() {
        if (mediaAdapter.itemCount == 0) {
            binding?.recyclerView?.gone()
            binding?.layoutNoData?.constraintEmptyView?.visible()
        } else {
            binding?.layoutNoData?.constraintEmptyView?.gone()
            binding?.recyclerView?.visible()
        }
    }

    private fun observeLoading() {
        loading.observe(this, Observer {
            if (it) {
                binding?.recyclerView?.gone()
//                binding?.layoutShimmer?.shimmerViewContainer?.visible()
//                binding?.layoutShimmer?.shimmerViewContainer?.startShimmer()
            } else {
//                binding?.layoutShimmer?.shimmerViewContainer?.gone()
//                binding?.layoutShimmer?.shimmerViewContainer?.stopShimmer()
                binding?.recyclerView?.visible()
            }
        })
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as Media
        if (view?.id == R.id.imgRemove) {
            item.id?.let { viewModel.deleteMedia(it).observe(this, deleteMediaObserver()) }
        } else {
            if (selectMedia == true) {
                val data = Intent()
                data.putExtra(Constants.BundleData.MEDIA, item)
                setResult(RESULT_OK, data)
                finish()
            } else {
                view?.imgMedia?.let {
                    item.url?.let { it1 ->
                        ViewImageActivity.start(
                            this,
                            it1,
                            it
                        )
                    }
                }
            }
        }
    }

    override fun onItemLongClick(view: View?, position: Int, item: Any) {
        super.onItemLongClick(view, position, item)
        item as Media
        view?.imgMedia?.let { item.url?.let { it1 -> ViewImageActivity.start(this, it1, it) } }
    }

    private fun mediaObserver(): CustomObserverResponse<List<Media>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Media>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<Media>>?
                ) {
                    isFinished = data?.body?.isNullOrEmpty() == true
                    data?.body?.let {
                        mediaAdapter.addItems(it)
                    }
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    loading.postValue(false)
                    hideShowNoData()
                }

                override fun onLoading() {
                    loading.postValue(true)
                }
            }, true, showError = false
        )
    }

    companion object {
        private const val requestCode: Int = 101
        fun start(
            context: Activity?,
            mediaType: String
        ) {
            val intent = Intent(context, MediaActivity::class.java).apply {
                putExtra(Constants.BundleData.MEDIA_TYPE, mediaType)
            }
            context?.startActivity(intent)
        }

        fun start(
            context: Activity?,
            selectMedia: Boolean? = false,
            mediaType: String,
            resultLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, MediaActivity::class.java)
                .apply {
                    putExtra(Constants.BundleData.MEDIA_TYPE, mediaType)
                    putExtra(Constants.BundleData.SELECT_MEDIA, selectMedia)
                }
            resultLauncher.launch(intent)
        }
    }

}