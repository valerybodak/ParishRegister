package com.parish.register.common

sealed class Resource<T>(
    var data: T? = null,
    val progress: ProgressData? = null,
    val message: String? = null,
    val errorCode: Int? = null,
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null, progress: ProgressData? = null) : Resource<T>(data = data, progress = progress)
    class Error<T>(errorCode: Int, message: String, data: T? = null) : Resource<T>(data, null, message, errorCode)
}

data class ProgressData(val currentValue: Int, val total: Int)