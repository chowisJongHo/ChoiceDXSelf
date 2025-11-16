package com.choiceTech.choicedxself.core.event

import com.choicetech.sdk.util.CWErrorData

sealed class CrmEvent {
    sealed class Login {
        object Success : Login()
        data class Failer(val errorData: CWErrorData) : Login()
    }

    sealed class Signup {
        object Success : Signup()
        data class Failer(val errorData: CWErrorData) : Signup()
    }

    sealed class Logout {
        object Success: Logout()
        object Failer: Logout()
    }
}