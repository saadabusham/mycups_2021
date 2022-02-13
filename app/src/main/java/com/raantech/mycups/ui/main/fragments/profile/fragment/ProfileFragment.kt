package com.raantech.mycups.ui.main.fragments.profile.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.LoginCallBack
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.MoreEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.models.profile.More
import com.raantech.mycups.databinding.FragmentProfileBinding
import com.raantech.mycups.ui.auth.AuthActivity
import com.raantech.mycups.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.mycups.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.dataview.dataviewer.DataViewerActivity
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.main.fragments.profile.adapters.MoreRecyclerAdapter
import com.raantech.mycups.ui.main.fragments.profile.presenter.ProfilePresenter
import com.raantech.mycups.ui.main.fragments.profile.viewmodels.ProfileViewModel
import com.raantech.mycups.ui.more.changepassword.ChangePasswordActivity
import com.raantech.mycups.ui.more.faqs.FaqsActivity
import com.raantech.mycups.ui.more.settings.activity.SettingsActivity
import com.raantech.mycups.utils.extensions.openShareView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding, ProfilePresenter>(),
    ProfilePresenter,
    BaseBindingRecyclerViewAdapter.OnItemClickListener, LoginCallBack {

    private val viewModel: ProfileViewModel by activityViewModels()

    private lateinit var accountMoreRecyclerAdapter: MoreRecyclerAdapter
    private lateinit var generalMoreRecyclerAdapter: MoreRecyclerAdapter
    private lateinit var legMoreRecyclerAdapter: MoreRecyclerAdapter
    override fun getLayoutId(): Int = R.layout.fragment_profile
    override fun getPresenter(): ProfilePresenter = this

    override fun onResume() {
        super.onResume()
        viewModel.isUserLoggedIn()?.let {
            viewModel.loggedIn.postValue(it)
            if (it)
                viewModel.getMyProfile().observe(this, profileObserver())
        }
    }

    override fun onViewVisible() {
        super.onViewVisible()
        init()
    }

    private fun init() {
        setUpBinding()
        setUpListeners()
        setUpAccountRecyclerView()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        (requireActivity() as MainActivity).loginCallBack = this
        binding?.layoutNotLoggedIn?.btnLogin?.setOnClickListener {
            AuthActivity.startForResult(requireActivity(), true)
        }
    }

    override fun onLogoutClicked() {
        //            viewModel.logout()
        AuthActivity.start(requireContext())
    }

    private fun setUpAccountRecyclerView() {
        accountMoreRecyclerAdapter = MoreRecyclerAdapter(requireContext())
        binding?.rvAccount?.adapter = accountMoreRecyclerAdapter
        binding?.rvAccount?.setOnItemClickListener(this)
        generalMoreRecyclerAdapter = MoreRecyclerAdapter(requireContext())
        binding?.rvGeneral?.adapter = generalMoreRecyclerAdapter
        binding?.rvGeneral?.setOnItemClickListener(this)
        legMoreRecyclerAdapter = MoreRecyclerAdapter(requireContext())
        binding?.rvLegalInfo?.adapter = legMoreRecyclerAdapter
        binding?.rvLegalInfo?.setOnItemClickListener(this)
        initData()
    }

    private fun profileObserver(): CustomObserverResponse<UserDetailsResponseModel> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<UserDetailsResponseModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: UserDetailsResponseModel?
                ) {
                    data?.let { it1 -> viewModel.storeUser(it1) }
                    viewModel.getUser()
                }
            }, showError = false
        )
    }

    private fun initData() {
        val accountItemList = arrayListOf<More>()
        val generalItemList = arrayListOf<More>()
        val legalItemList = arrayListOf<More>()
        if (viewModel.isUserLoggedIn()) {
            accountItemList.add(
                More(
                    resources.getString(R.string.more_my_ads),
                    R.drawable.ic_more_my_ads,
                    MoreEnums.MY_ADS
                )
            )
        }
        if (viewModel.isUserLoggedIn()) {
            More(
                resources.getString(R.string.more_my_orders),
                R.drawable.ic_more_my_orders,
                MoreEnums.MY_ORDERS
            )
        }
        accountItemList.add(
            More(
                resources.getString(R.string.more_recent_search),
                R.drawable.ic_more_recent_search,
                MoreEnums.RECENT_SEARCH
            )
        )
        if (viewModel.isUserLoggedIn()) {
            generalItemList.add(
                More(
                    resources.getString(R.string.more_change_password),
                    R.drawable.ic_more_change_password,
                    MoreEnums.CHANGE_PASSWORD
                )
            )
        }
        generalItemList.add(
            More(
                resources.getString(R.string.more_settings),
                R.drawable.ic_more_settings,
                MoreEnums.SETTINGS
            )
        )
        generalItemList.add(
            More(
                resources.getString(R.string.more_share_app),
                R.drawable.ic_more_share_app,
                MoreEnums.SHARE_APP
            )
        )
        legalItemList.add(
            More(
                resources.getString(R.string.more_customer_sppoirt),
                R.drawable.ic_more_contact_us,
                MoreEnums.CUSTOMER_SUPPORT
            )
        )

        legalItemList.add(
            More(
                resources.getString(R.string.more_faqs),
                R.drawable.ic_more_faqs,
                MoreEnums.FAQS
            )
        )
        legalItemList.add(
            More(
                resources.getString(R.string.more_privacy_policy),
                R.drawable.ic_more_privacy_policy,
                MoreEnums.PRIVACY_POLICY
            )
        )

        legalItemList.add(
            More(
                resources.getString(R.string.more_terms_and_conditions),
                R.drawable.ic_more_terms_and_conditions,
                MoreEnums.TERMS_AND_CONDITIONS
            )
        )
        accountMoreRecyclerAdapter.submitItems(accountItemList)
        generalMoreRecyclerAdapter.submitItems(generalItemList)
        legMoreRecyclerAdapter.submitItems(legalItemList)
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        item as More
        when (item.moreEnums) {
//            MoreEnums.MY_PROFILE -> {
//                UpdateProfileActivity.start(requireContext())
//            }
//            MoreEnums.MY_ADDRESSES -> {
//                AddressesActivity.start(requireContext())
//            }
//            MoreEnums.MY_FAVORITES -> {
//                FavoritesActivity.start(requireContext())
//            }
            MoreEnums.CHANGE_PASSWORD -> {
                ChangePasswordActivity.start(requireContext())
            }
            MoreEnums.SETTINGS -> {
                SettingsActivity.start(requireContext())
            }
//            MoreEnums.RATE_APP -> {
//                requireActivity().startAppDetailsOnGooglePlay()
//            }
            MoreEnums.SHARE_APP -> {
                requireContext().openShareView(viewModel.getShareText())
            }
            MoreEnums.FAQS -> {
                FaqsActivity.start(requireContext())
            }
//            MoreEnums.CONTACT_US -> {
//                ContactUsActivity.start(requireContext())
//            }
            MoreEnums.TERMS_AND_CONDITIONS -> {
                DataViewerActivity.start(
                    requireContext(),
                    resources.getString(R.string.more_terms_and_conditions),
                    viewModel.getTermsAndConditions()
                )
            }
            MoreEnums.PRIVACY_POLICY -> {
                DataViewerActivity.start(
                    requireContext(),
                    resources.getString(R.string.more_privacy_policy),
                    viewModel.getPrivacyPolicy()
                )
            }
//            MoreEnums.ABOUT_US -> {
//                AboutUsActivity.start(requireContext())
//            }
        }
    }

    override fun loggedInSuccess() {
        accountMoreRecyclerAdapter.clear()
        generalMoreRecyclerAdapter.clear()
        legMoreRecyclerAdapter.clear()
        initData()
    }
}