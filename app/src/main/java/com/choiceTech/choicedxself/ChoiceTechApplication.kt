package com.choiceTech.choicedxself

import android.app.Application
import com.chibatching.kotpref.Kotpref
import timber.log.Timber

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