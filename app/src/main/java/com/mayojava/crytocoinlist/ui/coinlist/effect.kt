package com.mayojava.crytocoinlist.ui.coinlist

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.onActive
import androidx.compose.setValue
import androidx.compose.state
import com.mayojava.crytocoinlist.api.dispatchers.CoroutineDispatchers
import com.mayojava.crytocoinlist.api.repository.CryptoListRepository
import com.mayojava.crytocoinlist.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun <T> loadUiState(
    repository: CryptoListRepository,
    dispatchers: CoroutineDispatchers,
    scope: CoroutineScope
): UiState<T> {
    var state: UiState<T> by state<UiState<T>> { UiState.Loading }

    onActive {
        scope.launch(dispatchers.main) {
            val data = repository.getCryptoList()
            state = UiState.Success(data = data as T)
        }
    }

    return state
}