package com.choiceTech.choicedxself.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choiceTech.choicedxself.core.data.CrmEvent
import com.choiceTech.choicedxself.core.domain.usecase.CrmUseCase
import com.choiceTech.choicedxself.core.state.ApiUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val crmUseCase: CrmUseCase
): ViewModel() {
    private val _userEmail = MutableStateFlow<String?>(null)
    private val _userPassword = MutableStateFlow<String?>(null)

    private val _apiState = MutableStateFlow<ApiUiState>(ApiUiState.Idle)
    val apiState = _apiState.asStateFlow()

    val isLoginEnabled = combine(
        _userEmail, _userPassword
    ) { email, password ->
        !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun requestLogin() {
        viewModelScope.launch {
            _apiState.value = ApiUiState.Loading

            when(val event = crmUseCase.login(
                _userEmail.value.toString(), _userPassword.value.toString()
            )) {
                is CrmEvent.Login.Failer -> _apiState.value = ApiUiState.Failer
                CrmEvent.Login.Success -> _apiState.value = ApiUiState.Success
            }
        }
    }

    fun getLoginEditText(type: LoginEditType, userInput: String) {
        when(type) {
            LoginEditType.EMAIL -> _userEmail.value = userInput
            LoginEditType.PASSWORD -> _userPassword.value = userInput
        }
    }
}

enum class LoginEditType {
    EMAIL,
    PASSWORD
}