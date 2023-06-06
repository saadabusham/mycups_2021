package com.raantech.mycups.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.NavHostFragment
import com.raantech.mycups.R
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.databinding.ActivityAuthBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseBindingActivity<ActivityAuthBinding,Nothing>() {

    @Inject lateinit var prefs : UserPref

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.nothing, R.anim.slide_in_bottom);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom)
        setContentView(R.layout.activity_auth, hasToolbar = false)
        setStartDestination()
    }

    private fun setStartDestination() {

        val navHostFragment = auth_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.auth_nav_graph)

        if (prefs.getIsFirstOpen()) {
            graph.startDestination = R.id.loginFragment
        } else {
            graph.startDestination = R.id.loginFragment
        }

        navHostFragment.navController.graph = graph
    }

    companion object {

        const val REQUEST_CODE = 2
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, AuthActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
            (context as Activity).finish()
        }
        fun startForResult(
            context: Activity?,
            isActivityResult: Boolean,
            resultLauncher: ActivityResultLauncher<Intent>? = null
        ) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.putExtra(Constants.BundleData.IS_ACTIVITY_RESULT, isActivityResult)
            resultLauncher?.launch(intent)
//            context?.startActivityForResult(intent, REQUEST_CODE)
//            val intent = Intent(context, AuthActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//            context?.startActivity(intent)
        }
    }
}