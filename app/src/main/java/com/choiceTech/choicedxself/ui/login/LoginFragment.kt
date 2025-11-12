package com.choiceTech.choicedxself.ui.login

import androidx.fragment.app.viewModels
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.databinding.FragmentLoginBinding
import com.choiceTech.choicedxself.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {
    private val viewModel: LoginViewModel by viewModels()

    override fun init() {
        super.init()

        binding.loginButton.setOnClickListener {
            viewModel.requestLogin(
                "jhkim@chowistest.com",
                "qwer1234"
            )
        }
    }

    override fun observe() {
        super.observe()
    }
}