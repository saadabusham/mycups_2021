package com.technzone.bai3.ui.main.fragments.favorites.fragment

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.databinding.FragmentFavoritesBinding
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.favorites.presenter.FavoritesPresenter
import com.technzone.bai3.ui.main.fragments.favorites.viewmodels.FavoritesViewModel
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