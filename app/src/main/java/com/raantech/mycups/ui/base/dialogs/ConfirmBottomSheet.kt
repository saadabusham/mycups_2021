package com.raantech.mycups.ui.base.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raantech.mycups.R
import com.raantech.mycups.databinding.BottomSheetConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmBottomSheet(
    val title:String,
    val description:String,
    val btnConfirmTxt:String,
    val btnCancelTxt:String,
    private val callBack: CallBack
) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onStart() {
        super.onStart()
        view?.post {
            val parent = requireView().parent as View
            val params = (parent).layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.peekHeight = requireView().measuredHeight
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_confirmation, null, false)
        binding.viewModel = this
        isCancelable = false
        return binding.root
    }

    fun onConfirmClicked() {
        dismiss()
        callBack.onConfirmed()
    }

    fun onDeclineClicked() {
        dismiss()
        callBack.onDecline()
    }

    interface CallBack {
        fun onConfirmed()
        fun onDecline()
    }

}