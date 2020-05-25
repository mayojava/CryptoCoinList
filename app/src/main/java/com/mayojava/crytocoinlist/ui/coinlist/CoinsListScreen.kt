package com.mayojava.crytocoinlist.ui.coinlist

import android.widget.Toast
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.ripple
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.mayojava.crytocoinlist.Injector
import com.mayojava.crytocoinlist.api.CoinData
import com.mayojava.crytocoinlist.api.repository.CryptoListRepository
import com.mayojava.crytocoinlist.lightThemeColors
import com.mayojava.crytocoinlist.state.UiState
import com.mayojava.crytocoinlist.util.getChangeInPriceColor
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
private fun ScreenContent(
    repository: CryptoListRepository,
    scope: CoroutineScope,
    modifier: Modifier
) {
    val uistate: UiState<List<CoinData>> = loadUiState(
        repository = repository,
        dispatchers = Injector.dispatcher(),
        scope = scope
    )

    when (uistate) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> ShowCoinsList(uistate.data, modifier)
        is UiState.Error -> Toast.makeText(ContextAmbient.current, "Error occurred. Check if API key is added.", Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun ShowCoinsList(data: List<CoinData>, modifier: Modifier = Modifier) {
    Stack {
        Box(modifier = Modifier.fillMaxSize(), backgroundColor = Color.Black.copy(alpha = 0.5f))
        AdapterList(
            data = data,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            )
                .plus(modifier)
        ) {
            ListItem(coinData = it)
        }
    }
}

@Composable
private fun ListItem(coinData: CoinData) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        Clickable(
            onClick = {},
            modifier = Modifier.ripple()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                verticalGravity = Alignment.CenterVertically
            ) {
                CoinSymbol(symbol = coinData.symbol)
                Spacer(modifier = Modifier.size(16.dp))
                CoinInfo(coinData = coinData)
            }
        }
    }
}

@Composable
private fun CoinInfo(coinData: CoinData) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalGravity = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        CoinNameAndPrice(coinData = coinData, modifier = Modifier.weight(1.0f))
        Last1hrCoinPrice(
            price = coinData.quote.USD.percent_change_1h,
            modifier = Modifier.weight(1.0f)
        )
        Last24hrCoinPrice(
            price = coinData.quote.USD.percent_change_24h,
            modifier = Modifier.weight(1.0f)
        )
    }
}

@Composable
private fun Last1hrCoinPrice(price: Double, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
            Text(
                text = "Last 1hr",
                style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
            Text(
                text = String.format("%.2f%%", price),
                color = getChangeInPriceColor(price),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun Last24hrCoinPrice(price: Double, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
            Text(
                text = "Last 24hr",
                style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
            Text(
                text = String.format("%.2f%%", price),
                color = getChangeInPriceColor(price),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun CoinNameAndPrice(coinData: CoinData, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ProvideEmphasis(emphasis = EmphasisAmbient.current.high) {
            Text(
                text = coinData.name,
                style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
            val price = String.format("%.2f", coinData.quote.USD.price)
            Text(
                text = "$$price",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
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