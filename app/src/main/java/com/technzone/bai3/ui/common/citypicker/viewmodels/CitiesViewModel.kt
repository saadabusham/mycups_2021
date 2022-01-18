package com.technzone.bai3.ui.common.citypicker.viewmodels

import android.content.Context
import androidx.lifecycle.liveData
import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.repos.lookups.LookupsRepo
import com.technzone.bai3.data.repos.user.UserRepo
import com.technzone.bai3.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val lookupsRepo: LookupsRepo,
    @ApplicationContext val context: Context
) : BaseViewModel() {
    var textToSearch: String? = null
    fun getCities(
        pageSize: Int,
        pageNumber: Int
    ) = liveData {
        emit(APIResource.loading())
        val response = lookupsRepo.getCountriesCode(pageSize, pageNumber, textToSearch)
        emit(response)
    }

}