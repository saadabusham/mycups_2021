package com.raantech.mycups.ui.base.dialogs.selectphoto

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raantech.mycups.R
import com.raantech.mycups.data.enums.ImageSelectorType
import com.raantech.mycups.databinding.BottomSheetSelectPhotoBinding
import com.raantech.mycups.utils.extensions.visible


class SelectPhotoBottomSheet(
    private val requestCode: Int,
    private val imageSelectorType: ImageSelectorType = ImageSelectorType.BOTH,
    private val photoSelectorCallBack: PhotoSelectorCallBack
) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetSelectPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onStart() {
        super.onStart()
        view?.post {
            val parent = requireView().parent as View
            val params = (parent).layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.peekHeight = requireView().measuredHeight
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_select_photo, null, false)
        binding.viewModel = this
        isCancelable = true
        setUpData()
        return binding.root
    }

    private fun setUpData() {
        when (imageSelectorType) {
            ImageSelectorType.BOTH -> {
                binding.tvGallery.visible()
                binding.divGallery.visible()
                binding.tvCamera.visible()
                binding.divCamera.visible()
            }
            ImageSelectorType.ALL -> {
                binding.tvGallery.visible()
                binding.divGallery.visible()
                binding.tvCamera.visible()
                binding.divCamera.visible()
                binding.tvFile.visible()
                binding.divFile.visible()
            }
            ImageSelectorType.IMAGE_ONLY -> {
                binding.tvGallery.visible()
                binding.divGallery.visible()
            }
            ImageSelectorType.FILE -> {
                binding.tvFile.visible()
                binding.divFile.visible()
            }
            ImageSelectorType.CAMERA_ONLY -> {
                binding.tvCamera.visible()
                binding.divCamera.visible()
            }
        }
    }


    fun onGalleryClicked() {
        dismiss()
        photoSelectorCallBack.gallery(requestCode)
    }

    fun onCameraClicked() {
        dismiss()
        photoSelectorCallBack.camera(requestCode)
    }

    fun onFileClicked() {
        dismiss()
        photoSelectorCallBack.file(requestCode)
    }

    fun onCancelClicked() {
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        data?.data?.path?.let { photoSelectorCallBack.success(it, this.requestCode) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    interface PhotoSelectorCallBack {
        fun gallery(requestCode: Int) {}
        fun camera(requestCode: Int) {}
        fun file(requestCode: Int) {}
        fun success(path: String, requestCode: Int) {}
    }
}