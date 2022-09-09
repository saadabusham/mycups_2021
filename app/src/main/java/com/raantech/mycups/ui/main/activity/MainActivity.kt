package com.raantech.mycups.ui.main.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.raantech.mycups.R
import com.raantech.mycups.common.CommonEnums
import com.raantech.mycups.common.MyApplication
import com.raantech.mycups.common.interfaces.LoginCallBack
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.MediaTypesEnum
import com.raantech.mycups.data.models.more.More
import com.raantech.mycups.databinding.ActivityMainBinding
import com.raantech.mycups.ui.auth.AuthActivity
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.cart.activity.CartActivity
import com.raantech.mycups.ui.main.adapters.DrawerRecyclerAdapter
import com.raantech.mycups.ui.main.fragments.favorites.viewmodels.FavoritesViewModel
import com.raantech.mycups.ui.main.viewmodels.MainViewModel
import com.raantech.mycups.ui.more.aboutus.AboutUsActivity
import com.raantech.mycups.ui.more.contactus.activity.ContactUsActivity
import com.raantech.mycups.ui.more.media.MediaActivity
import com.raantech.mycups.ui.more.orders.activtiy.OrdersActivity
import com.raantech.mycups.ui.more.profile.activity.UpdateProfileActivity
import com.raantech.mycups.ui.notifications.activity.NotificationsActivity
import com.raantech.mycups.ui.search.activity.SearchActivity
import com.raantech.mycups.ui.splash.SplashActivity
import com.raantech.mycups.ui.storage.activtiy.StorageActivity
import com.raantech.mycups.utils.LocaleUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding, Nothing>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: MainViewModel by viewModels()
    private val favoriteViewModel: FavoritesViewModel by viewModels()
    var loginCallBack: LoginCallBack? = null
    var navController: NavController? = null

    lateinit var drawerRecyclerAdapter: DrawerRecyclerAdapter

    override fun onResume() {
        super.onResume()
        viewModel.getCartsCount()
        loadFavorites()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main, hasToolbar = false)
        setUpBinding()
        setupNavigation()
        setUpDrawer()
        setUpListeners()
