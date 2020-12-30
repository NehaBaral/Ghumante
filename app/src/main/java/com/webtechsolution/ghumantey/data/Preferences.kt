package com.webtechsolution.ghumantey.data

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(@ApplicationContext val context: Context) {
    private val userPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val devicePreferences = context.getSharedPreferences(DEVICE_PREFERENCES,Context.MODE_PRIVATE)
    var dialogShown:Boolean
    get() = userPreferences.getBoolean(SCREEN_LOADED,true)
    set(value) = userPreferences.edit { putBoolean(SCREEN_LOADED,value) }

    var fcmToken:String?
        get() = devicePreferences.getString(KEY_FCM_TOKEN,null)
        set(value) = devicePreferences.edit { putString(KEY_FCM_TOKEN,value) }

    var fcmTokenUploaded:Boolean
    get() = userPreferences.getBoolean(KEY_FCM_TOKEN_UPLOADED,true)
    set(value) = userPreferences.edit { putBoolean(KEY_FCM_TOKEN_UPLOADED,value).apply() }

    companion object KEYS {
        const val SYNC_TIME = "SYNC_TIME"
        const val DEVICE_PREFERENCES = "DEVICE_PREFERENCES"
        const val KEY_USER = "USER_DATA"
        const val KEY_FCM_TOKEN = "FCM_TOKEN"
        const val KEY_FCM_TOKEN_UPLOADED = "FCM_TOKEN"
        const val SCREEN_LOADED ="SCREEN_LOADED"
    }
}