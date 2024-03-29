package com.raantech.mycups.ui.more.contactus.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.models.media.Media
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.extensions.getRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val moreRepo: CommonRepo
) : BaseViewModel() {

    val titleMutableLiveData: MutableLiveData<String> = MutableLiveData("")
    val descriptionMutableLiveData: MutableLiveData<String> = MutableLiveData("")
    val imagesMutableLiveData: MutableList<Media> = mutableListOf()

    fun contactUs() = liveData {
        emit(APIResource.loading())
        val params: HashMap<String, Int> = HashMap();
        imagesMutableLiveData.forEachIndexed { index, media ->
            media.id?.let {
                params["files[attachments][${index}]"] = it
            }
        }
        val response = moreRepo.contactUs(
            title = titleMutableLiveData.value.toString(),
            description = descriptionMutableLiveData.value.toString(),
            params,
            images = imagesMutableLiveData.map { it.id }
        )
        emit(response)
    }
}