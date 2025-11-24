package com.choiceTech.choicedxself.core.domain.repository

import com.choiceTech.choicedxself.core.event.CrmEvent

interface CrmRepository {
    suspend fun login(email: String, password: String): CrmEvent.Login
    suspend fun signup(email: String, password: String): CrmEvent.Signup
    suspend fun logout(): CrmEvent.Logout
    suspend fun productEnter(opticNumber: String): CrmEvent.RegisterOptic
}