package com.mayojava.crytocoinlist.api.repository

import com.mayojava.crytocoinlist.api.CoinData
import com.mayojava.crytocoinlist.api.WebService
import com.mayojava.crytocoinlist.api.dispatchers.CoroutineDispatchers
import kotlinx.coroutines.withContext

class CryptoListRepositoryImpl constructor(
    private val webService: WebService,
    private val dispatcher: CoroutineDispatchers
) : CryptoListRepository {
    override suspend fun getCryptoList(): List<CoinData> {
        return withContext(dispatcher.io) {
            webService
                .getCoinsList(50)
                .data
        }
    }
}