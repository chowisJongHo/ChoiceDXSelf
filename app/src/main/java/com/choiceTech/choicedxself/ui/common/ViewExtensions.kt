package com.choiceTech.choicedxself.ui.common

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.choiceTech.choicedxself.R
import com.choiceTech.choicedxself.core.state.ApiUiState
import kotlinx.coroutines.flow.StateFlow

fun Button.animationState(
    unEnableColor: Int,
    enableColor: Int,
    isEnabled: Boolean,
    duration: Long = 300L
) {
    val context = this.context
    val fromColor = backgroundTintList?.defaultColor ?: ContextCompat.getColor(
        context,
        unEnableColor
    )
    val toColorRes = if (isEnabled) enableColor else unEnableColor
    val toColor = ContextCompat.getColor(context, toColorRes)

    this.isEnabled = isEnabled
    animateFillTint(fromColor, toColor, duration)
}

private fun View.animateFillTint(
    @ColorInt fromColor: Int,
    @ColorInt toColor: Int,
    duration: Long = 300L
) {
    val animator = ValueAnimator.ofObject(
        ArgbEvaluator(), fromColor, toColor
    ).apply {
        this.duration = duration
        addUpdateListener {
            val color = it.animatedValue as Int
            backgroundTintList = ColorStateList.valueOf(color)
        }
    }
    animator.start()
}

suspend fun StateFlow<ApiUiState>.collectHandler(
    onShowLoading: () -> Unit,
    onHideLoading: () -> Unit,
    onSuccess: () -> Unit = {},
    onFailure: () -> Unit = {},
    onIdle: () -> Unit = {}
) {
    this.collect { state ->
        when(state) {
            ApiUiState.Idle -> {
                onHideLoading()
                onIdle()
            }

            ApiUiState.Loading -> onShowLoading()
            ApiUiState.Success -> {
                onHideLoading()
                onSuccess()
            }
            ApiUiState.Failer -> {
                onHideLoading()
                onFailure()
            }
        }
    }
}