package com.raantech.mycups.ui.more.addresses.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.raantech.mycups.ui.more.addresses.viewmodels.AddressesViewModel
import com.raantech.mycups.R
import com.raantech.mycups.common.interfaces.TextTypingCallback
import com.raantech.mycups.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.mycups.data.common.Constants
import com.raantech.mycups.data.common.CustomObserverResponse
import com.raantech.mycups.data.enums.InputFieldValidStateEnums
import com.raantech.mycups.data.models.auth.login.User
import com.raantech.mycups.data.models.general.City
import com.raantech.mycups.data.models.map.Address
import com.raantech.mycups.databinding.ActivityAddressBinding
import com.raantech.mycups.ui.base.activity.BaseBindingActivity
import com.raantech.mycups.ui.base.bindingadapters.updateStrokeColor
import com.raantech.mycups.ui.common.citypicker.activity.CitiesPickerActivity
import com.raantech.mycups.ui.more.map.MapActivity
import com.raantech.mycups.utils.extensions.gone
import com.raantech.mycups.utils.extensions.showErrorAlert
import com.raantech.mycups.utils.extensions.validate
import com.raantech.mycups.utils.extensions.visible
import com.raantech.mycups.utils.getLocationName
import com.raantech.mycups.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesActivity : BaseBindingActivity<ActivityAddressBinding, Nothing>() {

    private val viewModel: AddressesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_address,
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true
        )
        binding?.viewModel = viewModel
        viewModel.setData()
        setUpListener()
    }

    private fun setUpListener() {
        binding?.tvCity?.setOnClickListener {
            CitiesPickerActivity.start(
                context = this,
                resultLauncher = cityResultLauncher
            )
        }
        binding?.tvLocation?.setOnClickListener {
            MapActivity.start(
                context = this,
                resultLauncher = mapResultLauncher
            )
        }
        binding?.btnSave?.setOnClickListener {
            if (isDataValidate()) {
                viewModel.addAddress().observe(this, updateAddressResultObserver())
            }
        }
        binding?.edContactName?.addTextChangedListener(inputListeners)
        binding?.edPhoneNumber?.addTextChangedListener(inputListeners)
        binding?.tvCity?.addTextChangedListener(inputListeners)
        binding?.edDistrict?.addTextChangedListener(inputListeners)
        binding?.edStreet?.addTextChangedListener(inputListeners)
        binding?.edBuildingNumber?.addTextChangedListener(inputListeners)
        binding?.edDescription?.addTextChangedListener(inputListeners)
    }

    private var mapResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.getSerializableExtra(Constants.BundleData.ADDRESS).let {
                    it as Address
                    viewModel.latitude.value = it.lat
                    viewModel.longitude.value = it.lon
                    viewModel.addressStr.value = getLocationName(it.lat, it.lon)
                    isDataValidate()
                }
            }
        }

    private var cityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                viewModel.city.value =
                    ((data?.getSerializableExtra(Constants.BundleData.CITY) as City)).code
                isDataValidate()
            }
        }

    private val inputListeners = object : TextTypingCallback {
        override fun textChanged(text: String) {
            isDataValidate()
        }
    }

    private fun updateAddressResultObserver(): CustomObserverResponse<User> {
        return CustomObserverResponse(
            this,
            object : CustomObserverResponse.APICallBack<User> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: User?
                ) {
                    onBackPressed()
                }
            })
    }

    private fun isDataValidate(): Boolean {
        var valid = true
        binding?.edContactName?.text.toString()
            .validate(ValidatorInputTypesEnums.TEXT, this).let {
                if (!it.isValid) {
                    binding?.tvContactNameError?.text = it.errorMessage
                    binding?.tvContactNameError?.visible()
                    binding?.viewName?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.viewName?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvContactNameError?.gone()
                }
            }
        binding?.edPhoneNumber?.text.toString()
            .validate(ValidatorInputTypesEnums.PHONE_NUMBER, this).let {
                if (!it.isValid) {
                    binding?.tvPhoneError?.text = it.errorMessage
                    binding?.tvPhoneError?.visible()
                    binding?.viewPhone?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.viewPhone?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvPhoneError?.gone()
                }
            }
        if (viewModel.city.value == null) {
            binding?.tvCityError?.text = resources.getString(R.string.please_select_city)
            binding?.tvCityError?.visible()
            binding?.viewCity?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
            return false
        } else {
            binding?.viewCity?.updateStrokeColor(InputFieldValidStateEnums.VALID)
            binding?.tvCityError?.gone()
        }

        binding?.edDistrict?.text.toString()
            .validate(ValidatorInputTypesEnums.TEXT, this).let {
                if (!it.isValid) {
                    binding?.tvDescriptionError?.text = it.errorMessage
                    binding?.tvDescriptionError?.visible()
                    binding?.viewDistrict?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.viewDistrict?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvDescriptionError?.gone()
                }
            }

        binding?.edStreet?.text.toString()
            .validate(ValidatorInputTypesEnums.TEXT, this).let {
                if (!it.isValid) {
                    binding?.tvStreetError?.text = it.errorMessage
                    binding?.tvStreetError?.visible()
                    binding?.viewStreet?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.viewStreet?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvStreetError?.gone()
                }
            }
        binding?.edBuildingNumber?.text.toString()
            .validate(ValidatorInputTypesEnums.TEXT, this).let {
                if (!it.isValid) {
                    binding?.tvBuildingNumberError?.text = it.errorMessage
                    binding?.tvBuildingNumberError?.visible()
                    binding?.viewBuildingNumber?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.viewBuildingNumber?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvBuildingNumberError?.gone()
                }
            }
        binding?.edDescription?.text.toString()
            .validate(ValidatorInputTypesEnums.TEXT, this).let {
                if (!it.isValid) {
                    binding?.tvDescriptionError?.text = it.errorMessage
                    binding?.tvDescriptionError?.visible()
                    binding?.viewDesctiption?.updateStrokeColor(InputFieldValidStateEnums.ERROR)
                    return false
                } else {
                    binding?.viewDesctiption?.updateStrokeColor(InputFieldValidStateEnums.VALID)
                    binding?.tvDescriptionError?.gone()
                }
            }
        if (viewModel.latitude.value == null ||
            viewModel.latitude.value == 0.0 ||
            viewModel.longitude.value == null ||
            viewModel.longitude.value == 0.0
        ) {
            showErrorAlert(getString(R.string.location), getString(R.string.please_pick_location))
            return false
        }
        return valid
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AddressesActivity::class.java)
            context.startActivity(intent)
        }

        fun start(
            context: Activity?,
            resultLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, AddressesActivity::class.java).apply {
                putExtra(Constants.BundleData.IS_ACTIVITY_RESULT, true)
            }
            resultLauncher.launch(intent)
        }
    }

}