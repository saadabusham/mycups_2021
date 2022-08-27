package com.raantech.mycups.data.repos.product

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.product.ProductRemoteDao
import com.raantech.mycups.data.models.home.product.productdetails.Product
import com.raantech.mycups.data.models.home.product.productdetails.ProductResponse
import com.raantech.mycups.data.repos.base.BaseRepo
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    responseHandler: ResponseHandler,
    private val remoteDao: ProductRemoteDao
) : BaseRepo(responseHandler), ProductRepo {

    override suspend fun getProductById(productId: Int?): APIResource<ResponseWrapper<ProductResponse>> {
        return try {
            responseHandle.handleSuccess(
                remoteDao.getProductById(productId)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getProductsList(categoryId: Int): APIResource<ResponseWrapper<List<Product>>> {
        return try {
            responseHandle.handleSuccess(
                remoteDao.getProductsList(categoryId)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}