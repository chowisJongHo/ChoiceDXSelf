package com.choiceTech.choicedxself.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choiceTech.choicedxself.core.data.CrmEvent
import com.choiceTech.choicedxself.core.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    fun requestLogin(email: String, password: String) {
        viewModelScope.launch {
            when(val event = loginUseCase.login(email, password)) {
                is CrmEvent.Login.Failer -> Timber.d("DEBUG login ${event.errorData}")
                CrmEvent.Login.Success -> Timber.d("DEBUG login Success")
            }
        }
    }
}