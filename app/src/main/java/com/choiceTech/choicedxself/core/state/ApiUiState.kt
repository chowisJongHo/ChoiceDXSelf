package com.choiceTech.choicedxself.core.state

sealed class ApiUiState {
    object Idle: ApiUiState()
    object Loading: ApiUiState()
    object Success: ApiUiState()
    object Failer: ApiUiState()
}