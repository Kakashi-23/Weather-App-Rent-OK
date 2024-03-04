package com.example.weatherappRentOk.hilt

import com.example.weatherappRentOk.api.service.WeatherApiService
import com.example.weatherappRentOk.repository.WeatherRepository
import com.example.weatherappRentOk.utils.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object InjectionModule {

    @Provides
    fun providesRetrofit():Retrofit{
        return  Retrofit.Builder()
            .baseUrl(Consts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideWeatherService(retrofit:Retrofit):WeatherApiService{
        return retrofit.create(WeatherApiService::class.java)

    }

    @Provides
    fun provideRepository(apiService: WeatherApiService):WeatherRepository = WeatherRepository(apiService)

}