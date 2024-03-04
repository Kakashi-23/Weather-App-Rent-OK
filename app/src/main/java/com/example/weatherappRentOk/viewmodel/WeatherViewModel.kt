package com.example.weatherappRentOk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappRentOk.api.model.CurrentWeatherData
import com.example.weatherappRentOk.api.model.ForecastData
import com.example.weatherappRentOk.repository.WeatherRepository
import com.example.weatherappRentOk.utils.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResult<CurrentWeatherData>>()
    val weatherData: LiveData<WeatherResult<CurrentWeatherData>> = _weatherData

    private val _forecastData = MutableLiveData<WeatherResult<ForecastData>>()
    val forecastData: LiveData<WeatherResult<ForecastData>> = _forecastData

    private val _dayForecastList = MutableLiveData< List<Pair<String, String>>>()
    val dayForecastList: LiveData< List<Pair<String, String>>> = _dayForecastList

    private suspend fun fetchWeatherData(cityName: String) {
//        delay(10000)
        _weatherData.value = WeatherResult.Loading
        try {
            val response = repository.getWeatherData(cityName)
            _weatherData.value = response
        } catch (e: Exception) {
            _weatherData.value = WeatherResult.Error(e.message ?: "An error occurred")
        }

    }

    private suspend fun fetchForecastData(cityName: String) {
        _forecastData.value = WeatherResult.Loading
        try {
            val response = repository.getForecast(cityName)
            _forecastData.value = response
        } catch (e: Exception) {
            _forecastData.value = WeatherResult.Error(e.message ?: "An error occurred")
        }

    }

    fun getWeatherAndForecast(city: String) {
        viewModelScope.launch {
            val weatherDeferred = async { fetchWeatherData(city) }
            val forecastDeferred = async { fetchForecastData(city) }
            weatherDeferred.await()
            forecastDeferred.await()

        }
    }

    fun getForecastByDay(day: String, forecast:ForecastData){
        viewModelScope.launch {
            _dayForecastList.value = forecast.forecastByDay(day)
        }
    }
}
