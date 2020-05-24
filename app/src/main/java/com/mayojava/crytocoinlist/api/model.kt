package com.mayojava.crytocoinlist.api

data class ApiResponse(
    val status: Status,
    val data: List<CoinData>
)

data class Status(
    val timestamp: String,
    val error_code: Int,
    val error_message: String?
)

data class CoinData(
    val id: Int,
    val name: String,
    val symbol: String,
    val max_supply: Int,
    val circulating_supply: Int,
    val total_supply: Int,
    val quote: Quote
)

data class Quote(
    val USD: Usd
)

data class Usd(
    val price: Double,
    val volume_24h: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val market_cap: Double
)