package com.choiceTech.choicedxself.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choiceTech.choicedxself.core.domain.usecase.CrmUseCase
import com.choiceTech.choicedxself.core.event.CrmEvent
import com.choiceTech.choicedxself.core.state.ApiUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterOpticViewModel @Inject constructor(
    private val crmUseCase: CrmUseCase
): ViewModel() {
    private val _apiStatus = MutableStateFlow<ApiUiState>(ApiUiState.Idle)

    fun registerOptic(opticNumber: String) {
        _apiStatus.value = ApiUiState.Loading

        viewModelScope.launch {
            when(val event = crmUseCase.productEnter(opticNumber)) {
                is CrmEvent.RegisterOptic.Failer -> TODO()
                CrmEvent.RegisterOptic.Success -> TODO()
            }
        }
    }
}