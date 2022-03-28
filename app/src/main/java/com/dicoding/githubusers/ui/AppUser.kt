package com.dicoding.githubusers.ui

import android.app.Application
import android.content.Context

class AppUser : Application() {
    companion object {
        private lateinit var appContext : Context
        val context: Context
            get() = appContext
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}