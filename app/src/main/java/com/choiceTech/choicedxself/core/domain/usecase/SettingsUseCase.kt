package com.choiceTech.choicedxself.core.domain.usecase

import com.choiceTech.choicedxself.core.utils.Settings
import javax.inject.Inject

class SettingsUseCase @Inject constructor(

) {
    fun saveResultSummary(position: Int) {
        Settings.resultSummary = position
    }

    private fun getResultSummary(): Int {
        return Settings.resultSummary
    }

    fun saveAnalysisMode(position: Int) {
        Settings.analysisMode = position
    }

    private fun getAnalysisMode(): Int {
        return Settings.analysisMode
    }

    fun saveSleepOption(position: Int) {
        Settings.sleepOption = position
    }

    private fun getSleepOption(): Int {
        return Settings.sleepOption
    }

    fun saveSkinGroupSelection(position: Int) {
        Settings.skinGroupSelection = position
    }

    private fun getSkinGroupSelection(): Int {
        return Settings.skinGroupSelection
    }

    fun saveMode(position: Int) {
        Settings.mode = position
    }

    fun getMode(): Int {
        return Settings.mode
    }

    fun settingsInit(): SettingsInit {
        return SettingsInit(
            getResultSummary(),
            getAnalysisMode(),
            getSleepOption(),
            getSkinGroupSelection(),
            getMode()
        )
    }
}

data class SettingsInit(
    val resultSummaryPosition: Int,
    val analysisModePosition: Int,
    val sleepOptionPosition: Int,
    val skinGroupSelectionPosition: Int,
    val modePosition: Int
)