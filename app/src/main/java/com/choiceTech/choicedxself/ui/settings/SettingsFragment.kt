package com.choiceTech.choicedxself.ui.settings

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.databinding.FragmentSettingsBinding
import com.choiceTech.choicedxself.ui.common.collectHandler
import com.choiceTech.choicedxself.ui.settings.viewmodel.SettingsGeneralSelected
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
        ).forEach { (button, selected) ->
            button.setOnClickListener { viewModel.onSettingsClick(selected) }
        }

        binding.settingsLogoutContainer.setOnClickListener {
            viewModel.requestLogout()
        }

        mapOf(
            binding.settingsResultContainer to SettingsGeneralSelected.RESULT_SUMMARY,
            binding.settingsAnalysisContainer to SettingsGeneralSelected.ANALYSIS_MODE
        ).forEach { (button, selected) ->
            button.setOnClickListener {
                viewModel.onSettingsGeneralClick(selected)
            }
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
                    viewModel.crmState.collectHandler(
                        onShowLoading = {},
                        onHideLoading = {},
                        onSuccess = {},
                        onFailure = {}
                    )
                }

                launch {
                    viewModel.resultSummaryToggle.collect { isShow ->
                        binding.settingsResultItemContainer.visibility =
                            if (isShow) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }
}