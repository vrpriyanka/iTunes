package com.ragini.artistdataitunes

import android.app.Application
import com.ragini.artistdataitunes.di.PrefsModule
import com.ragini.artistdataitunes.util.SharedPreferencesHelper

class PrefsModuleTest(private val mockPrefs: SharedPreferencesHelper): PrefsModule() {
    override fun provideSharedPreferences(app: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}