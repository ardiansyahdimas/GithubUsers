package com.dicoding.githubusers.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubusers.databinding.ActivitySettingBinding
import com.dicoding.githubusers.ui.setting.SettingPreferences
import com.dicoding.githubusers.ui.viewmodel.SettingViewModel
import com.dicoding.githubusers.ui.viewmodel.SettingViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Github Setting"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.switchTheme.isChecked = false
                }
            })
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}