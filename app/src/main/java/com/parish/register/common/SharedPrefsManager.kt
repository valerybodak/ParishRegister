package com.parish.register.common

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StyleRes
import com.google.gson.GsonBuilder
import com.parish.register.model.ListFilter
import java.util.*
import javax.inject.Inject

class SharedPrefsManager @Inject constructor(
    private val context: Context
) {

    companion object {
        private const val APP_PREFERENCES = "APP_PREFERENCES"
        private const val LIST_FILTER = "LIST_FILTER"
    }

    private fun getGlobalProperties(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getLastSynced(tag: String): Long {
        return getGlobalProperties(context).getLong(tag, 0)
    }

    fun saveLastSynced(tag: String) {
        getGlobalProperties(context).edit()
            .putLong(tag, Calendar.getInstance().timeInMillis)
            .apply()
    }

    fun saveListFilter(listFilter: ListFilter) {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(ListFilter::class.java, GsonListFilterAdapter())
        builder.setPrettyPrinting()
        val gson = builder.create()
        getGlobalProperties(context).edit()
            .putString(LIST_FILTER, gson.toJson(listFilter))
            .apply()
    }

    fun getListFilter(): ListFilter {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(ListFilter::class.java, GsonListFilterAdapter())
        val gson = builder.create()
        val jsonAppSettings = getGlobalProperties(context).getString(LIST_FILTER, null)
        return if (jsonAppSettings == null) {
            ListFilter()
        } else {
            gson.fromJson(jsonAppSettings, ListFilter::class.java)
        }
    }
}
