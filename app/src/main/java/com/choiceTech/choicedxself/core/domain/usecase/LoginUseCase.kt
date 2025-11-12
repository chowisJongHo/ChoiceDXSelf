package com.choiceTech.choicedxself.core.domain.usecase

import com.choiceTech.choicedxself.core.domain.repository.CrmRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: CrmRepository
) {
    suspend fun login(email: String, password: String) = loginRepository.login(email, password)
}