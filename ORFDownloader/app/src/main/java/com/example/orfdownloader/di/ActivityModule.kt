package com.example.orfdownloader.di

import android.content.Context
import com.example.orfdownloader.data.RadioStations
import com.example.orfdownloader.network.OrfApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun stationListProvider(): Array<RadioStations> {
        return RadioStations.values()
    }

    //Retrofit
    @Provides
    @ActivityScoped
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val baseUrl = "https://audioapi.orf.at/"

        fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cachingInterceptor: Interceptor): OkHttpClient {
            val b = OkHttpClient.Builder()
            b.cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
            b.addInterceptor(cachingInterceptor)
            b.addInterceptor(loggingInterceptor)
            return b.build()
        }

        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        fun provideCachingInterceptor(): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .header("Cache-Control", "public, max-age=${60 * 60}")
                    .build()
                chain.proceed(request)
            }
        }



        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient(provideLoggingInterceptor(),provideCachingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @ActivityScoped
    fun provideOrfService(retrofit: Retrofit): OrfApis = retrofit.create(
        OrfApis::class.java
    )
}