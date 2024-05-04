package com.sample.movie.app.init.coil

import android.content.Context
import androidx.collection.LruCache
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import com.sample.movie.R
import javax.inject.Inject

class CoilFactory @Inject constructor(
    private val context: Context,
    private val basePath: String,
) : ImageLoaderFactory {
    companion object {
        // Set how much memory is reserved for the cache of the images
        private const val PERC = 0.2
    }

    override fun newImageLoader(): ImageLoader {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = (maxMemory * PERC).toInt()
        return ImageLoader.Builder(context)
            .components {
                add(CoilCacheInterceptor(LruCache(cacheSize), basePath))
                add(SvgDecoder.Factory())
            }
            .crossfade(true)
            .error(R.drawable.img_placeholder)
            .build()
    }
}