package com.mayojava.crytocoinlist.api.dispatchers

import com.mayojava.crytocoinlist.api.dispatchers.CoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchersImpl(
    override val io: CoroutineDispatcher = Dispatchers.IO,
    override val computation: CoroutineDispatcher = Dispatchers.Default,
    override val main: CoroutineDispatcher = Dispatchers.Main
) : CoroutineDispatchers
