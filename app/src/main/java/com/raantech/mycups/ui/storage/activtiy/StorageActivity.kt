package com.raantech.mycups.ui.storage.activtiy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.databinding.ActivityStorageBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_storage.*

@AndroidEntryPoint
class StorageActivity :
    BaseBindingActivity<ActivityStorageBinding, Nothing>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            layoutResID = R.layout.activity_storage,
            hasToolbar = false
        )
        setStartDestination()
    }

    private fun setStartDestination() {

        val navHostFragment = storage_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.storage_nav_graph)
        graph.startDestination = R.id.storageFragment
        navHostFragment.navController.graph = graph
    }

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, StorageActivity::class.java)
            context?.startActivity(intent)
        }

    }

}