package com.technzone.bai3.ui.main.fragments.chat.fragment

import androidx.fragment.app.activityViewModels
import com.technzone.bai3.R
import com.technzone.bai3.common.interfaces.LoginCallBack
import com.technzone.bai3.databinding.FragmentChatBinding
import com.technzone.bai3.ui.base.fragment.BaseBindingFragment
import com.technzone.bai3.ui.main.activity.MainActivity
import com.technzone.bai3.ui.main.fragments.chat.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseBindingFragment<FragmentChatBinding>(), LoginCallBack {

    private val viewModel: ChatViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.fragment_chat

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