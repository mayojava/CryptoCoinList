package com.mayojava.crytocoinlist.util

import androidx.ui.graphics.Color
import kotlin.random.Random

private val colors = listOf<Color>(
    Color(0xFFf44336),
    Color(0xFFe91e63),
    Color(0xFF9c27b0),
    Color(0xFF673ab7),
    Color(0xFF3f51b5),
    Color(0xFF2196f3),
    Color(0xFF03a9f4),
    Color(0xFF00bcd4),
    Color(0xFF009688),
    Color(0xFF4caf50),
    Color(0xFF8bc34a),
    Color(0xFFcddc39),
    Color(0xFFffeb3b),
    Color(0xFFffc107),
    Color(0xFFff9800),
    Color(0xFFff5722),
    Color(0xFF795548),
    Color(0xFF9e9e9e),
    Color(0xFF607d8b)
)

fun getRandomColor(): Color {
    val index = Random.nextInt(colors.size)
    return colors[index]
}