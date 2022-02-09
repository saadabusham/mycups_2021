package com.technzone.bai3.data.repos.favorites

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.favorites.FavoritesRemoteDao
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.product.ProductFilter
import com.technzone.bai3.data.models.home.product.productdetails.Ads
import com.technzone.bai3.data.repos.base.BaseRepo
import javax.inject.Inject

class FavoritesRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val favoritesRemoteDao: FavoritesRemoteDao
) : BaseRepo(responseHandler), FavoritesRepo {

    override suspend fun getFavorites(
        productFilter: ProductFilter
    ): APIResource<ResponseWrapper<ListWrapper<Ads>>> {
        return try {
            responseHandle.handleSuccess(
                favoritesRemoteDao.getFavorites(
                    productFilter
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getFavoriteIds(): APIResource<ResponseWrapper<List<Int>>> {
        return try {
            responseHandle.handleSuccess(
                favoritesRemoteDao.getFavoriteIds()
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun addFavorite(id: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                favoritesRemoteDao.addFavorite(id)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun removeFavorite(id: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                favoritesRemoteDao.removeFavorite(id)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}