package com.choiceTech.choicedxself.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseFragmentViewModel @Inject constructor(
): ViewModel() {
    private val _isShowLoading = MutableSharedFlow<Boolean>()
    val isShowLoading = _isShowLoading.asSharedFlow()

    fun shouldLoading(isShow: Boolean = false) {
        viewModelScope.launch {
            _isShowLoading.emit(isShow)
        }
    }
}