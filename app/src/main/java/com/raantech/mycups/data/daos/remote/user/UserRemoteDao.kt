package com.raantech.mycups.data.daos.remote.user

import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.common.NetworkConstants
import com.raantech.mycups.data.models.auth.login.TokenModel
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
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
    ): ResponseWrapper<String>

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
    @Multipart
    @PATCH("api/user/update")
    suspend fun updateProfile(
        @Part("FirstName") firstName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("PhoneNumber") phoneNumber: RequestBody
    ): ResponseWrapper<Any>

    @Headers("${NetworkConstants.SKIP_AUTHORIZATION_HEADER}:false")
    @POST("api/user/newPassword")
    suspend fun updatePassword(
        @Query("OldPassword") oldPassword: String,
        @Query("Password") newPassword: String
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
}