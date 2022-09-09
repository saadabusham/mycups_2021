package com.raantech.mycups.data.repos.common

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.common.CommonRemoteDao
import com.raantech.mycups.data.models.FaqsResponse
import com.raantech.mycups.data.models.category.CategoriesResponse
import com.raantech.mycups.data.models.category.Category
import com.raantech.mycups.data.models.category.DesignCategory
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.models.home.banner.Banner
import com.raantech.mycups.data.models.home.homedata.HomeResponse
import com.raantech.mycups.data.models.home.product.productdetails.SocialMedia
import com.raantech.mycups.data.models.notification.Notification
import com.raantech.mycups.data.repos.base.BaseRepo
import okhttp3.RequestBody
import retrofit2.http.FieldMap
import javax.inject.Inject

class CommonRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val commonRemoteDao: CommonRemoteDao
) : BaseRepo(responseHandler), CommonRepo {

    override suspend fun getNotifications(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<Notification>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getNotifications(pageSize, pageNumber)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getFaqs(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<FaqsResponse>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getFaqs(pageSize, pageNumber))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun contactUs(
        title: String,
        description: String,
        @FieldMap params: Map<String, Int>,
        images: List<Int?>?
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.contactUs(title, description, params))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getCategories(
    ): APIResource<ResponseWrapper<List<Category>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getCategories()
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getSubCategories(categoryId: Int): APIResource<ResponseWrapper<CategoriesResponse>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getSubCategories(categoryId)
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getDesignCategories(): APIResource<ResponseWrapper<List<DesignCategory>>> {
        return try {
            responseHandle.handleSuccess(
                commonRemoteDao.getDesignCategories()
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getContactUsSocialMedia(): APIResource<ResponseWrapper<List<SocialMedia>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getContactUsSocialMedia())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getBanner(
        pageSize: Int,
        pageNumber: Int
    ): APIResource<ResponseWrapper<ListWrapper<Banner>>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getBanner(pageSize, pageNumber))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getHome(): APIResource<ResponseWrapper<HomeResponse>> {
        return try {
            responseHandle.handleSuccess(commonRemoteDao.getHome())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}