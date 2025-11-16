package com.choiceTech.choicedxself.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import com.choiceTech.choicedxself.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

abstract class BaseFragment<VB: ViewBinding>(
    private val inflaterBinding: (LayoutInflater, ViewGroup, Boolean) -> VB
): Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private val viewModel: BaseFragmentViewModel by activityViewModels()

    protected lateinit var headerView: View

    private var loadingDialog: LoadingDialogFragment? = null

    open fun shouldShowHeaderView(): Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_base_container, container, false)
        val container: FrameLayout = root.findViewById(R.id.container)
        _binding = inflaterBinding(inflater, container, false)
        container.addView(binding.root)

        init()
        observe()

        baseObserve()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headerView = view.findViewById(R.id.header)

        showHeader(shouldShowHeaderView())
    }

    open fun init() {}
    open fun observe() {}

    private fun baseObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isShowLoading.collect { isShow ->
                        if (isShow) showLoading() else hideLoading()
                    }
                }
            }
        }
    }

    private fun showHeader(isShow: Boolean) {
        headerView.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    /**
     * LoadingDialog
     * */
    private fun showLoading() {
        if (loadingDialog?.isAdded != true) {
            loadingDialog = LoadingDialogFragment()
            loadingDialog?.show(childFragmentManager, "loadingDialog")
        }
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    /**
     * Navigation
     * */
    fun NavController.safeNavigate(
        directions: NavDirections,
        withAnim: Boolean = true
    ) {
        val action = currentDestination?.getAction(directions.actionId)
        if (action != null) {
            if (withAnim) {
                navigate(directions, navOptions {
                    anim {
                        enter = R.anim.slide_in_right
                        exit = R.anim.slide_out_left
                        popEnter = R.anim.slide_in_left
                        popExit = R.anim.slide_out_right
                    }
                })
            } else {
                navigate(directions)
            }
        }
    }


    /**
     * System
     * */
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}