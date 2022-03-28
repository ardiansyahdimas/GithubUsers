package com.dicoding.githubusers.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubusers.R
import com.dicoding.githubusers.ui.setting.SettingPreferences
import com.dicoding.githubusers.ui.viewmodel.SettingViewModel
import com.dicoding.githubusers.ui.viewmodel.SettingViewModelFactory

class SplashScreen : AppCompatActivity() {
    private val android.content.Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var settingViewModel: SettingViewModel
    companion object {
        const val WAITING = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this@SplashScreen,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, WAITING.toLong())
    }
}