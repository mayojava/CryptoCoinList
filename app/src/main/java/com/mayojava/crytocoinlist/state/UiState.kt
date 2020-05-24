package com.mayojava.crytocoinlist.state

import java.lang.Exception

sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Error(val exception: Exception): UiState<Nothing>()
    data class Success<out T>(val data: T): UiState<T>()
}