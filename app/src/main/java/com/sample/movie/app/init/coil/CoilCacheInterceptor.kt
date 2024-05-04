package com.sample.movie.app.init.coil

import android.graphics.drawable.Drawable
import androidx.collection.LruCache
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.request.SuccessResult

class CoilCacheInterceptor(
    private val cache: LruCache<String, Drawable>,
    private val basePath: String
) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request
        val scheme = request.data.toString()
        val digitsRegex = Regex("\\d+")

        if (
            !digitsRegex.matches(scheme)
            && !scheme.startsWith("http")
            && !scheme.startsWith("android")
            && !scheme.startsWith("content")
        ) {
            val modifiedRequest = request.newBuilder()
                .data("$basePath${request.data}")
                .build()

            val response = chain.proceed(modifiedRequest)

            if (response is SuccessResult)
                cache.put(request.data.toString(), response.drawable)

            return response
        }

        return chain.proceed(request)
    }
}