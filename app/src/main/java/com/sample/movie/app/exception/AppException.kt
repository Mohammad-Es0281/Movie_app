package com.sample.movie.app.exception

class NetworkUnavailableException(message: String? = null): Exception(message)

class ServerConnectionException(message: String? = null): Exception(message)
