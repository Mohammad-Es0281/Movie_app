package com.sample.movie.presentation.main

import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sample.movie.NavGraphs
import com.sample.movie.app.theme.AppColors
import com.sample.movie.app.theme.MovieTheme
import com.sample.movie.presentation.setting.SettingsViewModel
import com.sample.movie.provider.AppNavigationProvider
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

@Composable
fun MainRoot(
    settingsViewModel: SettingsViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    window: Window,
    finish: () -> Unit,
) {
    val navController = rememberNavController()

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val destination =
        currentBackStackEntryAsState?.destination?.route ?: NavGraphs.root.startRoute.route
    if (destination == NavGraphs.root.startRoute.route) { BackHandler { finish() } }

    MovieTheme(
        darkMode = settingsViewModel.darkMode,
        appTheme = settingsViewModel.appTheme,
        language = settingsViewModel.languagePref.code
    ) {
        window.navigationBarColor = AppColors.primary.toArgb()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColors.background
        ) {
            DestinationsNavHost(
                navController = navController,
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(AppNavigationProvider(navController))
                }
            )
        }
    }
}