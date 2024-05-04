package com.sample.movie.app.extension

val Any.classTag: String get() = this.javaClass.canonicalName.orEmpty()

inline fun <reified T : Any> Any.cast() = this as T

inline fun <reified T : Any> Any.castNullable() = this as? T