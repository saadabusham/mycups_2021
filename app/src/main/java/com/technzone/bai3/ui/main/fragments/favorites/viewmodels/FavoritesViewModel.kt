package com.technzone.bai3.ui.main.fragments.favorites.viewmodels

import androidx.lifecycle.liveData
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.RequestStatusEnum
import com.technzone.bai3.data.common.Constants
import com.technzone.bai3.data.models.home.product.ProductFilter
import com.technzone.bai3.data.pref.favorite.FavoritePref
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val commonRepo: CommonRepo,
    private var favoritePref: FavoritePref
) : BaseViewModel() {
    var textToSearch: String? = null
    fun getFavorites(
            pageNumber: Int,
    ) = liveData {
        emit(APIResource.loading())
        val response =
                commonRepo.getFavorites(
                        ProductFilter(
                                pageNumber = pageNumber,
                                pageSize = Constants.PAGE_SIZE,
                                getBookmarked = true,
                                name = textToSearch
                        )
                )
        favoritePref.getFavoriteList().forEach { favId ->
            response.data?.data?.data?.singleOrNull { it.id == favId }?.let { it.favorite = true }
        }
        emit(response)
    }

    fun getFavoritesIds(
    ) = liveData {
        emit(APIResource.loading())
        val response = commonRepo.getFavoriteIds()
        emit(response)
    }

    fun addToWishList(
            productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
                commonRepo.addFavorite(productId)
        if (response.status == RequestStatusEnum.SUCCESS) {
            addFavorite(productId)
        }
        emit(response)
    }

    fun removeFromWishList(
            productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
                commonRepo.removeFavorite(id = productId)
        if (response.status == RequestStatusEnum.SUCCESS) {
            removeFavorite(productId)
        }
        emit(response)
    }

    fun setFavoriteList(list: List<Int>) {
        favoritePref.setFavoriteList(list)
    }

    fun addFavorite(id: Int) {
        favoritePref.addFavorite(id)
    }

    fun removeFavorite(id: Int) {
        favoritePref.removeFavorite(id)
    }

}