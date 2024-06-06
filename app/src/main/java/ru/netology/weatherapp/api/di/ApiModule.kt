package ru.netology.weatherapp.api.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.netology.weatherapp.api.CitiesApi
import ru.netology.weatherapp.api.OffsetDateTimeGsonAdapter
import ru.netology.weatherapp.api.WeatherApi
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        offsetDateTimeGsonAdapter: OffsetDateTimeGsonAdapter,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://pogoda.ngs.ru/api/v1/")
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .registerTypeAdapter(OffsetDateTime::class.java, offsetDateTimeGsonAdapter)
                    .create()
            )
        )
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCitiesApi(retrofit: Retrofit): CitiesApi = retrofit.create()
}
