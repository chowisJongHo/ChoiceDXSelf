package com.choiceTech.choicedxself

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ChoiceTechApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Kotpref.init(this)
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format("[%s##%s:%s]", super.createStackElementTag(element), element.methodName, element.lineNumber)
            }
        })
    }
}