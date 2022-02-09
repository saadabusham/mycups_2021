package com.technzone.bai3.ui.ads.viewmodels

import android.content.Context
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class AdsDetailsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepo: UserRepo
) : BaseViewModel() {

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn

}