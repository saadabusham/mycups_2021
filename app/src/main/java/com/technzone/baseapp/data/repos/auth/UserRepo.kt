package com.technzone.baseapp.data.repos.auth

import com.technzone.baseapp.data.api.response.APIResource
import com.technzone.baseapp.data.api.response.ResponseWrapper
import com.technzone.baseapp.data.enums.UserEnums
import com.technzone.baseapp.data.models.auth.login.UserDetailsResponseModel


interface UserRepo {


    suspend fun login(
        userName: String,
        password: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun refreshToken(
        refreshToken: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    fun saveNotificationStatus(flag: Boolean)
    fun getNotificationStatus(): Boolean

    fun saveTouchIdStatus(flag: Boolean)
    fun getTouchIdStatus(): Boolean

    fun saveAccessToken(accessToken: String)
    fun getAccessToken(): String

    fun saveUserPassword(password: String)
    fun getUserPassword(): String

    fun setUserStatus(userState: UserEnums.UserState)
    fun getUserStatus(): UserEnums.UserState

    fun setUser(user: UserDetailsResponseModel)
    fun getUser(): UserDetailsResponseModel?
}