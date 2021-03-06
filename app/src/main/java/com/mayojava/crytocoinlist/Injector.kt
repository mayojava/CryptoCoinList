package com.mayojava.crytocoinlist

import com.mayojava.crytocoinlist.api.WebService
import com.mayojava.crytocoinlist.api.dispatchers.CoroutineDispatchers
import com.mayojava.crytocoinlist.api.dispatchers.DispatchersImpl
import com.mayojava.crytocoinlist.api.repository.CryptoListRepository
import com.mayojava.crytocoinlist.api.repository.CryptoListRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    fun createRepository(): CryptoListRepository =
        CryptoListRepositoryImpl(createWebservice(), DispatchersImpl())

    fun dispatcher(): CoroutineDispatchers = DispatchersImpl()

    private fun createWebservice(): WebService {
        return createRetrofit()
            .create(WebService::class.java)
    }

    private fun createRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttp = with(OkHttpClient.Builder()) {
            addInterceptor(headerInterceptor())
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

    private fun headerInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest = with(chain.request()) {
                    newBuilder()
                        .addHeader("X-CMC_PRO_API_KEY", BuildConfig.API_KEY)
                        .build()
                }
                return chain.proceed(newRequest)
            }
        }
    }
}