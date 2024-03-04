package com.example.weatherappRentOk.utils

sealed  class WeatherResult<out T> {
    data class Success<T>(val data: T) : WeatherResult<T>()
    object Loading : WeatherResult<Nothing>()
    data class Error(val message: String) : WeatherResult<Nothing>()

}