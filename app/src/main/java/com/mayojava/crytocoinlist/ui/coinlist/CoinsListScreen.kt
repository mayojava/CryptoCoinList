package com.mayojava.crytocoinlist.ui.coinlist

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.mayojava.crytocoinlist.Injector
import com.mayojava.crytocoinlist.api.CoinData
import com.mayojava.crytocoinlist.api.repository.CryptoListRepository
import com.mayojava.crytocoinlist.lightThemeColors
import com.mayojava.crytocoinlist.state.UiState
import com.mayojava.crytocoinlist.util.getRandomColor
import kotlinx.coroutines.CoroutineScope

@Composable
fun CoinsListScreen(repository: CryptoListRepository, scope: CoroutineScope) {
    Scaffold(
        topAppBar = {
            TopAppBar(
                title = { Text(text = "CryptoList") },
                elevation = 4.dp
            )
        }
    ) { modifier -> ScreenContent(repository, scope, modifier = modifier) }
}

@Composable
private fun ScreenContent(repository: CryptoListRepository, scope: CoroutineScope, modifier: Modifier) {
    val uistate: UiState<List<CoinData>> = loadUiState(
        repository = repository,
        dispatchers = Injector.dispatcher(),
        scope = scope
    )

    when(uistate) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> ShowCoinsList(uistate.data)
    }
}

@Composable
private fun ShowCoinsList(data: List<CoinData>) {
    AdapterList(data = data) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ListItem(coinData = it)
            Divider(color = lightThemeColors.onSurface.copy(alpha = 0.08f))
        }
    }
}

@Composable
private fun ListItem(coinData: CoinData) {
    Clickable(
        onClick = {},
        modifier = Modifier.ripple()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            verticalGravity = Alignment.CenterVertically
        ) {
            CoinSymbol(symbol = coinData.symbol)
            Spacer(modifier = Modifier.size(16.dp))
            CoinNameAndPrice(coinData = coinData)
        }
    }
}

@Composable
private fun CoinNameAndPrice(coinData: CoinData) {
    Column {
        ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
            Text(
                text = coinData.name,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
            val price = String.format("%.2f", coinData.quote.USD.price)
            Text(
                text = "$$price",
                style = MaterialTheme.typography.subtitle2.copy(fontSize = 12.sp)
            )
        }
    }
}

@Composable
private fun CoinSymbol(symbol: String) {
    Box(
        shape = CircleShape,
        modifier = Modifier.preferredSize(48.dp),
        backgroundColor = getRandomColor(),
        gravity = ContentGravity.Center
    ) {
        Text(
            text = symbol,
            style = TextStyle(
                color = lightThemeColors.onPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}