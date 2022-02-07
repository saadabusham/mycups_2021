package com.technzone.bai3.ui.auth.language

import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.CommonEnums
import com.technzone.bai3.databinding.FragmentLanguageBinding
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : BaseBindingFragment<FragmentLanguageBinding,LanguagePresenter>(),LanguagePresenter {

    private val viewModel: LanguageViewModel by navGraphViewModels(R.id.auth_nav_graph) { defaultViewModelProviderFactory }

    override fun getLayoutId(): Int = R.layout.fragment_language

    override fun getPresenter(): LanguagePresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    override fun onContinueClicked() {
        viewModel.saveLanguage().observe(viewLifecycleOwner, Observer {
            activity?.let {
                (it as BaseBindingActivity<*,*>).setLanguage(if (viewModel.englishSelected.value!!)
                    CommonEnums.Languages.English.value else CommonEnums.Languages.Arabic.value)
                navigationController.navigate(R.id.action_languageFragment_to_onBoardingFragment)
            }
        })
    }
}