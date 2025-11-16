package com.choiceTech.choicedxself.core.domain.repository

import com.choiceTech.choicedxself.core.data.CrmEvent
import com.choicetech.sdk.login.network.request.RequestLoginInfo

interface CrmRepository {
    suspend fun login(email: String, password: String): CrmEvent.Login
    suspend fun signup(email: String, password: String): CrmEvent.Signup
}