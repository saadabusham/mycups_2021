package com.technzone.bai3.data.repos.lookups

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.data.models.general.ListWrapper

interface LookupsRepo {
    suspend fun getCountriesCode(
        pageSize: Int,
        pageNumber: Int,
        name: String? = "",
        code: String? = ""
    ): APIResource<ResponseWrapper<ListWrapper<Countries>>>
}