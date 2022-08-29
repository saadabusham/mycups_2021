package com.raantech.mycups.ui.more.media.viewmodel

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.repos.media.MediaRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    val mediaRepo: MediaRepo
) : BaseViewModel() {

    fun getMedia(mime_types: String) = liveData {
        emit(APIResource.loading())
        val response = mediaRepo.getMedia(mime_types)
        emit(response)
    }

    fun uploadMedia(media: MultipartBody.Part) = liveData {
        emit(APIResource.loading())
        val response = mediaRepo.uploadMedia(media)
        emit(response)
    }

    fun deleteMedia(mediaId: Int) = liveData {
        emit(APIResource.loading())
        val response = mediaRepo.deleteMedia(mediaId)
        emit(response)
    }
}