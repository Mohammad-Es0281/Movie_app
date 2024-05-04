package com.sample.movie.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sample.movie.app.extension.catchIO
import com.sample.movie.data.local.datastore.di.AppSettingsDataStore
import com.sample.movie.data.local.model.AppLanguage
import com.sample.movie.data.local.model.AppTheme
import com.sample.movie.data.local.model.DarkMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AppSettingsDataSourceImpl @Inject constructor(
    @AppSettingsDataStore private val dataStore: DataStore<Preferences>
) : AppSettingsDataSource {
    private val defaultLanguage = AppLanguage.English
    private val defaultDarkMode = DarkMode.System
    private val defaultTheme = AppTheme.System

    private object PreferencesKey {
        val languageKey = stringPreferencesKey(name = "language_key")
        val darkModeKey = stringPreferencesKey(name = "dark_mode_key")
        val appThemeKey = stringPreferencesKey(name = "theme_key")
    }

    override suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.languageKey] = language.code
        }
    }

    override val language: Flow<AppLanguage> = dataStore.data
        .catchIO()
        .map { preferences ->
            when (preferences[PreferencesKey.languageKey]) {
                "en" -> AppLanguage.English
                "fa" -> AppLanguage.Persian
                else -> defaultLanguage
            }
        }

    override suspend fun setDarkMode(darkMode: DarkMode) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.darkModeKey] = darkMode.toString()
        }
    }

    override val darkMode: Flow<DarkMode>
        get() = dataStore.data
            .catchIO()
            .map { preferences ->
                when (preferences[PreferencesKey.darkModeKey]) {
                    DarkMode.System.toString() -> DarkMode.System
                    DarkMode.On.toString() -> DarkMode.On
                    DarkMode.Off.toString() -> DarkMode.Off
                    else -> defaultDarkMode
                }
            }

    override suspend fun setTheme(appTheme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.appThemeKey] = appTheme.toString()
        }
    }

    override val theme: Flow<AppTheme> = dataStore.data
        .catchIO()
        .map { preferences ->
            when (preferences[PreferencesKey.appThemeKey]) {
                AppTheme.Default.toString() -> AppTheme.Default
                AppTheme.System.toString() -> AppTheme.System
                else -> defaultTheme
            }
        }
}