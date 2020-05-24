package com.mayojava.crytocoinlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.lifecycleScope
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.mayojava.crytocoinlist.ui.coinlist.CoinsListScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = lightThemeColors
            ) {
                CoinsListScreen(Injector.createRepository(), lifecycleScope)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        //CoinsListScreen(Injector.createRepository())
    }
}