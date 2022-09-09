package com.raantech.mycups.data.repos.user

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.TokenModel
import com.raantech.mycups.data.models.auth.login.User
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.models.notification.Notification
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Query


interface UserRepo {

    suspend fun login(
        userName: String,
        password: String,
        device_token: String,
        platform: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun logout(
        deviceToken: String
    ): APIResource<ResponseWrapper<Any>>

    suspend fun register(
        password: String,
        name: String,
        phoneNumber: String?,
        email: String,
        deviceToken: String,
        platform: String
    ): APIResource<ResponseWrapper<String>>

    suspend fun forgetPassword(
        email: String
    ): APIResource<ResponseWrapper<String>>

    suspend fun verify(
        token: String,
        verificationCode: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun resendCode(
        token: String
    ): APIResource<ResponseWrapper<TokenModel>>

    suspend fun refreshToken(
        refreshToken: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun getProfile(
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun updateProfile(
        name: String,
        email: String,
        phoneNumber: String? = null,
        brandName: String? = null,
        hasStock: Int?,
        addressText: String,
        latitude: Double,
        longitude: Double
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>>

    suspend fun updatePassword(
        oldPassword: String,
        newPassword: String,
        newPasswordConfirmation: String
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

    suspend fun getNotifications(
        skip: Int
    ): APIResource<ResponseWrapper<List<Notification>>>

    suspend fun updateAddress(
        addressText: String,
        latitude: Double,
        longitude: Double,
        name: String,
        hasStock: Int?
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