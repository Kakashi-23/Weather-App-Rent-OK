package com.example.weatherappRentOk.api.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class ForecastData(
    val cod: String,
    val message: Int,
    val cnt: Int,
    @SerializedName("list")
    val forecastList: List<Forecast>,
    val city: City
) {
    val nextFourDayForecastList: List<Pair<String, String>>
        get() {
            return fourDaysLogic()
        }

    private fun fourDaysLogic(): List<Pair<String, String>> {
        val currentDate = Calendar.getInstance()

        // Initialize a list to store the forecasts for the next four days
        val stack = ArrayDeque<Pair<String, String>>()

        for (forecast in forecastList) {
            // Parse the forecast date
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val forecastDate = dateFormat.parse(forecast.dt_txt)

            // Check if the forecast date is within the next four days
            if (forecastDate.after(currentDate.time) && (currentDate.get(Calendar.DAY_OF_WEEK) - 1) != forecastDate.day) {
                if (!stack.isEmpty() && forecast.getDateTempPair().first == stack.last().first) {
                    val lastStackTemp = stack.last().second.toInt()
                    stack.removeLast()
                    val average = (lastStackTemp + forecast.getDateTempPair().second.toInt()) / 2
                    Log.d("ForecastData", "fourDaysLogic: av - $average")
                    stack.add(Pair(forecast.getDateTempPair().first, average.toString()))
                } else {
                    stack.add(forecast.getDateTempPair())
                }

                Log.d("ForecastData", "fourDaysLogic: stack- $stack")

            }

            // If we have collected forecasts for the next six days, break the loop
            if (stack.size >= 6) {
                break
            }
        }
        return stack.toList()
    }


    fun forecastByDay(day:String): List<Pair<String, String>> {
        val currentDate = Calendar.getInstance()

        // Initialize a list to store the forecasts for the next four days
        val stack = ArrayDeque<Pair<String, String>>()

        for (forecast in forecastList) {
            // Check if the forecast date is within the next four days
            if (forecast.getDay().equals(day, ignoreCase = true)) {
                stack.add(forecast.getDateTimeTempPair())
            }
        }
        return stack.toList()
    }

}

data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val dt_txt: String
) {

    fun getDateTempPair(): Pair<String, String> {
        return Pair(getDay(), main.getTempInCelsius())
    }

    fun getDateTimeTempPair():Pair<String,String>{
        return Pair(getDayTime(),main.getTempInCelsius())
    }

    fun getDay(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


        // Parse forecast date
        val forecastDate = dateFormat.parse(dt_txt)
        val calendar = Calendar.getInstance().apply { time = forecastDate }
        // Get day of the week (e.g., Monday, Tuesday, etc.)
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)
//        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
    }
    private fun getDayTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


        // Parse forecast date
        val forecastDate = dateFormat.parse(dt_txt)
        val calendar = Calendar.getInstance().apply { time = forecastDate }
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)

        // Get day of the week (e.g., Monday, Tuesday, etc.)
        return "${SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)} ($time)"
    }
}


data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

