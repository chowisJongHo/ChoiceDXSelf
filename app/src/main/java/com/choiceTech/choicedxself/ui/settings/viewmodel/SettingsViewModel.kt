package com.choiceTech.choicedxself.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choiceTech.choicedxself.R
import com.choiceTech.choicedxself.core.domain.usecase.CrmUseCase
import com.choiceTech.choicedxself.core.domain.usecase.SettingsUseCase
import com.choiceTech.choicedxself.core.event.CrmEvent
import com.choiceTech.choicedxself.core.model.settings.AnalysisMode
import com.choiceTech.choicedxself.core.model.settings.Mode
import com.choiceTech.choicedxself.core.model.settings.ResultSummary
import com.choiceTech.choicedxself.core.model.settings.SettingsGeneralSelected
import com.choiceTech.choicedxself.core.model.settings.SettingsSelected
import com.choiceTech.choicedxself.core.model.settings.SkinGroupSelection
import com.choiceTech.choicedxself.core.model.settings.SleepOption
import com.choiceTech.choicedxself.core.state.ApiUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val crmUseCase: CrmUseCase,
    private val settingsUseCase: SettingsUseCase
): ViewModel() {
    private val _settingsShared = MutableSharedFlow<SettingsSelected>()
    val settingsShared = _settingsShared.asSharedFlow()

    private val _crmState = MutableStateFlow<ApiUiState>(ApiUiState.Idle)
    val crmState = _crmState.asStateFlow()

    private val _resultSummaryToggle = MutableStateFlow(false)
    private val _analysisModeToggle = MutableStateFlow(false)
    private val _sleepOptionToggle = MutableStateFlow(false)
    private val _skinGroupSelectionToggle = MutableStateFlow(false)
    private val _modeToggle = MutableStateFlow(false)

    val resultSummaryToggle = _resultSummaryToggle.asStateFlow()
    val analysisModeToggle = _analysisModeToggle.asStateFlow()
    val sleepOptionToggle = _sleepOptionToggle.asStateFlow()
    val skinGroupSelectionToggle = _skinGroupSelectionToggle.asStateFlow()
    val modeToggle = _modeToggle.asStateFlow()

    private val _resultSummarySelect = MutableStateFlow(ResultSummary.SKIN_HEALTH_SCORE)
    private val _analysisModeSelect = MutableStateFlow(AnalysisMode.ONLINE)
    private val _sleepOptionSelect = MutableStateFlow(SleepOption.MIN30)
    private val _skinGroupSelect = MutableStateFlow(SkinGroupSelection.AUTO)
    private val _modeSelect = MutableStateFlow(Mode.SELECTABLE)

    val resultSummarySelect = _resultSummarySelect.asStateFlow()
    val analysisModeSelect = _analysisModeSelect.asStateFlow()
    val sleepOptionSelect = _sleepOptionSelect.asStateFlow()
    val skinGroupSelect = _skinGroupSelect.asStateFlow()
    val modeSelect = _modeSelect.asStateFlow()

    init {
        val data = settingsUseCase.settingsInit()

        _resultSummarySelect.value = ResultSummary.fromPosition(data.resultSummaryPosition)
        _analysisModeSelect.value = AnalysisMode.fromPosition(data.analysisModePosition)
        _sleepOptionSelect.value = SleepOption.fromPosition(data.sleepOptionPosition)
        _skinGroupSelect.value = SkinGroupSelection.fromPosition(data.skinGroupSelectionPosition)
        _modeSelect.value = Mode.fromPosition(data.modePosition)
    }

    fun onSettingsClick(selected: SettingsSelected) {
        viewModelScope.launch {
            _settingsShared.emit(selected)
        }
    }

    fun requestLogout() {
        _crmState.value = ApiUiState.Loading

        viewModelScope.launch {
            _crmState.value = when(crmUseCase.logout()) {
                CrmEvent.Logout.Failer -> ApiUiState.Failer
                CrmEvent.Logout.Success -> ApiUiState.Success
            }
        }
    }

    fun onSettingsGeneralClick(selected: SettingsGeneralSelected) {
        generalToggle(selected)
    }

    private fun generalToggle(selected: SettingsGeneralSelected) {
        val targetToggle = when(selected) {
            SettingsGeneralSelected.RESULT_SUMMARY -> _resultSummaryToggle
            SettingsGeneralSelected.ANALYSIS_MODE -> _analysisModeToggle
            SettingsGeneralSelected.SLEEP_OPTION -> _sleepOptionToggle
            SettingsGeneralSelected.SKIN_GROUP_SELECTION -> _skinGroupSelectionToggle
            SettingsGeneralSelected.MODE -> _modeToggle
        }

        targetToggle.value = !targetToggle.value
    }

    fun getResultSummarySelect(select: ResultSummary) {
        _resultSummarySelect.value = select
        settingsUseCase.saveResultSummary(select.position)

        generalToggle(SettingsGeneralSelected.RESULT_SUMMARY)
    }

    fun getAnalysisModeSelect(select: AnalysisMode) {
        _analysisModeSelect.value = select
        settingsUseCase.saveAnalysisMode(select.position)

        generalToggle(SettingsGeneralSelected.ANALYSIS_MODE)
    }

    fun getSleepOptionSelect(select: SleepOption) {
        _sleepOptionSelect.value = select
        settingsUseCase.saveSleepOption(select.position)

        generalToggle(SettingsGeneralSelected.SLEEP_OPTION)
    }

    fun getSkinGroupSelect(select: SkinGroupSelection) {
        _skinGroupSelect.value = select
        settingsUseCase.saveSkinGroupSelection(select.position)

        generalToggle(SettingsGeneralSelected.SKIN_GROUP_SELECTION)
    }

    fun getModeSelect(select: Mode) {
        _modeSelect.value = select
        settingsUseCase.saveMode(select.position)

        generalToggle(SettingsGeneralSelected.MODE)
    }
}