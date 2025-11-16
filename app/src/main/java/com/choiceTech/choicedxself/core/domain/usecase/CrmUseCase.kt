package com.choiceTech.choicedxself.core.domain.usecase

import com.choiceTech.choicedxself.core.domain.repository.CrmRepository
import javax.inject.Inject

class CrmUseCase @Inject constructor(
    private val crmRepository: CrmRepository
) {
    suspend fun login(email: String, password: String) = crmRepository.login(email, password)
    suspend fun signup(email: String, password: String) = crmRepository.signup(email, password)
}