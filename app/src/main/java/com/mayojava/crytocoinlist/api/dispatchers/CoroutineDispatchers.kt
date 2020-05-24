package com.mayojava.crytocoinlist.api.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val io: CoroutineDispatcher
    val computation: CoroutineDispatcher
    val main: CoroutineDispatcher
}
