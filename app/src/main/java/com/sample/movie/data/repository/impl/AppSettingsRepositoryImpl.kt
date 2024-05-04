package com.sample.movie.data.repository.impl

import com.sample.movie.data.local.model.AppLanguage
import com.sample.movie.data.local.model.AppTheme
import com.sample.movie.data.local.model.DarkMode
import com.sample.movie.data.local.datastore.AppSettingsDataSource
import com.sample.movie.data.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    private val dataSource: AppSettingsDataSource
) : AppSettingsRepository {
    override val languageStream: Flow<AppLanguage> = dataSource.language

    override val darkModeStream: Flow<DarkMode> = dataSource.darkMode

    override val themeStream: Flow<AppTheme> = dataSource.theme

    override suspend fun setLanguage(appLanguage: AppLanguage) {
        dataSource.setLanguage(appLanguage)
    }

    override suspend fun setDarkMode(darkMode: DarkMode) {
        dataSource.setDarkMode(darkMode)
    }

    override suspend fun setTheme(appTheme: AppTheme) {
        dataSource.setTheme(appTheme)
    }
}