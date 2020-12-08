package com.webtechsolution.ghumantey.app

import android.app.Application
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.webtechsolution.ghumantey.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Ghumantey: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun restartProcess(logOut: Boolean = false) {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            Runtime.getRuntime().exit(0)
        }, 100)
    }


}