//        updateFcmToken()
        handleNewNotifications()
        checkDeepLink()
        handleNotifications()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.appBarMain?.layoutToolbar?.imgCart?.setOnClickListener {
            if (!viewModel.cartCount.value.equals("0")) {
                CartActivity.start(this)
            }
        }
        binding?.appBarMain?.layoutToolbar?.imgSearch?.setOnClickListener {
            SearchActivity.start(this)
        }
        binding?.appBarMain?.layoutToolbar?.imgNotifications?.setOnClickListener {
            NotificationsActivity.start(this)
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

    private fun setupNavigation() {
        navController = findNavController(R.id.main_nav_host_fragment)
    }


    private fun setUpDrawer() {
        drawerRecyclerAdapter = DrawerRecyclerAdapter(this)
        drawerRecyclerAdapter.submitItems(getDrawerList())
        binding?.drawerRecyclerView?.adapter = drawerRecyclerAdapter
        binding?.drawerRecyclerView?.setOnItemClickListener(this)
        val toggle = ActionBarDrawerToggle(
            this, binding?.drawerLayout, binding?.appBarMain?.layoutToolbar?.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        initDrawer(toggle)
    }

    private fun initDrawer(toggle: ActionBarDrawerToggle) {
        val drawable = ResourcesCompat.getDrawable(
            resources, R.drawable.ic_menu,
            theme
        )
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(drawable)
        binding?.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        toggle.toolbarNavigationClickListener = View.OnClickListener { v: View? ->
            if (binding?.drawerLayout?.isDrawerVisible(GravityCompat.START) == true) {
                binding?.drawerLayout?.closeDrawer(GravityCompat.START)
            } else {
                binding?.drawerLayout?.openDrawer(GravityCompat.START)
            }
        }
        binding?.drawerLayout?.setScrimColor(Color.TRANSPARENT)
        binding?.drawerLayout?.drawerElevation = 0.toFloat()
        binding?.drawerLayout?.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawer: View, slideOffset: Float) {
//                if (LocaleUtil.getLanguage() == "ar") {
//                    binding?.appBarMain?.container?.x =
//                        (binding?.navigationView?.width!! * (slideOffset)) * -1
//                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.4f - 1)
//                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.2f - 1)
//                } else {
//                    binding?.appBarMain?.container?.x =
//                        (binding?.navigationView?.width!! * (slideOffset))
//                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.4f - 1)
//                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.2f - 1)
//                }
//                if (LocaleUtil.getLanguage() == "ar") {
//                    binding?.appBarMain?.holder?.rotation = (slideOffset * -1) * 10
//                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.2f - 1)
//                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.2f - 1)
//                    binding?.appBarMain?.container?.pivotX = 0.toFloat()
//                    binding?.appBarMain?.container?.pivotY = (1000).toFloat()
//                } else {
//                    binding?.appBarMain?.container?.x =
//                        (binding?.navigationView?.width!! * (slideOffset))
//                    binding?.appBarMain?.holder?.rotation = slideOffset * 10
//                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.2f - 1)
//                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.2f - 1)
//                    binding?.appBarMain?.container?.pivotX = 0.toFloat()
//                    binding?.appBarMain?.container?.pivotY = (1000).toFloat()
//                }
                if (LocaleUtil.getLanguage() == "ar") {
                    binding?.appBarMain?.container?.x =
                        ((binding?.navigationView?.width!!-400) * (slideOffset)) * -1
                    binding?.appBarMain?.holder?.rotation = (slideOffset * -1) * 10
                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.4f - 1)
                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.33f - 1)
                } else {
                    binding?.appBarMain?.container?.x =
                        ((binding?.navigationView?.width!!-400) * (slideOffset))
                    binding?.appBarMain?.holder?.rotation = slideOffset * 10
                    binding?.appBarMain?.container?.scaleX = abs(slideOffset * 0.4f - 1)
                    binding?.appBarMain?.container?.scaleY = abs(slideOffset * 0.33f - 1)
                }
            }

            override fun onDrawerClosed(drawerView: View) {}
        }
        )
    }


    private fun getDrawerList(): List<More> {
        val list = mutableListOf<More>()
        list.apply {
            addAll(
                arrayListOf(
                    More(resources.getString(R.string.menu_my_orders)),
                    More(resources.getString(R.string.menu_my_designes)),
                    More(resources.getString(R.string.menu_my_storage)),
                    More(resources.getString(R.string.media)),
                    More(resources.getString(R.string.menu_account)),
                    More(resources.getString(R.string.menu_customer_support)),
                    More(resources.getString(R.string.menu_about_us))
                )
            )
            add(
                if (viewModel.isUserLoggedIn())
                    More(resources.getString(R.string.logout))
                else
                    More(resources.getString(R.string.login))
            )
            add(More(resources.getString(R.string.menu_language)))
        }
        return list
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        if (item is More) {
            val loggedIn = viewModel.isUserLoggedIn()
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
            when (position) {
                0 -> {
                    if (!loggedIn) {
                        showLoginDialog()
                    } else {
                        OrdersActivity.start(this)
                    }
                }
                1 -> {
                    if (!loggedIn) {
                        showLoginDialog()
                    } else {
                        MediaActivity.start(this, MediaTypesEnum.DESIGN.value)
                    }
                }
                2 -> {
                    if (!loggedIn) {
                        showLoginDialog()
                    } else {
                        StorageActivity.start(this)
                    }
                }
                3 -> {
                    if (!loggedIn) {
                        showLoginDialog()
                    } else {
                        MediaActivity.start(this, MediaTypesEnum.IMAGES.value)
                    }
                }
                4 -> {
                    if (!loggedIn) {
                        showLoginDialog()
                    } else {
                        UpdateProfileActivity.start(this)
                    }
                }
                5 -> {
                    if (!loggedIn) {
                        showLoginDialog()
                    } else {
                        ContactUsActivity.start(this)
                    }
                }
                6 -> AboutUsActivity.start(this)
                7 -> {
                    if (loggedIn)
                        viewModel.logoutRemote().observe(this, logoutResultObserver())
                    else
                        AuthActivity.startForResult(this@MainActivity, true, loginResultLauncher)
                }
                8 -> viewModel.saveLanguage().observe(this, Observer {
                    this.let {
                        (it as BaseBindingActivity<*, *>).setLanguage(
                            if (viewModel.getAppLanguage() == "ar")
                                CommonEnums.Languages.Arabic.value else CommonEnums.Languages.English.value
                        )
                    }
                })
            }
        }
    }

    private fun logoutResultObserver(): CustomObserverResponse<Any> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<Any> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: Any?
                ) {
                    viewModel.logoutLocale()
                    SplashActivity.start(this@MainActivity)
                }
            })
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
        if (data?.getBooleanExtra(Constants.BundleData.IS_LOGIN_SUCCESS, false) == true) {
            setUpDrawer()
            loginCallBack?.loggedInSuccess()
        }
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