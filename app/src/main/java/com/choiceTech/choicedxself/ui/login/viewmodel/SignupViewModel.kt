package com.choiceTech.choicedxself.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choiceTech.choicedxself.core.domain.usecase.CrmUseCase
import com.choiceTech.choicedxself.core.event.CrmEvent
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
class SignupViewModel @Inject constructor(
    private val crmUseCase: CrmUseCase
): ViewModel() {
    private val _email = MutableStateFlow<String?>(null)
    private val _password = MutableStateFlow<String?>(null)
    private val _confirm = MutableStateFlow<String?>(null)

    private val _emailAgree = MutableStateFlow(false)
    private val _privacyAgree = MutableStateFlow(false)

    private val _inputState = combine(
        _email, _password, _confirm
    ) { email, password, confirm ->
        !email.isNullOrEmpty() && !password.isNullOrEmpty() && !confirm.isNullOrEmpty()
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _isCheckStats = combine(
        _emailAgree, _privacyAgree
    ) { email, privacy ->
        email && privacy
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    val isSignupEnabled = combine(
        _inputState, _isCheckStats
    ) { input, check ->
        input && check
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _apiState = MutableStateFlow<ApiUiState>(ApiUiState.Idle)
    val apiState = _apiState.asStateFlow()

    fun getInput(type: SignupInputType, input: String) {
        when(type) {
            SignupInputType.EMAIL -> _email.value = input
            SignupInputType.PASSWORD -> _password.value = input
            SignupInputType.CONFIRM -> _confirm.value = input
        }
    }

    fun getCheck(type: SignupCheckType, isCheck: Boolean) {
        when(type) {
            SignupCheckType.EMAIL_AGREE -> _emailAgree.value = isCheck
            SignupCheckType.PRIVACY_AGREE -> _privacyAgree.value = isCheck
        }
    }

    fun requestSignup() {
        _apiState.value = ApiUiState.Loading

        viewModelScope.launch {
            when(crmUseCase.signup(_email.value!!, _password.value!!)) {
                is CrmEvent.Signup.Failer -> _apiState.value = ApiUiState.Success
                CrmEvent.Signup.Success -> _apiState.value = ApiUiState.Failer
            }
        }
    }
}

enum class SignupInputType {
    EMAIL,
    PASSWORD,
    CONFIRM
}

enum class SignupCheckType {
    EMAIL_AGREE,
    PRIVACY_AGREE
}