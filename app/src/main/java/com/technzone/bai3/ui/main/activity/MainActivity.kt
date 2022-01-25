package com.technzone.bai3.ui.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.technzone.bai3.R
import com.technzone.bai3.common.MyApplication
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.enums.NavigationTabsEnum
import com.technzone.bai3.databinding.ActivityMainBinding
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import com.technzone.bai3.ui.checkout.viewmodels.CheckoutViewModel
import com.technzone.bai3.ui.main.fragments.favorites.viewmodels.FavoritesViewModel
import com.technzone.bai3.ui.main.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val favoriteViewModel: FavoritesViewModel by viewModels()
    private val checkoutViewModel: CheckoutViewModel by viewModels()
    var loginCallBack: LoginCallBack? = null
    var navController: NavController? = null

    override fun onResume() {
        super.onResume()
        loadCarts()
        loadFavorites()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main, hasToolbar = false)
        setUpBinding()
        setupNavigation()
        setUpListeners()
//        updateFcmToken()
        handleNewNotifications()
        checkDeepLink()
        handleNotifications()
    }

    private fun setUpBinding(){
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.fabBuy?.setOnClickListener {
            navController?.navigate(R.id.nav_buy)
        }
    }

    private fun updateFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            viewModel.updateRegId(task.result ?: "").observe(this, updateFcmResultObserver())
        })
    }

    private fun updateFcmResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                }
            }, showError = false
        )
    }

    private fun handleNotifications() {
        if (intent?.getBooleanExtra(EXTRA_FROM_NOTIFICATION, false) == true) {
            when (intent?.getStringExtra(NOTIFICATION_TYPE)) {

            }
        }
    }

    private fun checkDeepLink() {
        if (MyApplication.instance.deeplink_id.isNotEmpty()) {
            val id = MyApplication.instance.deeplink_id.toIntOrNull()
            MyApplication.instance.deeplink_id = ""
            id?.let {
//                ProductDetailsActivity.start(this, it)
            }
        }
    }

    private fun loadFavorites() {
        if (viewModel.isUserLoggedIn())
            favoriteViewModel.getFavoritesIds().observe(this, favoriteIdsResultObserver())
    }

    private fun loadCarts() {
        if (viewModel.isUserLoggedIn())
            checkoutViewModel.getCartProductsIds().observe(this, cartIdsResultObserver())
    }

    private fun favoriteIdsResultObserver(): CustomObserverResponse<List<Int>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Int>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Int>?
                ) {
                    data?.let { favoriteViewModel.setFavoriteList(it) }
                }
            }, showError = false
        )
    }

    private fun cartIdsResultObserver(): CustomObserverResponse<List<Int>> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<List<Int>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: List<Int>?
                ) {
                    if (data != null || data?.isEmpty() == true) {
                        checkoutViewModel.setCartList(data)
                    } else {
                        checkoutViewModel.setCartList(mutableListOf())
                    }
                    viewModel.getCartsCount()
                }
            }, showError = false
        )
    }

    private fun setupNavigation() {
        binding?.bnvMain?.itemIconTintList = null
        navController = findNavController(R.id.main_nav_host_fragment)
        navController?.saveState()
//        navController?.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.nav_home -> {
////                    babMain.show()
////                    fabBook.show()
//                }
//                R.id.nav_favorites -> {
////                    babMain.show()
////                    fabBook.show()
//                }
//                R.id.nav_buy -> {
////                    babMain.show()
////                    fabBook.show()
//                }
//                R.id.nav_chat -> {
////                    babMain.show()
////                    fabBook.show()
//                }
//                else -> {
////                    babMain.invisible()
////                    fabBook.hide()
//                }
//            }
//        }

        binding?.bnvMain?.let {
            navController?.let { navController ->
                NavigationUI.setupWithNavController(
                    it,
                    navController
                )
            }
            it.setOnNavigationItemReselectedListener {
                // Do Nothing To Disable ReLunch fragment when reClick on nav icon
            }
            it.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_home -> {
                        viewModel.selectedTab.postValue(NavigationTabsEnum.HOME)
                        navController?.navigate(R.id.nav_home)
                    }
                    R.id.nav_favorites -> {
                        viewModel.selectedTab.postValue(NavigationTabsEnum.FAVORITES)
                        navController?.navigate(R.id.nav_favorites)
                    }
                    R.id.nav_chat -> {
                        viewModel.selectedTab.postValue(NavigationTabsEnum.CHART)
                        navController?.navigate(R.id.nav_chat)
                    }
                    else -> {
                        viewModel.selectedTab.postValue(NavigationTabsEnum.MORE)
                        navController?.navigate(R.id.nav_profile)
                    }
                }
                return@setOnNavigationItemSelectedListener true
            }
        }
//            it.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
//                when (it.itemId) {
//                    R.id.nav_home -> {
//                        navController?.navigate(R.id.nav_home)
//                    }
//                    R.id.nav_favorites -> {
//                        navController?.navigate(R.id.nav_favorites)
//                    }
//                    R.id.nav_chat -> {
//                        navController?.navigate(R.id.nav_chat)
//                    }
//                    R.id.nav_profile -> {
//                        navController?.navigate(R.id.nav_profile)
//                    }
//                }
//                return@OnNavigationItemSelectedListener true
//            })
//        }
        setStartDestination()
    }

    private fun setStartDestination() {
        val navHostFragment = main_nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav_graph)
        graph.startDestination = R.id.nav_home
        navHostFragment.navController.graph = graph
    }

    private fun handleNewNotifications() {
        if (intent?.getBooleanExtra(EXTRA_FROM_NOTIFICATION, false) == true) {
            when (intent?.getStringExtra(NOTIFICATION_TYPE)?.toIntOrNull()) {
//                NotificationsEnum.NEW_ORDER.value,
//                NotificationsEnum.ORDER_CONFIRMATION.value,
//                NotificationsEnum.OUT_FOR_DELIVERY.value,
//                NotificationsEnum.ORDER_CANCELED.value,
//                NotificationsEnum.ORDER_COMPLETED.value -> {
//                    OrderDetailsActivity.start(
//                        this,
//                        intent.getStringExtra(NOTIFICATION_DATA)?.toIntOrNull() ?: -1
//                    )
//                }
//                NotificationsEnum.NEW_MESSAGE.value -> {
//
//                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.getBooleanExtra(Constants.BundleData.IS_LOGIN_SUCCESS, false) == true)
            loginCallBack?.loggedInSuccess()
    }

    companion object {

        const val EXTRA_FROM_NOTIFICATION = "notifications"
        const val NOTIFICATION_TYPE = "notificationsType"
        const val NOTIFICATION_DATA = "notificationsData"
        fun start(
            context: Context?,
            extraFromNotification: Boolean = false,
            type: String = "",
            data: String = ""
        ) {
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_FROM_NOTIFICATION, extraFromNotification)
                putExtra(NOTIFICATION_TYPE, type)
                putExtra(NOTIFICATION_DATA, data)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
        }
    }

}