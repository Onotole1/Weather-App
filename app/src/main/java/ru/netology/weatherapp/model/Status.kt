package ru.netology.weatherapp.model

sealed interface Status<out E> {
    data object Loading : Status<Nothing>
    data object Idle : Status<Nothing>
    data class Error<E>(val value: E) : Status<E>

    val error: E?
        get() = (this as? Error)?.value
}
