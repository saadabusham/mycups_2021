package com.technzone.baseapp.ui.common.countrypicker.viewmodels

import android.content.Context
import androidx.lifecycle.liveData
import com.technzone.baseapp.data.api.response.APIResource
import com.technzone.baseapp.data.repos.user.UserRepo
import com.technzone.baseapp.ui.base.viewmodel.BaseViewModel
import com.technzone.baseapp.data.repos.lookups.LookupsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val lookupsRepo: LookupsRepo,
    @ApplicationContext val context: Context
) : BaseViewModel() {
    var textToSearch : String? = null
    fun getCountriesCode(
        pageSize: Int,
        pageNumber: Int
    ) = liveData {
        emit(APIResource.loading())
        val response = lookupsRepo.getCountriesCode(pageSize, pageNumber, textToSearch)
        emit(response)
    }

}