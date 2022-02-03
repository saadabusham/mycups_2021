package com.technzone.bai3.ui.main.fragments.profile.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.common.CustomObserverResponse
import com.technzone.bai3.data.enums.MoreEnums
import com.technzone.bai3.data.models.auth.login.UserDetailsResponseModel
import com.technzone.bai3.data.models.profile.More
import com.technzone.bai3.databinding.FragmentProfileBinding
import com.technzone.bai3.ui.auth.AuthActivity
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.base.bindingadapters.setOnItemClickListener
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.dataview.dataviewer.DataViewerActivity
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.profile.adapters.MoreRecyclerAdapter
import com.technzone.bai3.ui.main.fragments.profile.viewmodels.ProfileViewModel
import com.technzone.bai3.ui.more.changepassword.ChangePasswordActivity
import com.technzone.bai3.ui.more.faqs.FaqsActivity
import com.technzone.bai3.ui.more.settings.activity.SettingsActivity
import com.technzone.bai3.utils.extensions.openShareView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener, LoginCallBack {

    private val viewModel: ProfileViewModel by activityViewModels()

    private lateinit var accountMoreRecyclerAdapter: MoreRecyclerAdapter
    private lateinit var generalMoreRecyclerAdapter: MoreRecyclerAdapter
    private lateinit var legMoreRecyclerAdapter: MoreRecyclerAdapter
    override fun getLayoutId(): Int = R.layout.fragment_profile

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
        binding?.linLogout?.setOnClickListener {
//            viewModel.logout()
            AuthActivity.start(requireContext())
        }
        binding?.layoutNotLoggedIn?.btnLogin?.setOnClickListener {
            AuthActivity.startForResult(requireActivity(), true)
        }

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