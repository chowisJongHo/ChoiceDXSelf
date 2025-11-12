package com.choiceTech.choicedxself.core.data

import android.content.Context
import com.choiceTech.choicedxself.core.domain.repository.CrmRepository
import com.choicetech.sdk.login.network.CTKLoginAPI
import com.choicetech.sdk.util.CWErrorData
import com.choicetech.sdk.util.ResponseCallback
import com.choicetech.sdk.util.ServerType
import com.choicetech.sdk.util.UserType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CrmRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): CrmRepository {
    private val loginCRM = CTKLoginAPI.getInstance().apply {
        initialize(
            context,
            144,
            UserType.COUNSELOR,
            ServerType.PRODUCTION,
            "ko"
        )
    }

    override suspend fun login(email: String, password: String): CrmEvent.Login =
        suspendCoroutine { continuation ->
        loginCRM.requestLogin(object : ResponseCallback<Any?>() {
            override fun onDidStart() {}
            override fun onFailure(p0: CWErrorData) {
                continuation.resume(CrmEvent.Login.Failer(p0))
            }

            override fun onError(p0: CWErrorData) {
                continuation.resume(CrmEvent.Login.Failer(p0))
            }

            override fun onSuccess(p0: Any?) {
                continuation.resume(CrmEvent.Login.Success)
            }

        }, email, password)
    }
}

sealed class CrmEvent {
    sealed class Login {
        object Success: Login()
        data class Failer(val errorData: CWErrorData): Login()
    }
}