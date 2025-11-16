package com.choiceTech.choicedxself.ui.login

import android.graphics.Paint
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.choiceTech.choicedxself.R
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.core.base.BaseFragmentViewModel
import com.choiceTech.choicedxself.custom.dialog.LoginDialogMode
import com.choiceTech.choicedxself.custom.dialog.LoginEventDialog
import com.choiceTech.choicedxself.databinding.FragmentLoginBinding
import com.choiceTech.choicedxself.ui.common.animationState
import com.choiceTech.choicedxself.ui.common.collectHandler
import com.choiceTech.choicedxself.ui.login.viewmodel.LoginEditType
import com.choiceTech.choicedxself.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val baseViewModel: BaseFragmentViewModel by activityViewModels()
    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        super.init()

        binding.root.setOnClickListener { it.hideKeyboard() }

        mapOf(
            binding.loginEmailEdit to LoginEditType.EMAIL,
            binding.loginPasswordEdit to LoginEditType.PASSWORD
        ).forEach { (editText, type) ->
            editText.addTextChangedListener {
                viewModel.getLoginEditText(type, it.toString())
            }
        }

        binding.loginSignup.apply {
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            setOnClickListener {
                findNavController().safeNavigate(
                    LoginFragmentDirections.actionLoginToSignup()
                )
            }
        }

        binding.loginButton.setOnClickListener {
            viewModel.requestLogin()
        }
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoginEnabled.collect { isEnabled ->
                        binding.loginButton.animationState(
                            R.color.buttonDisableColor,
                            R.color.primaryColor,
                            isEnabled
                        )
                    }
                }

                launch {
                    viewModel.apiState.collectHandler(
                        onShowLoading = { baseViewModel.shouldLoading(true) },
                        onHideLoading = { baseViewModel.shouldLoading(false) },
                        onSuccess = { showLoginEventDialog(true) },
                        onFailure = { showLoginEventDialog(false) },
                        onIdle = {}
                    )
                }
            }
        }
    }

    private fun showLoginEventDialog(isSuccess: Boolean) {
        if (childFragmentManager.findFragmentByTag("loginEvent") == null) {
            val dialog = LoginEventDialog(
                LoginDialogMode.LOGIN,
                isSuccess,
                onClickButton = {
                    findNavController().safeNavigate(
                        LoginFragmentDirections.actionLoginToMain()
                    )
                }
            )
            dialog.show(parentFragmentManager, "loginEvent")
        }
    }
}