package com.raantech.mycups.ui.more.faqs

import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.repos.common.CommonRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqsViewModel @Inject constructor(
    private val moreRepo: CommonRepo
) : BaseViewModel() {

    fun getFAQs() = liveData {
        emit(APIResource.loading())
        val response = moreRepo.getFaqs(pageSize = 1000000, pageNumber = 1)
        emit(response)
    }
}