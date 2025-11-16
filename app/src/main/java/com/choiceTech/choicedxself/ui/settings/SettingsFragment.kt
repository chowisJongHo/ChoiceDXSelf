package com.choiceTech.choicedxself.ui.settings

import android.provider.Settings
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.databinding.FragmentSettingsBinding
import com.choiceTech.choicedxself.ui.settings.viewmodel.SettingsSelected
import com.choiceTech.choicedxself.ui.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment: BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    private val viewModel: SettingsViewModel by viewModels()

    override fun init() {
        super.init()

        mapOf(
            binding.settingsDeviceContainer to SettingsSelected.DEVICE,
            binding.settingsProductContainer to SettingsSelected.PRODUCT
        ).forEach { (button, mode) ->
            button.setOnClickListener { viewModel.onSettingsClick(mode) }
        }

        binding.settingsLogoutContainer.setOnClickListener {
            viewModel.requestLogout()
        }
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.settingsShared.collect { shared ->
                        when(shared) {
                            SettingsSelected.DEVICE -> TODO()
                            SettingsSelected.PRODUCT -> TODO()
                        }
                    }
                }

                launch {
                    viewModel.crmState.collect {
                        TODO()
                    }
                }
            }
        }
    }
}