package com.choiceTech.choicedxself.custom.dialog

import android.view.LayoutInflater
import com.choiceTech.choicedxself.R
import com.choiceTech.choicedxself.core.base.EventDialog
import com.choiceTech.choicedxself.databinding.DialogLoginBinding

class LoginEventDialog(
    val mode: LoginDialogMode,
    val isSuccess: Boolean,
    val onClickButton: (() -> Unit)? = null
): EventDialog<DialogLoginBinding>() {
    override fun inflateBinding(inflater: LayoutInflater) = DialogLoginBinding.inflate(inflater)
    override fun dialogWidth(): Int = com.intuit.sdp.R.dimen._191sdp
    override fun dialogHeight(): Int = com.intuit.sdp.R.dimen._204sdp

    override fun onInit() {
        val lottieRaw = if (isSuccess) R.raw.checkmark else R.raw.wrongmark
        binding.loginEventLottie.setAnimation(lottieRaw)

        binding.loginDialogButton.setOnClickListener {
            dismiss()
            if (mode == LoginDialogMode.SIGN_UP) onClickButton?.invoke()
        }
    }
}

enum class LoginDialogMode {
    LOGIN,
    SIGN_UP
}