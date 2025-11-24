package com.choiceTech.choicedxself.ui.settings

import androidx.fragment.app.viewModels
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.databinding.FragmentRegisterOtpicBinding
import com.choiceTech.choicedxself.ui.settings.viewmodel.RegisterOpticViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterOpticFragment: BaseFragment<FragmentRegisterOtpicBinding>(
    FragmentRegisterOtpicBinding::inflate
) {
    private val viewModel: RegisterOpticViewModel by viewModels()

    override fun init() {
        super.init()
    }

    override fun observe() {
        super.observe()
    }
}