package com.raantech.mycups.ui.auth.login.viewmodels

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.DateTimeUtil
import com.raantech.mycups.utils.extensions.millisecondFormatting
import com.raantech.mycups.utils.extensions.minToMillisecond
import com.raantech.mycups.utils.extensions.secondToMillisecond
import com.raantech.mycups.utils.pref.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    @ApplicationContext context: Context
) : BaseViewModel() {

    companion object {

        const val VALIDATION_CODE_LENGTH = 5

        const val RESEND_ENABLE_TIME_IN_MIN: Long = 2
        const val RESEND_ENABLE_TIME_UPDATE_TIMER_IN_SECOND: Long = 1
        const val JORDANIAN_PHONE_NUMBER_WITHOUT_COUNTRY_CODE_REGEX = "^(7|07)(7|8|9)([0-9]{7})\$"

    }

    val emailMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val passwordMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val buttonEnabled:MutableLiveData<Boolean> = MutableLiveData(false)
    // Login Verification Code
    val signUpVerificationCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val signUpResendPinCodeEnabled: MutableLiveData<Boolean>
            by lazy { MutableLiveData<Boolean>(false) }
    val signUpResendTimer: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val userIdMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    private val forgetCountDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(
            RESEND_ENABLE_TIME_IN_MIN.minToMillisecond(),
            RESEND_ENABLE_TIME_UPDATE_TIMER_IN_SECOND.secondToMillisecond()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                signUpResendTimer.value =
                    millisUntilFinished.millisecondFormatting(DateTimeUtil.TIME_FORMATTING_MIN_AND_SECOND)
            }

            override fun onFinish() {
                signUpResendPinCodeEnabled.value = true
            }
        }
    }

    fun loginUser() = liveData {
        emit(APIResource.loading())
        val response = userRepo.login(
            emailMutableLiveData.value.toString(),
            passwordMutableLiveData.value.toString()
        )
        emit(response)
    }

//    fun socialLoginUser(accessToken: String, provider: String) = liveData {
//        emit(APIResource.loading())
//        val response = userRepo.socialLogin(
//            accessToken,
//            provider
//        )
//        emit(response)
//    }


    fun storeUser(user: UserDetailsResponseModel) {
        signUpVerificationCode.postValue("")
        user.token?.let { userRepo.saveAccessToken(it) }
        userRepo.setUserStatus(UserEnums.UserState.LoggedIn)
        userRepo.setUser(user)
    }

    fun startHandleResendSignUpPinCodeTimer() {
        signUpResendPinCodeEnabled.value = false
        forgetCountDownTimer.cancel()
        forgetCountDownTimer.start()
    }

    fun verifyCode() = liveData {
        emit(APIResource.loading())
        val response = userRepo.verify(
            userIdMutableLiveData.value.toString(),
            signUpVerificationCode.value.toString()
        )
        emit(response)
    }

    fun resendVerificationCode() = liveData {
        emit(APIResource.loading())
        val response = userRepo.resendCode(
            emailMutableLiveData.value.toString()
        )
        emit(response)
    }
}