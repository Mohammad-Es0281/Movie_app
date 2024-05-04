package com.sample.movie.data.repository

import com.sample.movie.data.local.model.AppLanguage
import com.sample.movie.data.local.model.AppTheme
import com.sample.movie.data.local.model.DarkMode
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val languageStream: Flow<AppLanguage>
    val darkModeStream: Flow<DarkMode>
    val themeStream: Flow<AppTheme>

    suspend fun setLanguage(appLanguage: AppLanguage)
    suspend fun setDarkMode(darkMode: DarkMode)
    suspend fun setTheme(appTheme: AppTheme)
}
