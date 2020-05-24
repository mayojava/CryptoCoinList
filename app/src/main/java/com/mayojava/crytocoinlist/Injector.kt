package com.mayojava.crytocoinlist

import com.mayojava.crytocoinlist.api.WebService
import com.mayojava.crytocoinlist.api.dispatchers.DispatchersImpl
import com.mayojava.crytocoinlist.api.repository.CryptoListRepository
import com.mayojava.crytocoinlist.api.repository.CryptoListRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    fun createRepository(): CryptoListRepository =
        CryptoListRepositoryImpl(createWebservice(), DispatchersImpl())

    private fun createWebservice(): WebService {
        return createRetrofit()
            .create(WebService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttp = with(OkHttpClient.Builder()) {
            addInterceptor(interceptor)
            build()
        }

        return with(Retrofit.Builder()) {
            baseUrl(BuildConfig.API_URL)
            client(okHttp)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
    }
}