package com.raantech.mycups.data.repos.lookups

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.lookups.LookupsRemoteDao
import com.raantech.mycups.data.models.general.Countries
import com.raantech.mycups.data.models.general.ListWrapper
import com.raantech.mycups.data.repos.base.BaseRepo
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