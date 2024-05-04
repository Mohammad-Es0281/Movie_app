package com.sample.movie.app.extension

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

fun Flow<Preferences>.catchIO() = this.catch { exception ->
    if (exception is IOException)
        emit(emptyPreferences())
    else
        throw exception
}
