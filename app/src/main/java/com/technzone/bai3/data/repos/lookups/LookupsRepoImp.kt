package com.technzone.bai3.data.repos.lookups

import com.technzone.bai3.data.api.response.APIResource
import com.technzone.bai3.data.api.response.ResponseHandler
import com.technzone.bai3.data.api.response.ResponseWrapper
import com.technzone.bai3.data.daos.remote.lookups.LookupsRemoteDao
import com.technzone.bai3.data.models.general.Countries
import com.technzone.bai3.data.models.general.ListWrapper
import com.technzone.bai3.data.repos.base.BaseRepo
import javax.inject.Inject

class LookupsRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val lookupsRemoteDao: LookupsRemoteDao
) : BaseRepo(responseHandler), LookupsRepo {

    override suspend fun getCountriesCode(
        pageSize: Int,
        pageNumber: Int,
        name: String?,
        code: String?
    ): APIResource<ResponseWrapper<ListWrapper<Countries>>> {
        return try {
            responseHandle.handleSuccess(
                lookupsRemoteDao.getCountriesCode(
                    pageSize, pageNumber, name, code
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }
}