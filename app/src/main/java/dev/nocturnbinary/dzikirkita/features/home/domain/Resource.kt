package dev.nocturnbinary.dzikirkita.features.home.domain

sealed class Resource<out T> {
    data class Error(val e: ResourceError, val errorMessage: String?) : Resource<Nothing>()
    data class Success<R>(val result: R) : Resource<R>()
}

enum class ResourceError {
    UNAUTHORIZED,
    SERVICE_UNAVAILABLE,
    UNKNOWN,
    FORBIDDEN,
    BAD_REQUEST
}