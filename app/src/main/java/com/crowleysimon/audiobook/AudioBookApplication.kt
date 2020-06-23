package com.crowleysimon.audiobook

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class AudioBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isNightMode = getSharedPreferences("GENERAL", MODE_PRIVATE).getBoolean("NIGHT_MODE", false)
        AppCompatDelegate.setDefaultNightMode(if (isNightMode) MODE_NIGHT_YES else MODE_NIGHT_NO)
    }
}