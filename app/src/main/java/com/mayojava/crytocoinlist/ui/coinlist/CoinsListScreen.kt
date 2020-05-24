package com.mayojava.crytocoinlist.ui.coinlist

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.*
import androidx.ui.unit.dp
import com.mayojava.crytocoinlist.api.repository.CryptoListRepository

@Composable
fun CoinsListScreen(repository: CryptoListRepository) {
    Scaffold(
        topAppBar = {
            TopAppBar(
                title = { Text(text = "CryptoList") },
                elevation = 4.dp
            )
        }
    ) { modifier -> ScreenContent(repository, modifier = modifier) }
}

@Composable
private fun ScreenContent(repository: CryptoListRepository, modifier: Modifier) {
    LoadingView()
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}