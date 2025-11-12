package com.choiceTech.choicedxself.core.domain.repository

import com.choiceTech.choicedxself.core.data.CrmEvent

interface CrmRepository {
    suspend fun login(email: String, password: String): CrmEvent.Login
}