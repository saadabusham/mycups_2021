package com.technzone.bai3.ui.main.fragments.home.viewmodels

import androidx.lifecycle.liveData
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val commonRepo: CommonRepo
) : BaseViewModel() {

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn

    fun getCategoriesData() = liveData {
        emit(APIResource.loading())
        val categoriesResponse = commonRepo.getCategories(pageSize = 8, pageNumber = 1)
        emit(categoriesResponse)
    }

}