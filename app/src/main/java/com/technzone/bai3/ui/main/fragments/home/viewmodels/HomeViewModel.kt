package com.technzone.bai3.ui.main.fragments.home.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.data.enums.UserEnums
import com.technzone.bai3.data.models.general.City
import com.technzone.bai3.data.models.home.product.productdetails.Ads
import com.technzone.bai3.data.pref.favorite.FavoritePref
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.data.repos.product.ProductRepo
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import com.technzone.bai3.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val commonRepo: CommonRepo,
    private val productRepo: ProductRepo,
    private val favoritePref: FavoritePref
) : BaseViewModel() {

    val selectedCity : MutableLiveData<City> = MutableLiveData(City(name= "Riyadh"))
    fun isUserLoggedIn() = userRepo.getUserStatus() == UserEnums.UserState.LoggedIn

    fun getCategoriesData() = liveData {
        emit(APIResource.loading())
        val categoriesResponse = commonRepo.getCategories(pageSize = 8, pageNumber = 1)
        emit(categoriesResponse)
    }

    fun getBannerData() = liveData {
        emit(APIResource.loading())
        val dealResponse = commonRepo.getBanner(pageSize = 10, pageNumber = 1)
        emit(dealResponse)
    }
    fun loadCategoriesProduct() = liveData {
        emit(APIResource.loading())
        val response = commonRepo.getCategories(200, 1)
        if (response.statusSubCode == ResponseSubErrorsCodeEnum.Success) {
            response.data?.data?.data?.forEach { categoryResponse ->
                categoryResponse.id?.let { it1 ->
                    getCategoriesProducts(it1).let {
                        categoryResponse.ads = it
                    }
                }
            }
        }
        emit(response)
    }
    private suspend fun getCategoriesProducts(categoryId: Int): MutableList<Ads> {
        val map: HashMap<String, String> = HashMap()
        map["PageSize"] = "5"
        map["PageNumber"] = "1"
        map["CountryIds"] = categoryId.toString()
        val response = productRepo.getProductsList(map)
        favoritePref.getFavoriteList().forEach { favId ->
            response.data?.data?.data?.singleOrNull { it.id == favId }
                ?.let { it.favorite = true }
        }
        return response.data?.data?.data?.toMutableList() ?: mutableListOf()
    }

}