package com.raantech.mycups.ui.main.fragments.home.viewmodels

import android.content.Context
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.RequestStatusEnum
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val commonRepo: CommonRepo,
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn
    fun loadHomeData(
    ) = liveData {
        emit(APIResource.loading())
        val list = mutableListOf<Category>()
        val response = commonRepo.getHome()
        if (response.status == RequestStatusEnum.SUCCESS) {
            if (!response.data?.body?.offers.isNullOrEmpty())
                list.add(
                    Category(
                        name = context.getString(R.string.latest_offers),
                        items = response.data?.body?.offers
                    )
                )

            if (!response.data?.body?.categories.isNullOrEmpty())
                list.add(
                    Category(
                        name = context.getString(R.string.categories),
                        items = response.data?.body?.categories
                    )
                )

            if (!response.data?.body?.latestProducts.isNullOrEmpty())
                list.add(
                    Category(
                        name = context.getString(R.string.latest_products),
                        items = response.data?.body?.latestProducts
                    )
                )
            if (!response.data?.body?.latestDesigns.isNullOrEmpty())
                list.add(
                    Category(
                        name = context.getString(R.string.latest_designs),
                        items = response.data?.body?.latestDesigns
                    )
                )
        }
        emit(
            APIResource.success(
                ResponseWrapper(
                    errors = listOf(),
                    message = "",
                    code = 0,
                    body = list
                )
            )
        )
    }

}