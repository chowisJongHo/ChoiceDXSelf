package com.choiceTech.choicedxself.ui.login

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.core.base.BaseFragmentViewModel
import com.choiceTech.choicedxself.custom.dialog.LoginDialogMode
import com.choiceTech.choicedxself.custom.dialog.LoginEventDialog
import com.choiceTech.choicedxself.databinding.FragmentSignupBinding
import com.choiceTech.choicedxself.ui.common.collectHandler
import com.choiceTech.choicedxself.ui.login.viewmodel.SignupCheckType
import com.choiceTech.choicedxself.ui.login.viewmodel.SignupInputType
import com.choiceTech.choicedxself.ui.login.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingUpFragment : BaseFragment<FragmentSignupBinding>(
    FragmentSignupBinding::inflate
) {
    private val baseViewModel: BaseFragmentViewModel by activityViewModels()
    private val viewModel: SignupViewModel by viewModels()

    override fun init() {
        super.init()

        mapOf(
            binding.signupEmailEdit to SignupInputType.EMAIL,
            binding.signupPasswordEdit to SignupInputType.PASSWORD,
            binding.signupConfirmEdit to SignupInputType.CONFIRM
        ).forEach { (editText, type) ->
            editText.addTextChangedListener {
                viewModel.getInput(type, it.toString())
            }
        }

        mapOf(
            binding.signupCheck2 to SignupCheckType.EMAIL_AGREE,
            binding.signupCheck3 to SignupCheckType.PRIVACY_AGREE
        ).forEach { (checkBox, type) ->
            checkBox.setOnCheckedChangeListener { _, isCheck ->
                viewModel.getCheck(type, isCheck)
            }
        }

        binding.signupButton.setOnClickListener {
            viewModel.requestSignup()
        }
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isSignupEnabled.collect { isEnabled ->
                        binding.signupButton.isEnabled = isEnabled
                    }
                }

                launch {
                    viewModel.apiState.collectHandler(
                        onShowLoading = { baseViewModel.shouldLoading(true) },
                        onHideLoading = { baseViewModel.shouldLoading(false) },
                        onSuccess = { showEventDialog(true) },
                        onFailure = { showEventDialog(false) },
                        onIdle = {}
                    )
                }
            }
        }
    }

    private fun showEventDialog(isSuccess: Boolean) {
        if (childFragmentManager.findFragmentByTag("loginEvent") == null) {
            val dialog = LoginEventDialog(
                LoginDialogMode.SIGN_UP,
                isSuccess,
                onClickButton = {
                    findNavController().popBackStack()
                })
            dialog.show(parentFragmentManager, "loginEvent")
        }
    }
}