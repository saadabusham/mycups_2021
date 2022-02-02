package com.technzone.bai3.data.repos.product

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.product.ProductRemoteDao
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.models.home.product.productdetails.Product
import com.technzone.bai3.data.repos.base.BaseRepo
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    responseHandler: ResponseHandler,
    private val remoteDao: ProductRemoteDao
) : BaseRepo(responseHandler), ProductRepo {

    override suspend fun getProductById(productId: Int?): APIResource<ResponseWrapper<Product>> {
        return try {
            responseHandle.handleSuccess(
                remoteDao.getProductById(productId)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getProductsList(fields: Map<String, String>): APIResource<ResponseWrapper<ListWrapper<Product>>> {
        return try {
            responseHandle.handleSuccess(
                remoteDao.getProductsList(fields)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}