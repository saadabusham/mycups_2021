package com.raantech.mycups.data.common

import android.app.Activity
import androidx.lifecycle.Observer
import com.raantech.mycups.data.api.response.*
import com.raantech.mycups.ui.base.dialogs.CustomDialogUtils
import com.raantech.mycups.utils.HandleRequestFailedUtil

class CustomObserverResponse<T>(
    private val activity: Activity,
    private val apiCallBack: APICallBack<T>,
    private val withProgress: Boolean = true,
    private val showError: Boolean = true
) : CustomDialogUtils(activity, withProgress, false),
    Observer<APIResource<ResponseWrapper<T>>> {

    override fun onChanged(responseWrapperResponse: APIResource<ResponseWrapper<T>>) {
        when (responseWrapperResponse?.status) {
            RequestStatusEnum.SUCCESS -> {
                if (withProgress) {
                    hideProgress()
                }
                if (responseWrapperResponse.statusSubCode == ResponseSubErrorsCodeEnum.Success) {
                    apiCallBack.onSuccess(
                        responseWrapperResponse.statusCode
                            ?: -1,
                        responseWrapperResponse.statusSubCode,
                        responseWrapperResponse.data?.body
                    )
                    apiCallBack.onSuccess(
                        responseWrapperResponse.statusCode
                            ?: -1,
                        responseWrapperResponse.statusSubCode,
                        responseWrapperResponse.data
                    )
                    apiCallBack.onSuccess(
                        responseWrapperResponse.statusCode
                            ?: -1, responseWrapperResponse.statusSubCode, responseWrapperResponse
                    )
                } else {
                    if (showError)
                        handleRequestFailedMessages(
                            responseWrapperResponse.statusSubCode,
                            responseWrapperResponse.errors?.get(0)?.getErrorsString()
                                ?: responseWrapperResponse.messages
                        )
                    responseWrapperResponse.messages?.let {
                        responseWrapperResponse.statusSubCode?.let { it1 ->
                            apiCallBack.onError(it1, it, responseWrapperResponse.errors)
                        }
                    }
                }
            }
            RequestStatusEnum.FAILED -> {
                if (withProgress) {
                    hideProgress()
                }
                if (showError)
                    handleRequestFailedMessages(
                        responseWrapperResponse.statusSubCode,
                        responseWrapperResponse.errors?.map { it.getErrorsString() }?.joinToString()
                            ?: responseWrapperResponse.messages
                    )
                responseWrapperResponse.messages?.let {
                    responseWrapperResponse.statusSubCode?.let { it1 ->
                        apiCallBack.onError(it1, it, responseWrapperResponse.errors)
                    }
                }
            }
            RequestStatusEnum.LOADING -> {
                if (withProgress) {
                    showProgress()
                }
                apiCallBack.onLoading()
            }
        }
    }

    interface APICallBack<T> {
        fun onSuccess(statusCode: Int, subErrorCode: ResponseSubErrorsCodeEnum, data: T?) {}
        fun onSuccess(
            statusCode: Int,
            subErrorCode: ResponseSubErrorsCodeEnum,
            data: ResponseWrapper<T>?
        ) {
        }

        fun onSuccess(
            statusCode: Int,
            subErrorCode: ResponseSubErrorsCodeEnum,
            data: APIResource<ResponseWrapper<T>>
        ) {
        }

        fun onError(
            subErrorCode: ResponseSubErrorsCodeEnum,
            message: String,
            errors: List<GeneralError>?
        ) {
        }

        fun onLoading() {}
    }

    private fun handleRequestFailedMessages(
        subErrorCode: ResponseSubErrorsCodeEnum?,
        requestMessage: String?
    ) {
        HandleRequestFailedUtil.handleRequestFailedMessages(
            activity,
            subErrorCode,
            requestMessage
        )
    }
}