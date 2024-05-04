package com.sample.movie.app.extension

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

val moshi: Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

inline fun <reified T> String.fromJson(): T? {
    val jsonAdapter = moshi.adapter(T::class.java)
    return jsonAdapter.fromJson(this)
}

inline fun <reified T> Any.fromJsonList(): List<T>? {
    val type = Types.newParameterizedType(MutableList::class.java, T::class.java)
    val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)
    return jsonAdapter.fromJson(this.toString())
}

inline fun <reified T> T.toJson(): String {
    val jsonAdapter = moshi.adapter(T::class.java).serializeNulls().lenient()
    return jsonAdapter.toJson(this)
}