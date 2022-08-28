package com.raantech.mycups.ui.more.contactus.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.databinding.ActivityContactUsBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.base.dialogs.CompletedDialog
import com.raantech.mycups.ui.more.contactus.adapter.ImagesAdapter
import com.raantech.mycups.ui.more.contactus.presenter.ContactUsPresenter
import com.raantech.mycups.ui.more.contactus.viewmodels.ContactUsViewModel
import com.raantech.mycups.utils.ImagePickerUtil
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.extensions.visible
import com.raantech.mycups.utils.pickImages
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsActivity : BaseBindingActivity<ActivityContactUsBinding, ContactUsPresenter>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener, ContactUsPresenter {

    override fun getPresenter() = this
    private val viewModel: ContactUsViewModel by viewModels()
    private lateinit var adapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_contact_us,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            titleString = resources.getString(R.string.menu_customer_support)
        )
        binding?.viewModel = viewModel
        setUpListeners()
        setUpRvImage()
    }

    private fun setUpListeners() {

    }

    override fun onSubmitClicked() {
        if (validateInput()) {
            viewModel.imagesMutableLiveData.clear()
            viewModel.imagesMutableLiveData.addAll(adapter.items.filter { it.isNotEmpty() })
            viewModel.contactUs().observe(this, contactUsObserver())
        }
    }

    override fun onSelectImageClicked() {
        pickImages(
            requestCode = ImagePickerUtil.TAKE_USER_IMAGE_REQUEST_CODE
        )
    }

    override fun onReasonClicked() {

    }

    private fun setUpRvImage() {
        adapter = ImagesAdapter((this))
        binding?.rvData?.adapter = adapter
        binding?.rvData?.setOnItemClickListener(this)
    }

    private fun contactUsObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    showCompletedDialog()
                }
            }
        )
    }

    private fun showCompletedDialog() {
        val completedDialog =
            CompletedDialog(
                context = this,
                title = resources.getString(R.string.submit_successfully)
            )
        completedDialog.setOnDismissListener {
            finish()
        }
        completedDialog.show()

    }

    private fun validateInput(): Boolean {
        binding?.edSubject?.text.toString().validate(ValidatorInputTypesEnums.TEXT, this)
            .let {
                if (!it.isValid) {
                    showErrorAlert(getString(R.string.problem_title), it.errorMessage)
                    return false
                }
            }
        binding?.edMessage?.text.toString().validate(ValidatorInputTypesEnums.TEXT, this)
            .let {
                if (!it.isValid) {
                    showErrorAlert(getString(R.string.problem_description), it.errorMessage)
                    return false
                }
            }
        return true
    }

    private fun validateImages() {
        if (adapter.itemCount == 0) {
            binding?.tvAddImages?.visible()
            binding?.rvData?.gone()
        } else {
            if (adapter.itemCount == 1) {
                adapter.submitItem("")
            }
            binding?.tvAddImages?.gone()
            binding?.rvData?.visible()
        }
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as String
        if (item.isEmpty()) {
            pickImages(
                requestCode = ImagePickerUtil.TAKE_USER_IMAGE_REQUEST_CODE
            )
        } else {
            adapter.removeItemAt(position)
            if (adapter.itemCount == 1) {
                adapter.clear()
                validateImages()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        val fileUri = data?.data
        fileUri?.path?.let {
            adapter.submitItem(it, (if (adapter.itemCount == 0) 1 else adapter.itemCount) - 1)
            validateImages()
        }
    }

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, ContactUsActivity::class.java)
            context?.startActivity(intent)
        }

    }

}