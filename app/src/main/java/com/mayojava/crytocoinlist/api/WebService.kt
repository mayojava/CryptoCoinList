package com.mayojava.crytocoinlist.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("listings/latest")
    suspend fun getCoinsList(@Query("limit") limit: Int = 10): ApiResponse
}
