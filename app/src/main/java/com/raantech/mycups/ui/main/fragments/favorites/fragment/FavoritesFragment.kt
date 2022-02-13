package com.raantech.mycups.ui.main.fragments.favorites.fragment

import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.LoginCallBack
import com.raantech.mycups.databinding.FragmentFavoritesBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.main.fragments.favorites.presenter.FavoritesPresenter
import com.raantech.mycups.ui.main.fragments.favorites.viewmodels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseBindingFragment<FragmentFavoritesBinding, FavoritesPresenter>(),
    FavoritesPresenter, LoginCallBack {

    private val viewModel: FavoritesViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_favorites
    override fun getPresenter(): FavoritesPresenter = this

    override fun onViewVisible() {
        super.onViewVisible()
        setUpBinding()
        setUpListeners()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        (requireActivity() as MainActivity).loginCallBack = this
    }

    override fun loggedInSuccess() {

    }
}