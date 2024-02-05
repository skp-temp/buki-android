package com.example.skptemp.network

//import com.example.skptemp.common.constants.NetworkConstants
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RetrofitBuilder {
//    private var instance: Retrofit? = null
//    private val httpClient = OkHttpClient.Builder().apply {
//        addInterceptor { chain ->
//            val request = chain.request()
//                .newBuilder()
//                .addHeader("Authorization", NetworkConstants.API_KEY)
//                .build()
//            chain.proceed(request)
//        }
//    }
//
//    @Synchronized
//    @Singleton
//    @Provides
//    fun getInstance(): Retrofit {
//        instance?.let { return it } ?: run {
//            instance = Retrofit.Builder().apply {
//                baseUrl(NetworkConstants.BASE_URL)
//                addConverterFactory(GsonConverterFactory.create())
//                client(httpClient.build())
//            }.build()
//        }
//        return instance!!
//    }
//}