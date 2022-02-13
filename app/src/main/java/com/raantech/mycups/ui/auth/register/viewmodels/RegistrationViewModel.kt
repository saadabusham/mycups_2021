package com.raantech.mycups.ui.auth.register.viewmodels

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import co.infinum.goldfinger.Goldfinger
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.APIResource
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.enums.UserEnums
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.data.repos.user.UserRepo
import com.raantech.mycups.ui.base.viewmodel.BaseViewModel
import com.raantech.mycups.utils.DateTimeUtil
import com.raantech.mycups.utils.extensions.millisecondFormatting
import com.raantech.mycups.utils.extensions.minToMillisecond
import com.raantech.mycups.utils.extensions.secondToMillisecond
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepo: UserRepo,
    @ApplicationContext val context: Context
) : BaseViewModel() {

    companion object {
        // Phone Number
        const val PHONE_NUMBER_MAX_LENGTH = 9
        const val JORDANIAN_PHONE_NUMBER_WITHOUT_COUNTRY_CODE_REGEX = "^(7|07)(7|8|9)([0-9]{7})\$"

        const val VALIDATION_CODE_LENGTH = 5

        const val RESEND_ENABLE_TIME_IN_MIN: Long = 1
        const val RESEND_ENABLE_TIME_UPDATE_TIMER_IN_SECOND: Long = 1

    }

    val phoneNumberWithoutCountryCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val firstNameMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val lastNameMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val emailMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val passwordMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val confirmPasswordMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val userIdMutableLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>("") }
    val buttonEnabled:MutableLiveData<Boolean> = MutableLiveData(false)

    // SignUp Verification Code
    val signUpVerificationCode: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val signUpResendPinCodeEnabled: MutableLiveData<Boolean>
            by lazy { MutableLiveData<Boolean>(false) }
    val signUpResendTimer: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val forgetCountDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(
            RESEND_ENABLE_TIME_IN_MIN.minToMillisecond(),
            RESEND_ENABLE_TIME_UPDATE_TIMER_IN_SECOND.secondToMillisecond()
        ) {
            override fun onTick(millisUntilFinished: Long) {
                signUpResendTimer.value =
                    millisUntilFinished.millisecondFormatting(DateTimeUtil.TIME_FORMATTING_MIN_AND_SECOND)
            }

            override fun onFinish() {
                signUpResendTimer.value = context.resources.getString(R.string.send_again)
                signUpResendPinCodeEnabled.value = true
            }
        }
    }

    fun startHandleResendSignUpPinCodeTimer() {
        signUpResendPinCodeEnabled.value = false
        forgetCountDownTimer.cancel()
        forgetCountDownTimer.start()
    }

    fun registerUser() = liveData {
        emit(APIResource.loading())
        val response = userRepo.register(
            firstName = firstNameMutableLiveData.value.toString(),
//            lastName = lastNameMutableLiveData.value.toString(),
            phoneNumber = if(phoneNumberWithoutCountryCode.value.isNullOrEmpty()) null else phoneNumberWithoutCountryCode.value.toString(),
            email = emailMutableLiveData.value.toString(),
            password = passwordMutableLiveData.value.toString(),
            deviceType = Constants.DEVICE_TYPE,
            registrationId = "",
            userName = emailMutableLiveData.value.toString()
        )
        emit(response)
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
        val response = userRepo.forgetPassword(
            emailMutableLiveData.value.toString()
        )
        emit(response)
    }

    fun storeUser(user: UserDetailsResponseModel) {
        signUpVerificationCode.postValue("")
        userRepo.saveUserPassword(passwordMutableLiveData.value ?: "")
        userRepo.setUserStatus(UserEnums.UserState.LoggedIn)
        userRepo.setUser(user)
        user.token?.let { it1 -> userRepo.saveAccessToken(it1) }
    }

    fun isTouchIdShouldVisible(): Boolean {
        return Goldfinger.Builder(context).build().canAuthenticate()
    }

}