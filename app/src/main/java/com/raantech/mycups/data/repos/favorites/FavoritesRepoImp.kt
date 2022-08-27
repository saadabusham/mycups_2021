package com.raantech.mycups.data.repos.favorites

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.favorites.FavoritesRemoteDao
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.product.ProductFilter
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.repos.base.BaseRepo
import javax.inject.Inject

class FavoritesRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val favoritesRemoteDao: FavoritesRemoteDao
) : BaseRepo(responseHandler), FavoritesRepo {

    override suspend fun getFavorites(
        productFilter: ProductFilter
    ): APIResource<ResponseWrapper<ListWrapper<Product>>> {
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