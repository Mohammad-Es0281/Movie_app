package com.sample.movie.app.exception

sealed class UiException(message: String? = null) : Exception(message) {
    class ClientOffline(message: String? = null) : UiException(message)
    class ServerConnectionFailed(message: String? = null) : UiException(message)
    class LowStorage(message: String? = null) : UiException(message)
    class Unknown(message: String? = null) : UiException(message)
}