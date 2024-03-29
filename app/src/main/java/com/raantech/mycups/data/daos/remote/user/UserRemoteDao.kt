package com.raantech.mycups.data.daos.remote.user

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.auth.login.TokenModel
import com.raantech.mycups.data.models.auth.login.User
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.models.notification.Notification
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserRemoteDao {

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @POST("auth/login")
    suspend fun login(
        @Query("phone_number") userName: String,
        @Query("password") password: String,
        @Query("device_token") device_token: String,
        @Query("platform") platform: String,
    ): ResponseWrapper<UserDetailsResponseModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("auth/logout")
    suspend fun logout(
        @Query("device_token") deviceToken: String
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @POST("auth/register")
    suspend fun register(
        @Query("password") password: String,
        @Query("name") name: String,
        @Query("phone_number") phoneNumber: String?,
        @Query("email") email: String,
        @Query("device_token") deviceToken: String,
        @Query("platform") platform: String
    ): ResponseWrapper<TokenModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @POST("auth/forgetPassword/{email}")
    suspend fun forgetPassword(
        @Query("email") email: String
    ): ResponseWrapper<String>


    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @POST("auth/verify")
    suspend fun verify(
        @Query("token") token: String,
        @Query("code") verificationCode: String
    ): ResponseWrapper<UserDetailsResponseModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:true")
    @GET("auth/resend")
    suspend fun resendCode(
        @Query("token") token: String
    ): ResponseWrapper<TokenModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/user/refreshToken")
    suspend fun refreshToken(
        @Query("RefreshToken") refreshToken: String
    ): ResponseWrapper<UserDetailsResponseModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("api/user")
    suspend fun getProfile(
    ): ResponseWrapper<UserDetailsResponseModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("profile/update/profile")
    suspend fun updateProfile(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("PhoneNumber") phoneNumber: String?,
        @Query("brand_name") brand_Name: String?,
        @Query("has_stock") hasStock: Int?,
        @Query("address_text") addressText: String,
        @Query("address_lat") latitude: Double,
        @Query("address_lng") longitude: Double
    ): ResponseWrapper<UserDetailsResponseModel>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("profile/update/password")
    suspend fun updatePassword(
        @Query("current_password") oldPassword: String,
        @Query("new_password") newPassword: String,
        @Query("new_password_confirmation") newPasswordConfirmation: String
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/user/newPassword")
    suspend fun resetPassword(
        @Query("Password") newPassword: String
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @Multipart
    @PATCH("api/user/update")
    suspend fun updateFcmToken(
        @Part("RegistrationId") registrationId: RequestBody,
        @Part("DeviceType") deviceType: RequestBody
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @Multipart
    @PATCH("api/user/update")
    suspend fun updateProfilePicture(
        @Part image: MultipartBody.Part
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @GET("notifications")
    suspend fun getNotifications(
        @Query("skip") skip: Int
    ): ResponseWrapper<List<Notification>>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("profile/update/profile")
    suspend fun updateAddress(
        @Query("address_text") addressText: String,
        @Query("address_lat") latitude: Double,
        @Query("address_lng") longitude: Double,
        @Query("name") name: String,
        @Query("has_stock") hasStock: Int?
    ): ResponseWrapper<UserDetailsResponseModel>

}