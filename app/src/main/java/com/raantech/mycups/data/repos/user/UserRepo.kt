package com.raantech.mycups.data.repos.user

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface UserRepo {


    suspend fun login(
        userName: String,
        password: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun register(
        password: String,
        firstName: String,
//        lastName: String,
        phoneNumber: String?,
        email: String,
        registrationId: String,
        deviceType: Int,
        userName: String
    ): APIResource<ResponseWrapper<String>>

    suspend fun forgetPassword(
        email: String
    ): APIResource<ResponseWrapper<String>>

    suspend fun verify(
        userId: String,
        verificationCode: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun resendCode(
        phoneNumber: String
    ): APIResource<ResponseWrapper<String>>

    suspend fun refreshToken(
        refreshToken: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun getProfile(
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun updateProfile(
        firstName: RequestBody,
        lastName: RequestBody,
        phoneNumber: RequestBody
    ): APIResource<ResponseWrapper<Any>>

    suspend fun updatePassword(
        oldPassword: String,
        newPassword: String
    ): APIResource<ResponseWrapper<Any>>

    suspend fun resetPassword(
        newPassword: String
    ): APIResource<ResponseWrapper<Any>>

    suspend fun updateFcmToken(
        registrationId: RequestBody,
        deviceType: RequestBody
    ): APIResource<ResponseWrapper<Any>>

    suspend fun updateProfilePicture(
        image: MultipartBody.Part
    ): APIResource<ResponseWrapper<Any>>

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