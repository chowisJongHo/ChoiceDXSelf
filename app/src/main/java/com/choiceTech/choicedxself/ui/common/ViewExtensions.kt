package com.choiceTech.choicedxself.ui.common

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Dialog
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
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

fun ImageView.animateRotate(rotateUp: Boolean) {
    val fromDegrees = if (rotateUp) 0f else 90f
    val toDegrees = if (rotateUp) 90f else 0f
    val anim = RotateAnimation(
        fromDegrees,
        toDegrees,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 200
        fillAfter = true
    }

    this.startAnimation(anim)
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