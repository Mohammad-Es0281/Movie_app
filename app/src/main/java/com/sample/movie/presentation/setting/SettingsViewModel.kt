package com.sample.movie.presentation.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sample.movie.app.architecture.mvvm.MvvmViewModel
import com.sample.movie.data.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: AppSettingsRepository
) : MvvmViewModel() {

    var languagePref by
        mutableStateOf(value = runBlocking { repository.languageStream.first() })
        private set

    var darkMode by
        mutableStateOf(value = runBlocking { repository.darkModeStream.first() })
        private set

    var appTheme by
        mutableStateOf(value = runBlocking { repository.themeStream.first() })
        private set

    init {
        collectLanguage()
        collectDarkMode()
        collectTheme()
    }

    private fun collectLanguage() {
        safeLaunch {
            repository.languageStream.collectLatest { languagePref = it }
        }
    }

    private fun collectDarkMode() {
        safeLaunch {
            repository.darkModeStream.collectLatest { darkMode = it }
        }
    }

    private fun collectTheme() {
        safeLaunch {
            repository.themeStream.collectLatest { appTheme = it }
        }
    }
}