package com.raantech.mycups.ui.more.profile.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.raantech.mycups.R
import com.raantech.mycups.data.api.response.GeneralError
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.models.auth.login.User
import com.raantech.mycups.data.models.auth.login.UserDetailsResponseModel
import com.raantech.mycups.databinding.ActivityUpdateProfileBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.dialogs.ConfirmBottomSheet
import com.raantech.mycups.ui.main.activity.MainActivity
import com.raantech.mycups.ui.more.addresses.activity.AddressesActivity
import com.raantech.mycups.ui.more.changepassword.ChangePasswordActivity
import com.raantech.mycups.ui.more.profile.presenter.UpdateProfilePresenter
import com.raantech.mycups.ui.more.profile.viewmodels.UpdateProfileViewModel
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class UpdateProfileActivity :
    BaseBindingActivity<ActivityUpdateProfileBinding, UpdateProfilePresenter>(),
    UpdateProfilePresenter {

    override fun getPresenter()=this

    private val viewModel: UpdateProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_update_profile,
            hasToolbar = true,
            toolbarView = toolbar,
            hasBackButton = true,
            showBackArrow = true,
            hasTitle = true,
            title = R.string.menu_account
        )
        binding?.viewModel = viewModel
    }

    override fun onEditClicked() {
        if (validateInput())
            viewModel.updateUser().observe(this, updateResultObserver())
    }

    override fun onAddressClicked() {
        AddressesActivity.start(this)

    }

    override fun onChangePasswordClicked() {
        ChangePasswordActivity.start(this)

    }

    override fun onSelectStorageClicked() {
        if (viewModel.hasStock.value == false) {
            showStorageHintDialog()
        } else {
            viewModel.hasStock.value = false
        }
    }

    private fun showStorageHintDialog() {
        ConfirmBottomSheet(
            title = getString(R.string.storage_information),
            description = getString(R.string.storage_information_desc),
            btnConfirmTxt = getString(R.string.agree),
            btnCancelTxt = getString(R.string.disagree),
            object : ConfirmBottomSheet.CallBack {
                override fun onConfirmed() {
                    viewModel.hasStock.value = true
                }

                override fun onDecline() {

                }
            }).show(supportFragmentManager, "tag")
    }

    private fun validateInput(): Boolean {
        binding?.edFirstName?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            this
        ).let {
            if (!it.isValid) {
                showErrorAlert(
                    title = getString(R.string.full_name),
                    message = it.errorMessage
                )
                return false
            }
        }
        binding?.edBrandName?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            this
        ).let {
            if (!it.isValid) {
                showErrorAlert(
                    title = getString(R.string.brand_name),
                    message = it.errorMessage
                )
                return false
            }
        }
//        binding?.edPhoneNumber?.text.toString().validate(
//            ValidatorInputTypesEnums.PHONE_NUMBER,
//            this
//        ).let {
//                if (!it.isValid) {
//                    showValidationErrorAlert(
//                        title = getString(R.string.phone_number),
//                        message = it.errorMessage
//                    )
//                    return false
//                }
//            }
        binding?.edEmail?.text.toString().validate(
            ValidatorInputTypesEnums.EMAIL,
            this
        ).let {
            if (!it.isValid) {
                showErrorAlert(
                    title = getString(R.string.email),
                    message = it.errorMessage
                )
                return false
            }
        }
//        binding?.tvAddress?.text.toString().validate(
//            ValidatorInputTypesEnums.TEXT,
//            this
//        ).let {
//                if (!it.isValid) {
//                    showValidationErrorAlert(
//                        title = getString(R.string.address),
//                        message = it.errorMessage
//                    )
//                    return false
//                }
//            }

        return true
    }

    private fun updateResultObserver(): CustomObserverResponse<UserDetailsResponseModel> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<UserDetailsResponseModel> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: UserDetailsResponseModel?
                ) {
                    data?.user?.let {
                        viewModel.storeUser(it)
                        MainActivity.start(this@UpdateProfileActivity)
                    }
                }

                override fun onError(
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    message: String,
                    errors: List<GeneralError>?
                ) {
                    super.onError(subErrorCode, message, errors)
                    errors?.get(0)?.let {
                        showErrorAlert(it.key, it.getErrorsString())
                    }
                }
            }, showError = false
        )
    }

    companion object {
        fun start(
            context: Context?
        ) {
            val intent = Intent(context, UpdateProfileActivity::class.java)
            context?.startActivity(intent)
        }
    }
}