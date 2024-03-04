package com.example.weatherappRentOk.api.service

import com.example.weatherappRentOk.api.model.CurrentWeatherData
import com.example.weatherappRentOk.api.model.ForecastData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") cityName: String,
        @Query("APPID") apiKey: String
    ): CurrentWeatherData

    @GET("forecast")
    suspend fun getForecast(@Query("q") city: String, @Query("APPID") apiKey: String): ForecastData

}
