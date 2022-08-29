package com.raantech.mycups.data.repos.media

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.media.MediaRemoteDao
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.data.repos.base.BaseRepo
import okhttp3.MultipartBody
import javax.inject.Inject

class MediaRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val mediaRemoteDao: MediaRemoteDao
) : BaseRepo(responseHandler), MediaRepo {

    override suspend fun getMedia(mime_types: String): APIResource<ResponseWrapper<List<Media>>> {
        return try {
            responseHandle.handleSuccess(mediaRemoteDao.getMedia(mime_types))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun uploadMedia(mediaFile: MultipartBody.Part): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(mediaRemoteDao.uploadMedia(mediaFile))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun deleteMedia(mediaId: Int): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(mediaRemoteDao.deleteMedia(mediaId))
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

}