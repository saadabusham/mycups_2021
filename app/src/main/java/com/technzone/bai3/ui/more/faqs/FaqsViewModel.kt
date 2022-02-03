package com.technzone.bai3.ui.more.faqs

import androidx.lifecycle.liveData
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.repos.common.CommonRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
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