package com.choiceTech.choicedxself.core.data

import android.content.Context
import com.choiceTech.choicedxself.core.config.Constants
import com.choiceTech.choicedxself.core.domain.repository.CrmRepository
import com.choiceTech.choicedxself.core.event.CrmEvent
import com.choicetech.sdk.login.network.CTKLoginAPI
import com.choicetech.sdk.login.network.request.RequestLoginInfo
import com.choicetech.sdk.util.CWErrorData
import com.choicetech.sdk.util.ResponseCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CrmRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CrmRepository {
    private val loginCRM = CTKLoginAPI.getInstance().apply {
        initialize(
            context,
            Constants.APP_ID,
            Constants.USER_TYPE,
            Constants.SERVER_TYPE,
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

    override suspend fun signup(email: String, password: String): CrmEvent.Signup =
        suspendCoroutine { continuation ->
            val signupInfo = RequestLoginInfo.builder()
                .email("")
                .password("")
                .build()

            loginCRM.requestSignup(object : ResponseCallback<Any?>() {
                override fun onDidStart() {}
                override fun onFailure(p0: CWErrorData) {
                    continuation.resume(CrmEvent.Signup.Failer(p0))
                }

                override fun onError(p0: CWErrorData) {
                    continuation.resume(CrmEvent.Signup.Failer(p0))
                }

                override fun onSuccess(p0: Any?) {
                    continuation.resume(CrmEvent.Signup.Success)
                }

            }, signupInfo)
        }

    override suspend fun logout(): CrmEvent.Logout =
        suspendCoroutine { continuation ->
            loginCRM.onRequestLogout(object : ResponseCallback<Any>() {
                override fun onFailure(p0: CWErrorData?) {}
                override fun onError(p0: CWErrorData?) {}
                override fun onDidStart() {}
                override fun onSuccess(p0: Any?) {}
            })
    }
}