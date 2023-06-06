package com.raantech.mycups.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.raantech.mycups.data.enums.ImageSelectorType
import com.raantech.mycups.ui.base.dialogs.selectphoto.SelectPhotoBottomSheet

class ImagePickerUtil {

    companion object {

        const val TAKE_USER_IMAGE_REQUEST_CODE = 11111
    }

}

fun Fragment.pickImages(requestCode: Int) {
    com.github.dhaval2404.imagepicker.ImagePicker.with(this)
        .crop() //Crop image(Optional), Check Customization for more option
        .compress(1024)            //Final image size will be less than 1 MB(Optional)
        .maxResultSize(
            1080,
            1080
        )    //Final image resolution will be less than 1080 x 1080(Optional)

        .start(requestCode)
}

fun Fragment.captureImage(requestCode: Int) {
    com.github.dhaval2404.imagepicker.ImagePicker.with(this)
        .crop() //Crop image(Optional), Check Customization for more option
        .cameraOnly()
        .compress(1024)            //Final image size will be less than 1 MB(Optional)
        .maxResultSize(
            1080,
            1080
        )    //Final image resolution will be less than 1080 x 1080(Optional)
        .start(requestCode)
}

fun Activity.pickImages(requestCode: Int) {
    com.github.dhaval2404.imagepicker.ImagePicker.with(this)
        .crop() //Crop image(Optional), Check Customization for more option
        .compress(1024)            //Final image size will be less than 1 MB(Optional)
        .maxResultSize(
            1080,
            1080
        )    //Final image resolution will be less than 1080 x 1080(Optional)

        .start(requestCode)
}

fun Activity.captureImage(requestCode: Int) {
    com.github.dhaval2404.imagepicker.ImagePicker.with(this)
        .crop() //Crop image(Optional), Check Customization for more option
        .compress(1024)            //Final image size will be less than 1 MB(Optional)
        .maxResultSize(
            1080,
            1080
        )    //Final image resolution will be less than 1080 x 1080(Optional)
        .start(requestCode)
}

fun AppCompatActivity.showSelectPhotoSheet(
    requestCode: Int,
    photoSelectorCallBack: SelectPhotoBottomSheet.PhotoSelectorCallBack,
    imageSelectorType: ImageSelectorType = ImageSelectorType.BOTH
) {
    SelectPhotoBottomSheet(
        requestCode = requestCode,
        photoSelectorCallBack = photoSelectorCallBack,
        imageSelectorType = imageSelectorType
    ).show(
        this.supportFragmentManager,
        "photoSelector"
    )
}

fun Fragment.showSelectPhotoSheet(
    requestCode: Int,
    photoSelectorCallBack: SelectPhotoBottomSheet.PhotoSelectorCallBack,
    imageSelectorType: ImageSelectorType = ImageSelectorType.BOTH
) {
    SelectPhotoBottomSheet(
        requestCode = requestCode,
        photoSelectorCallBack = photoSelectorCallBack,
        imageSelectorType = imageSelectorType
    ).show(
        this.childFragmentManager,
        "photoSelector"
    )
}

