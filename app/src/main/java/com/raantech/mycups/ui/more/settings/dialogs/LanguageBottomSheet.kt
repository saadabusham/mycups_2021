package com.raantech.mycups.ui.more.settings.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raantech.mycups.R
import com.raantech.mycups.common.CommonEnums
import com.raantech.mycups.data.models.general.GeneralLookup
import com.raantech.mycups.databinding.BottomSheetLanguageBinding
import com.raantech.mycups.utils.LocaleUtil
import com.raantech.mycups.ui.more.settings.adapters.LanguageRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageBottomSheet(
    private val languageCallBack: LanguageCallBack
) : BottomSheetDialogFragment() {

    lateinit var binding: BottomSheetLanguageBinding
    lateinit var languageRecyclerAdapter: LanguageRecyclerAdapter

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
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_language, null, false)
        binding.viewModel = this
        isCancelable = true
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        languageRecyclerAdapter = LanguageRecyclerAdapter(requireActivity())
        binding.recyclerView.adapter = languageRecyclerAdapter
        languageRecyclerAdapter.submitItems(
            arrayListOf(
                GeneralLookup(
                    name = activity?.getString(R.string.english),
                    isSelected = MutableLiveData(LocaleUtil.getLanguage() != CommonEnums.Languages.Arabic.value)
                ),
                GeneralLookup(
                    name = activity?.getString(R.string.arabic),
                    isSelected = MutableLiveData(LocaleUtil.getLanguage() == CommonEnums.Languages.Arabic.value)
                )
            )
        )
    }

    fun onDoneClicked() {
        languageRecyclerAdapter.items.withIndex().singleOrNull { it.value.isSelected.value == true }
            ?.let {
                languageCallBack.callBack(it.index == 0)
                dismiss()
            }
    }

    fun onCancelClicked() {
        dismiss()
    }

    interface LanguageCallBack {
        fun callBack(englishSelected: Boolean)
    }

}