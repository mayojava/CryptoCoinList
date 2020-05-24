package com.mayojava.crytocoinlist.api

interface CryptoListRepository {
    suspend fun getCryptoList()
}