package com.mayojava.crytocoinlist.api.repository

import com.mayojava.crytocoinlist.api.CoinData

interface CryptoListRepository {
    suspend fun getCryptoList(): List<CoinData>
}