package com.sample.movie.data.local.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sample.movie.data.local.datastore.AppSettingsDataSource
import com.sample.movie.data.local.datastore.AppSettingsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreNonStaticModule {
    @Provides
    @Singleton
    @AppSettingsDataStore
    fun provideAppSettingsDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { ex ->
                    Timber.i(ex)
                    emptyPreferences()
                }
            ),
            produceFile = {
                context.preferencesDataStoreFile(APP_SETTINGS_PREF_NAME)
            }
        )
    }

    private const val APP_SETTINGS_PREF_NAME = "app_settings_pref"
}


@Module
@InstallIn(SingletonComponent::class)
interface DataStoreStaticModule {
    @Singleton
    @Binds
    fun bindsAppSettingsDataSource(repository: AppSettingsDataSourceImpl): AppSettingsDataSource
}
