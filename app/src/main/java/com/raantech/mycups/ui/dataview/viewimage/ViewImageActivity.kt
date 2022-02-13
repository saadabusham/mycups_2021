package com.raantech.mycups.ui.dataview.viewimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.ActivityViewImageBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewImageActivity : BaseBindingActivity<ActivityViewImageBinding,Nothing>() {

    companion object {
        fun start(
                context: Activity,
                item: String,
                image: View
        ) {

            val p1: androidx.core.util.Pair<View, String> = androidx.core.util.Pair(
                    image,
                    image.transitionName
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    p1
            )
            val intent = Intent(context, ViewImageActivity::class.java)
            intent.putExtra(Constants.BundleData.IMAGE, item)
            context?.startActivity(intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
                R.layout.activity_view_image,
                hasToolbar = false,
                hasTitle = false
        )
        binding?.data = intent.getStringExtra(Constants.BundleData.IMAGE)
    }

}