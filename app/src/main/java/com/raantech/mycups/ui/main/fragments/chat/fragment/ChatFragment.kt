package com.raantech.mycups.ui.main.fragments.chat.fragment

import androidx.fragment.app.activityViewModels
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.LoginCallBack
import com.raantech.mycups.databinding.FragmentChatBinding
import com.raantech.mycups.ui.base.fragment.BaseBindingFragment
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.main.fragments.chat.presenter.ChatPresenter
import com.raantech.mycups.ui.main.fragments.chat.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseBindingFragment<FragmentChatBinding, ChatPresenter>(),ChatPresenter, LoginCallBack {

    private val viewModel: ChatViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_chat

    override fun getPresenter(): ChatPresenter = this

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