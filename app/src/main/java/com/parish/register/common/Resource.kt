package com.parish.register.nineteen.common

sealed class Resource<T>(
    val data: T? = null,
    val progress: ProgressData? = null,
    val message: String? = null,
    val errorCode: Int? = null,
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(progress: ProgressData? = null) : Resource<T>(progress = progress)
    class Error<T>(errorCode: Int, message: String, data: T? = null) : Resource<T>(data, null, message, errorCode)
}

data class ProgressData(val currentValue: Int, val total: Int)