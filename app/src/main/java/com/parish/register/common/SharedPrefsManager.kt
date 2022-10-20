package com.parish.register.nineteen.common

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StyleRes
import java.util.*
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
        private const val BOOK_PREFIX = "book_"
        private const val APP_THEME = "app_theme"
    }

    private fun getGlobalProperties(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getThemeId(): Int{
        return getGlobalProperties(context).getInt(APP_THEME, 0)
    }

    fun saveThemeId(@StyleRes resId: Int) {
        getGlobalProperties(context).edit()
                .putInt(APP_THEME, resId)
                .apply()
    }

    fun getLastSynced(tag: String): Long {
        return getGlobalProperties(context).getLong(tag, 0)
    }

    fun saveLastSynced(tag: String) {
        getGlobalProperties(context).edit()
            .putLong(tag, Calendar.getInstance().timeInMillis)
            .apply()
    }

    fun getBookVersion(bookId: String): Int {
        return getGlobalProperties(context).getInt(BOOK_PREFIX + bookId, -1)
    }

    fun saveBookVersion(bookId: String, version: Int) {
        getGlobalProperties(context).edit()
            .putInt(BOOK_PREFIX + bookId, version)
            .apply()
    }
}
