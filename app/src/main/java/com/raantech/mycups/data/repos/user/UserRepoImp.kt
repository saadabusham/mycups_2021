package com.raantech.mycups.data.repos.user

import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.api.response.ResponseHandler
import com.raantech.mycups.data.api.response.ResponseWrapper
import com.raantech.mycups.data.daos.remote.user.UserRemoteDao
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.TokenModel
import com.raantech.mycups.data.pref.user.UserPref
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.models.notification.Notification
import com.raantech.mycups.data.repos.base.BaseRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Query
import javax.inject.Inject

class UserRepoImp @Inject constructor(
    responseHandler: ResponseHandler,
    private val userRemoteDao: UserRemoteDao,
    private val userPref: UserPref
) : BaseRepo(responseHandler), UserRepo {


    override suspend fun login(
        userName: String,
        password: String,
        device_token: String,
        platform: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.login(
                    userName,
                    password,
                    device_token,
                    platform
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun logout(deviceToken: String): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.logout(
                    deviceToken
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun register(
        password: String,
        name: String,
        phoneNumber: String?,
        email: String,
        deviceToken: String,
        platform: String
    ): APIResource<ResponseWrapper<String>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.register(
                    password, name, phoneNumber, email, deviceToken, platform
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun forgetPassword(email: String): APIResource<ResponseWrapper<String>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.forgetPassword(
                    email
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun verify(
        token: String,
        verificationCode: String
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.verify(
                    token, verificationCode
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun resendCode(token: String): APIResource<ResponseWrapper<TokenModel>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.resendCode(
                    token
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun refreshToken(refreshToken: String): APIResource<ResponseWrapper<UserDetailsResponseModel>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.refreshToken(
                    refreshToken
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getProfile(): APIResource<ResponseWrapper<UserDetailsResponseModel>> {
        return try {
            responseHandle.handleSuccess(userRemoteDao.getProfile())
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updateProfile(
        name: String,
        email: String,
        phoneNumber: String?,
        brandName: String?,
        hasStock: Int?,
        addressText: String,
        latitude: Double,
        longitude: Double
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.updateProfile(
                    name, email, phoneNumber, brandName, hasStock,addressText, latitude, longitude
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updatePassword(
        oldPassword: String,
        newPassword: String,
        newPasswordConfirmation: String
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.updatePassword(
                    oldPassword, newPassword, newPasswordConfirmation
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun resetPassword(
        newPassword: String
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.resetPassword(
                    newPassword
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updateFcmToken(
        registrationId: RequestBody,
        deviceType: RequestBody
    ): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.updateFcmToken(
                    registrationId, deviceType
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updateProfilePicture(image: MultipartBody.Part): APIResource<ResponseWrapper<Any>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.updateProfilePicture(
                    image
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun getNotifications(skip: Int): APIResource<ResponseWrapper<List<Notification>>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.getNotifications(
                    skip
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override suspend fun updateAddress(
        addressText: String,
        latitude: Double,
        longitude: Double,
        name: String,
        hasStock: Int?
    ): APIResource<ResponseWrapper<UserDetailsResponseModel>> {
        return try {
            responseHandle.handleSuccess(
                userRemoteDao.updateAddress(
                    addressText, latitude, longitude, name, hasStock
                )
            )
        } catch (e: Exception) {
            responseHandle.handleException(e)
        }
    }

    override fun saveAccessToken(accessToken: String) {
        userPref.saveAccessToken(accessToken)
    }

    override fun getAccessToken(): String {
        return userPref.getAccessToken()
    }

    override fun saveUserPassword(password: String) {
        userPref.saveUserPassword(password)
    }

    override fun getUserPassword(): String {
        return userPref.getUserPassword()
    }

    override fun setUserStatus(userState: UserEnums.UserState) {
        userPref.setUserStatus(userState.ordinal)
    }

    override fun getUserStatus(): UserEnums.UserState {
        return UserEnums.UserState.getUserStateByPosition(userPref.getUserStatus())
    }


    override fun saveNotificationStatus(flag: Boolean) {
        userPref.setNotificationStatus(flag)
    }

    override fun getNotificationStatus(): Boolean {
        return userPref.getNotificationStatus()
    }

    override fun saveTouchIdStatus(flag: Boolean) {
        userPref.setTouchIdStatus(flag)
    }

    override fun getTouchIdStatus(): Boolean {
        return userPref.getTouchIdStatus()
    }

    override fun setUser(user: UserDetailsResponseModel) {
        userPref.setUser(user)
    }

    override fun getUser(): UserDetailsResponseModel? {
        return userPref.getUser()
    }

}