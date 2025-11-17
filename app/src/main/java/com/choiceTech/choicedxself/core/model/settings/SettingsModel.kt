package com.choiceTech.choicedxself.core.model.settings

import com.choiceTech.choicedxself.R

enum class SettingsSelected {
    DEVICE,
    PRODUCT
}

enum class SettingsGeneralSelected {
    RESULT_SUMMARY,
    ANALYSIS_MODE,
    SLEEP_OPTION,
    SKIN_GROUP_SELECTION,
    MODE
}

enum class ResultSummary(val position: Int, val strID: Int) {
    SKIN_HEALTH_SCORE(0, R.string.skin_health_score),
    SKIN_AGE(1, R.string.skin_age);

    companion object {
        fun fromPosition(position: Int): ResultSummary {
            return entries.find { it.position == position } ?: SKIN_HEALTH_SCORE
        }
    }
}

enum class AnalysisMode(val position: Int, val strID: Int) {
    ONLINE(0, R.string.settings_online_analysis),
    OFFLINE(1, R.string.settings_offline_analysis),
    WITH_OUT(2, R.string.measurement_without_images);

    companion object {
        fun fromPosition(position: Int): AnalysisMode {
            return entries.find { it.position == position } ?: ONLINE
        }
    }
}

enum class SleepOption(val position: Int, val strID: Int) {
    MIN30(0, R.string.sleep_option_30),
    NEVER(1, R.string.sleep_option_never);

    companion object {
        fun fromPosition(position: Int): SleepOption {
            return entries.find { it.position == position } ?: MIN30
        }
    }
}

enum class SkinGroupSelection(val position: Int, val strID: Int) {
    AUTO(0, R.string.selection_auto),
    AUTO_MANUAL(1, R.string.selection_auto_manual),
    DISABLE(2, R.string.option_disable);

    companion object {
        fun fromPosition(position: Int): SkinGroupSelection {
            return entries.find { it.position == position } ?: AUTO
        }
    }
}

enum class Mode(val position: Int, val strID: Int) {
    SELECTABLE(0, R.string.selectable),
    SKIN_ONLY(1, R.string.skin_only),
    HAIR_ONLY(2, R.string.hair_only);

    companion object {
        fun fromPosition(position: Int): Mode {
            return entries.find { it.position == position } ?: SELECTABLE
        }
    }
}