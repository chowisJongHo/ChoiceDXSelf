package com.choiceTech.choicedxself.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choiceTech.choicedxself.core.domain.usecase.CrmUseCase
import com.choiceTech.choicedxself.core.event.CrmEvent
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
    private val crmUseCase: CrmUseCase
): ViewModel() {
    private val _settingsShared = MutableSharedFlow<SettingsSelected>()
    val settingsShared = _settingsShared.asSharedFlow()

    private val _crmState = MutableStateFlow<ApiUiState>(ApiUiState.Idle)
    val crmState = _crmState.asStateFlow()

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
}

enum class SettingsSelected {
    DEVICE,
    PRODUCT
}