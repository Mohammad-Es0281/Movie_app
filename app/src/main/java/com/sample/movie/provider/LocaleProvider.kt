package com.sample.movie.provider

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import java.util.Locale

object LocaleProvider {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val DEFAULT_LANGUAGE = "en"

    fun onAttach(
        context: Context
    ): Context {
        val lang = getPersistedData(context)
        return setAppLocale(context, lang)
    }

    fun getLanguage(context: Context): String {
        return getPersistedData(context)
    }

    private fun setAppLocale(context: Context, language: String): Context {
        persist(context, language)
        return updateResources(context, language)
    }

    private fun getPersistedData(context: Context): String {
        val preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        return preferences.getString(SELECTED_LANGUAGE, DEFAULT_LANGUAGE)!!
    }

    private fun persist(context: Context, language: String) {
        val preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(
        context: Context,
        language: String,
    ): Context {
        return context.setLocale(language)
    }

    private fun Context.setLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val current = resources.configuration.locales.get(0)

        if (current == locale) return this

        val config = Configuration(resources.configuration)
        val set = linkedSetOf(locale)

        val defaultLocales = LocaleList.getDefault()
        val all = List<Locale>(defaultLocales.size()) { defaultLocales[it] }
        set.addAll(all)

        config.setLocales(LocaleList(*set.toTypedArray()))
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        return createConfigurationContext(config)
    }
}