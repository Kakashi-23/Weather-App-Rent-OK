package com.example.weatherappRentOk.repository

import com.example.weatherappRentOk.api.model.CurrentWeatherData
import com.example.weatherappRentOk.api.model.ForecastData
import com.example.weatherappRentOk.api.service.WeatherApiService
import com.example.weatherappRentOk.utils.Consts.API_KEY
import com.example.weatherappRentOk.utils.WeatherResult
import javax.inject.Inject


class WeatherRepository @Inject constructor(val apiService: WeatherApiService) {

    suspend fun getWeatherData(cityName: String): WeatherResult<CurrentWeatherData> {
        return try {
            val response = apiService.getWeatherData(cityName, API_KEY)
            WeatherResult.Success(response)
        } catch (e: Exception) {
            WeatherResult.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getForecast(city: String): WeatherResult<ForecastData> {
        return try {
            val response = apiService.getForecast(city, API_KEY)
            WeatherResult.Success(response)
        } catch (e: Exception) {
            WeatherResult.Error(e.message ?: "An error occurred")
        }    }

}