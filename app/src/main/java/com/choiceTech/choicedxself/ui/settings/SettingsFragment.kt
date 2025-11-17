package com.choiceTech.choicedxself.ui.settings

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.core.model.settings.AnalysisMode
import com.choiceTech.choicedxself.core.model.settings.Mode
import com.choiceTech.choicedxself.core.model.settings.ResultSummary
import com.choiceTech.choicedxself.core.model.settings.SettingsGeneralSelected
import com.choiceTech.choicedxself.core.model.settings.SettingsSelected
import com.choiceTech.choicedxself.core.model.settings.SkinGroupSelection
import com.choiceTech.choicedxself.core.model.settings.SleepOption
import com.choiceTech.choicedxself.databinding.FragmentSettingsBinding
import com.choiceTech.choicedxself.ui.common.animateRotate
import com.choiceTech.choicedxself.ui.common.collectHandler
import com.choiceTech.choicedxself.ui.settings.adapter.SettingsLanguageAdapter
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
            binding.settingsAnalysisContainer to SettingsGeneralSelected.ANALYSIS_MODE,
            binding.settingsSleepContainer to SettingsGeneralSelected.SLEEP_OPTION,
            binding.settingsSkinGroupContainer to SettingsGeneralSelected.SKIN_GROUP_SELECTION,
            binding.settingsModeContainer to SettingsGeneralSelected.MODE
        ).forEach { (button, selected) ->
            button.setOnClickListener {
                viewModel.onSettingsGeneralClick(selected)
            }
        }

        mapOf(
            binding.settingsResultHealthText to ResultSummary.SKIN_HEALTH_SCORE,
            binding.settingsResultIAgeText to ResultSummary.SKIN_AGE
        ).forEach { (button, type) ->
            button.setOnClickListener {
                viewModel.getResultSummarySelect(type)
            }
        }

        mapOf(
            binding.settingsAnalysisOnlineText to AnalysisMode.ONLINE,
            binding.settingsAnalysisOfflineText to AnalysisMode.OFFLINE,
            binding.settingsAnalysisWithoutText to AnalysisMode.WITH_OUT
        ).forEach { (button, type) ->
            button.setOnClickListener {
                viewModel.getAnalysisModeSelect(type)
            }
        }

        mapOf(
            binding.settingsSleep30Text to SleepOption.MIN30,
            binding.settingsSleepNeverText to SleepOption.NEVER
        ).forEach { (button, type) ->
            button.setOnClickListener {
                viewModel.getSleepOptionSelect(type)
            }
        }

        mapOf(
            binding.settingsSkinGroupAutoText to SkinGroupSelection.AUTO,
            binding.settingsSkinGroupAutoManualText to SkinGroupSelection.AUTO_MANUAL,
            binding.settingsSkinGroupDisableText to SkinGroupSelection.DISABLE
        ).forEach { (button, type) ->
            button.setOnClickListener {
                viewModel.getSkinGroupSelect(type)
            }
        }

        mapOf(
            binding.settingsModeSelectableText to Mode.SELECTABLE,
            binding.settingsModeSkinOnlyText to Mode.SKIN_ONLY,
            binding.settingsModeHairOnlyText to Mode.HAIR_ONLY
        ).forEach { (button, type) ->
            button.setOnClickListener {
                viewModel.getModeSelect(type)
            }
        }

        val adapter = SettingsLanguageAdapter()
        binding.settingsLanguageRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
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
                        binding.settingsGeneralResultArrow.animateRotate(isShow)
                        binding.settingsResultItemContainer.visibility =
                            if (isShow) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    viewModel.analysisModeToggle.collect { isShow ->
                        binding.settingsGeneralAnalysisArrow.animateRotate(isShow)
                        binding.settingsAnalysisItemContainer.visibility =
                            if (isShow) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    viewModel.sleepOptionToggle.collect { isShow ->
                        binding.settingsGeneralSleepsArrow.animateRotate(isShow)
                        binding.settingsSleepItemContainer.visibility =
                            if (isShow) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    viewModel.skinGroupSelectionToggle.collect { isShow ->
                        binding.settingsGeneralSkinGroupArrow.animateRotate(isShow)
                        binding.settingsSkinGroupItemContainer.visibility =
                            if (isShow) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    viewModel.modeToggle.collect { isShow ->
                        binding.settingsGeneralModeArrow.animateRotate(isShow)
                        binding.settingsModeItemContainer.visibility =
                            if (isShow) View.VISIBLE else View.GONE
                    }
                }

                launch {
                    viewModel.resultSummarySelect.collect { data ->
                        binding.settingsGeneralResultSelected.text = getText(data.strID)
                    }
                }

                launch {
                    viewModel.analysisModeSelect.collect { data ->
                        binding.settingsAnalysisSelected.text = getText(data.strID)
                    }
                }

                launch {
                    viewModel.sleepOptionSelect.collect { data ->
                        binding.settingsSleepSelected.text = getText(data.strID)
                    }
                }

                launch {
                    viewModel.skinGroupSelect.collect { data ->
                        binding.settingsSkinGroupSelected.text = getText(data.strID)
                    }
                }

                launch {
                    viewModel.modeSelect.collect { data ->
                        binding.settingsModeSelected.text = getText(data.strID)
                    }
                }
            }
        }
    }
}