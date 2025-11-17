package com.choiceTech.choicedxself.core.utils

import com.chibatching.kotpref.KotprefModel

object SharedPref: KotprefModel() {}

object Settings: KotprefModel() {
    var resultSummary by intPref(0)
    var analysisMode by intPref(0)
    var sleepOption by intPref(0)
    var skinGroupSelection by intPref(0)
    var mode by intPref(0)
}