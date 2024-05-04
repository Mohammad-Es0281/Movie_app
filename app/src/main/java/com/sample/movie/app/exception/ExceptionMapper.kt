package com.sample.movie.app.exception

import android.database.sqlite.SQLiteFullException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.asUiException(): UiException {
    return this
        .mapToUiException()
        .takeIf { it !is UiException.Unknown }
        ?: this.cause.mapToUiException()
}

private fun Throwable?.mapToUiException(): UiException {
    return when (this) {
        is NetworkUnavailableException -> UiException.ClientOffline()

        is ServerConnectionException -> UiException.ServerConnectionFailed()
        is HttpException -> UiException.ServerConnectionFailed()
        is SocketTimeoutException -> UiException.ServerConnectionFailed()
        is UnknownHostException -> UiException.ServerConnectionFailed()

        is SQLiteFullException -> UiException.LowStorage()

        else -> UiException.Unknown()
    }
}