package com.choiceTech.choicedxself.core.utils

import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object SharedPref: KotprefModel() {}

object Settings: KotprefModel() {
    var resultSummary by intPref(0)
    var analysisMode by intPref(0)
    var sleepOption by intPref(0)
    var skinGroupSelection by intPref(0)
    var mode by intPref(0)

    private val gson = Gson()

    fun <T> listPref(
        default: List<T> = emptyList(),
        clazz: Class<T>
    ): ReadWriteProperty<KotprefModel, List<T>> {
        val stringDelegate = stringPref("[]")

        return object : ReadWriteProperty<KotprefModel, List<T>> {
            override fun getValue(
                thisRef: KotprefModel,
                property: KProperty<*>
            ): List<T> {
                val json = stringDelegate.getValue(thisRef, property)
                if (json.isEmpty()) return default

                return try {
                    val type = TypeToken.getParameterized(List::class.java, clazz).type
                    gson.fromJson(json, type)
                } catch (e: Exception) {
                    default
                }
            }

            override fun setValue(
                thisRef: KotprefModel,
                property: KProperty<*>,
                value: List<T>
            ) {
                val json = try {
                    gson.toJson(value)
                } catch (e: Exception) {
                    "[]"
                }
                stringDelegate.setValue(thisRef, property, json)
            }
        }
    }
}