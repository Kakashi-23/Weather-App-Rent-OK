package com.example.weatherappRentOk.ui

import ForecastAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappRentOk.R
import com.example.weatherappRentOk.api.model.CurrentWeatherData
import com.example.weatherappRentOk.api.model.ForecastData
import com.example.weatherappRentOk.databinding.ActivityMainBinding
import com.example.weatherappRentOk.ui.fragments.HomeFragment
import com.example.weatherappRentOk.utils.Consts
import com.example.weatherappRentOk.utils.WeatherResult
import com.example.weatherappRentOk.utils.gone
import com.example.weatherappRentOk.utils.startActivityWithBundle
import com.example.weatherappRentOk.utils.visible
import com.example.weatherappRentOk.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()


    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // observe here
        homeFragment()
        // Fetch weather data for Bengaluru
        viewModel.getWeatherAndForecast(Consts.cityName)
    }

    private fun homeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.homeContainer, HomeFragment())
            .commit()
    }


}