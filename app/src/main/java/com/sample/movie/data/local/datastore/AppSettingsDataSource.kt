package com.sample.movie.data.local.datastore

import com.sample.movie.data.local.model.AppLanguage
import com.sample.movie.data.local.model.AppTheme
import com.sample.movie.data.local.model.DarkMode
import kotlinx.coroutines.flow.Flow

interface AppSettingsDataSource {
    suspend fun setLanguage(language: AppLanguage)
    val language: Flow<AppLanguage>

    suspend fun setDarkMode(darkMode: DarkMode)
    val darkMode: Flow<DarkMode>

    suspend fun setTheme(appTheme: AppTheme)
    val theme: Flow<AppTheme>
}