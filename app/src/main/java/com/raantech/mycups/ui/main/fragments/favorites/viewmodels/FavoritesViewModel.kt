package com.raantech.mycups.ui.main.fragments.favorites.viewmodels

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.RequestStatusEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.models.home.product.ProductFilter
import com.raantech.mycups.data.pref.favorite.FavoritePref
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.data.repos.favorites.FavoritesRepo
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val commonRepo: CommonRepo,
    private var favoritePref: FavoritePref,
    private val favoritesRepo: FavoritesRepo
) : BaseViewModel() {
    var textToSearch: String? = null
    fun getFavorites(
            pageNumber: Int,
    ) = liveData {
        emit(APIResource.loading())
        val response =
                favoritesRepo.getFavorites(
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
        val response = favoritesRepo.getFavoriteIds()
        emit(response)
    }

    fun addToWishList(
            productId: Int
    ) = liveData {
        emit(APIResource.loading())
        val response =
            favoritesRepo.addFavorite(productId)
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
            favoritesRepo.removeFavorite(id = productId)
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