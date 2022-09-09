package com.raantech.mycups.ui.base.bindingadapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.raantech.mycups.BuildConfig
import com.raantech.mycups.R
import com.raantech.mycups.data.enums.MediaTypesEnum
import com.raantech.mycups.utils.extensions.px
import java.io.File

@BindingAdapter("ivSetSrcImageFromResources")
fun ImageView.setImageFromResources(@DrawableRes imageRes: Int) {
    setImageResource(imageRes)
}
//@BindingAdapter(
//    value = [
//        "ivImageUrl",
//        "ivImagePlaceholder",
//        "ivImageErrorPlaceholder",
//        "ivImageProgressId",
//        "ivImageIsCircle",
//        "ivImageIsRoundedCorners",
//        "ivImageRoundedRadius",
//        "ivSrcImageFromResources",
//        "ivSrcImageFromResources",
////        "ivSrcImageExtension"
//    ],
//    requireAll = false
//)
//fun ImageView.setImageFromUrl(
//    imageUrl: String?,
//    @DrawableRes imagePlaceholder: Int?,
//    @DrawableRes imageErrorPlaceholder: Int?,
//    @IdRes imageProgressId: Int,
//    imageIsCircle: Boolean = false,
//    imageIsRoundedCorners: Boolean = false,
//    roundingRadius: Int?,
//    @DrawableRes imageRes: Int? = null,
////    extension: String?
//) {
//    if (imageRes != null) {
//        setImageFromResources(imageRes)
//        return
//    }
//    if (imageUrl.isNullOrEmpty()) {
//        setImageResource(imageErrorPlaceholder ?: R.drawable.ic_default_image_place_holder)
//        return
//    }
//
////    if(extension == MediaTypesEnum.PSD.value || extension == MediaTypesEnum.PSD.value || extension == MediaTypesEnum.IL.value){
////
////        return
////    }
//    val progressView: ProgressBar? = findViewById(imageProgressId)
//    progressView?.visibility = View.VISIBLE
//
//    Glide.with(context)
//        .load(getLoadingUrl(imageUrl))
//        .listener(object : RequestListener<Drawable> {
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: Target<Drawable>?,
//                isFirstResource: Boolean
//            ): Boolean {
//                progressView?.visibility = View.GONE
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: Target<Drawable>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean
//            ): Boolean {
//                progressView?.visibility = View.GONE
//                return false
//            }
//        })
//        .placeholder(imagePlaceholder ?: R.drawable.ic_default_image_place_holder)
//        .apply(setUpRequestOptions(imageIsCircle, imageIsRoundedCorners, roundingRadius ?: 10))
//        .error(imageErrorPlaceholder ?: R.drawable.ic_default_image_place_holder)
//        .into(this)
//}

@BindingAdapter(
    value = [
        "ivImageUrl",
        "ivImagePlaceholder",
        "ivImageErrorPlaceholder",
        "ivImageProgressId",
        "ivImageIsCircle",
        "ivImageIsRoundedCorners",
        "ivImageRoundedRadius",
        "ivSrcImageFromResources",
        "ivImageExtension"
    ],
    requireAll = false
)
fun ImageView.setImageFromUrl(
    imageUrl: String?,
    @DrawableRes imagePlaceholder: Int?,
    @DrawableRes imageErrorPlaceholder: Int?,
    @IdRes imageProgressId: Int,
    imageIsCircle: Boolean = false,
    imageIsRoundedCorners: Boolean = false,
    roundingRadius: Int?,
    @DrawableRes imageRes: Int? = null,
    extension: String?
) {
    if (imageRes != null) {
        setImageFromResources(imageRes)
        return
    }
    if (imageUrl.isNullOrEmpty()) {
        setImageResource(imageErrorPlaceholder ?: R.drawable.ic_default_image_place_holder)
        return
    }
    when(extension){
        MediaTypesEnum.PSD.value -> {
            setImageResource(R.drawable.ic_file)
            return
        }
        MediaTypesEnum.PDF.value -> {
            setImageResource(R.drawable.ic_file)
            return
        }
        MediaTypesEnum.IL.value -> {
            setImageResource(R.drawable.ic_file)
            return
        }
    }

    val progressView: ProgressBar? = findViewById(imageProgressId)
    progressView?.visibility = View.VISIBLE

    Glide.with(context)
        .load(getLoadingUrl(imageUrl))
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressView?.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressView?.visibility = View.GONE
                return false
            }
        })
        .placeholder(imagePlaceholder ?: R.drawable.ic_default_image_place_holder)
        .apply(setUpRequestOptions(imageIsCircle, imageIsRoundedCorners, roundingRadius ?: 10))
        .error(imageErrorPlaceholder ?: R.drawable.ic_default_image_place_holder)
        .into(this)
}

fun setUpRequestOptions(
    imageIsCircle: Boolean,
    imageIsRoundedCorners: Boolean,
    roundingRadius: Int
): BaseRequestOptions<*> =
    when {
        imageIsCircle -> RequestOptions.circleCropTransform()
        imageIsRoundedCorners -> RequestOptions().transform(
            CenterCrop(), RoundedCorners(roundingRadius.px())
        )
        else -> RequestOptions.noTransformation()
    }


fun getLoadingUrl(imageUrl: String): Any {

    if (imageUrl.startsWith("http") || imageUrl.startsWith("https")) {
        return imageUrl
    } else if (imageUrl.contains("/storage/")) {
        return "$IMAGES_BASE_URL$imageUrl"
    } else if (imageUrl.contains("storage")) {
        return Uri.fromFile(File(imageUrl))
    } else if (imageUrl.startsWith("content", true)) {
        return Uri.parse(imageUrl)
    }
    return imageUrl
}


@BindingAdapter("imageRec")
fun ImageView?.setImageFromRec(
    @DrawableRes imageRes: Int
) {
    this?.setImageResource(imageRes)
}

const val IMAGES_BASE_URL = BuildConfig.ImageSubUrl
