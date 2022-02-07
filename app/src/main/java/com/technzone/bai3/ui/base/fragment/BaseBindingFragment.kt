package com.technzone.bai3.ui.base.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akexorcist.localizationactivity.core.OnLocaleChangedListener
import com.technzone.bai3.BR
import com.technzone.bai3.R
import com.technzone.bai3.data.api.response.ResponseSubErrorsCodeEnum
import com.technzone.bai3.ui.base.activity.BaseBindingActivity
import com.technzone.bai3.ui.base.activity.IBaseBindingActivity
import com.technzone.bai3.ui.base.dialogs.CustomDialogUtils
import com.technzone.bai3.ui.base.dialogs.progressdialog.ProgressDialogUtil
import com.technzone.bai3.ui.base.presenters.BaseBindingPresenter
import com.technzone.bai3.utils.HandleRequestFailedUtil
import com.technzone.bai3.utils.extensions.longToast
import com.technzone.bai3.utils.extensions.refreshLocal
import com.technzone.bai3.utils.extensions.startTransitionDelay
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseBindingFragment<BINDING : ViewDataBinding, PRESENTER : BaseBindingPresenter?> :
    Fragment(),
    IBaseBindingFragment<PRESENTER>, OnLocaleChangedListener {

    protected var binding: BINDING? = null
    protected lateinit var navigationController: NavController

    var rootView: View? = null
    private var shouldCallVisibleView = true
    open fun refreshData() {}
    lateinit var customDialogUtils: CustomDialogUtils

    override fun getPresenter(): PRESENTER? {
        return null
    }

    override fun getParentPresenter(): BaseBindingPresenter? {
        return (requireActivity() as BaseBindingActivity<*, *>).getPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity?.refreshLocal()
        customDialogUtils =
            CustomDialogUtils(requireActivity(), withProgress = true, isShowNow = false)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null || shouldRefreshPageWhenReturn()) {
            when {
                binding != null -> {
                    rootView = binding?.root
                }
                isBindingEnabled() -> {
                    binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
                    binding?.lifecycleOwner = this
                    getPresenter()?.let {
                        binding?.setVariable(BR.presenter, it)
                    }
                    rootView = binding?.root
                }
                else -> {
                    rootView = inflater.inflate(getLayoutId(), container, false)
                }
            }
            shouldCallVisibleView = true
        } else {
            shouldCallVisibleView = false
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (shouldCallVisibleView) {
            onViewVisible()
        }
        if (hasNavigation())
            try {
                navigationController = Navigation.findNavController(view)
            } catch (e: Exception) {

            }
    }


    override fun startActivity(intent: Intent?) =
        super.startActivity(
            intent, ActivityOptions.makeCustomAnimation(
                context,
                R.anim.slide_in_end, R.anim.slide_out_left
            ).toBundle()
        )


    override fun startActivityForResult(intent: Intent?, requestCode: Int) =
        super.startActivityForResult(
            intent, requestCode, ActivityOptions.makeCustomAnimation(
                context,
                R.anim.slide_in_end, R.anim.slide_out_left
            ).toBundle()
        )

    override fun showLoadingView() {
        customDialogUtils.showProgress()
    }

    override fun hideLoadingView() {
        customDialogUtils.hideProgress()
    }

    override fun showProgressDialog(title: String, message: String, isRemovable: Boolean) {
        activity?.let {
            ProgressDialogUtil.showProgressDialog(it, title, message, isRemovable)
        }
    }

    override fun handleRequestFailedMessages(
        errorCode: Int?,
        subErrorCode: ResponseSubErrorsCodeEnum?,
        requestMessage: String?
    ) {
        activity?.let {
            HandleRequestFailedUtil.handleRequestFailedMessages(
                it,
                subErrorCode,
                requestMessage
            )
        }
    }

    override fun addToolbar(
        toolbarView: Toolbar?,
        hasToolbar: Boolean,
        hasBackButton: Boolean,
        hasTitle: Boolean,
        title: Int,
        titleString: String?,
        hasSubTitle: Boolean,
        subTitle: Int,
        showBackArrow: Boolean,
        navigationIcon: Int?
    ) {
        if (activity is IBaseBindingActivity<*>) {
            (activity as IBaseBindingActivity<*>).addToolbar(
                toolbarView = toolbarView,
                hasToolbar = hasToolbar,
                hasBackButton = hasBackButton,
                hasTitle = hasTitle,
                title = title,
                titleString = titleString,
                hasSubTitle = hasSubTitle,
                subTitle = subTitle,
                showBackArrow = showBackArrow,
                navigationIcon = navigationIcon
            )
        }
    }

    override fun updateToolbarTitle(hasTitle: Boolean, title: Int, titleString: String?) {
        if (activity is IBaseBindingActivity<*>) {
            (activity as IBaseBindingActivity<*>).updateToolbarTitle(
                hasTitle, title, titleString
            )
        }
    }

    override fun updateToolbarTitle(
        hasTitle: Boolean,
        title: Int,
        titleString: String?,
        hasBackButton: Boolean,
        backArrowTint: Int?,
        showBackArrow: Boolean
    ) {
        if (activity is IBaseBindingActivity<*>) {
            (activity as IBaseBindingActivity<*>).updateToolbarTitle(
                hasTitle, title, titleString, hasBackButton, backArrowTint, showBackArrow
            )
        }
    }

    override fun updateToolbarSubTitle(
        hasSubTitle: Boolean,
        subTitle: Int,
        subTitleString: String?
    ) {
        if (activity is IBaseBindingActivity<*>) {
            (activity as IBaseBindingActivity<*>).updateToolbarSubTitle(
                hasSubTitle, subTitle, subTitleString
            )
        }
    }

    override fun onAfterLocaleChanged() {

    }

    override fun onBeforeLocaleChanged() {

    }

    fun handleError(throwable: Throwable?) {
        when (throwable) {
            is IOException -> {
                longToast(getString(R.string.error_no_internet))
            }
            is SocketTimeoutException -> {
                longToast(getString(R.string.error_server))
            }
            is HttpException -> {

            }
            else -> {
                longToast(getString(R.string.error_msg))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    internal fun startTransitionDelay(viewGroup: ViewGroup? = null) {
        if (viewGroup == null)
            (binding?.root as ViewGroup).startTransitionDelay()
        else {
            viewGroup.startTransitionDelay()
        }
    }
}