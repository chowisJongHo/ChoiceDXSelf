package com.choiceTech.choicedxself.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _settingsShared = MutableSharedFlow<Unit>()
    val settingsShared = _settingsShared.asSharedFlow()

    fun getVersionName(): String {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return "Ver.${packageInfo.versionName}"
    }

    fun onSettingsButton() {
        viewModelScope.launch {
            _settingsShared.emit(Unit)
        }
    }
}