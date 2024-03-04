package com.example.weatherappRentOk.ui.fragments

import ForecastAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappRentOk.R
import com.example.weatherappRentOk.api.model.CurrentWeatherData
import com.example.weatherappRentOk.api.model.ForecastData
import com.example.weatherappRentOk.databinding.FragmentHomeBinding
import com.example.weatherappRentOk.utils.Consts
import com.example.weatherappRentOk.utils.WeatherResult
import com.example.weatherappRentOk.utils.gone
import com.example.weatherappRentOk.utils.visible
import com.example.weatherappRentOk.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    private val TAG = "Home Fragment"

    private val forecastAdapter by lazy {
        ForecastAdapter(listOf(), forecastClickListener)
    }

    private var forecastData:ForecastData?=null
    private val forecastClickListener by lazy {
        object : ForecastAdapter.ForecastOnClickListener {
            override fun showForecast(day: String) {
                forecastData?.let {
                    viewModel.getForecastByDay(day,it)
                    val bundle = Bundle().apply {
                        putString("day", day)
                        // Add more key-value pairs as needed
                    }

                    val destinationFragment = ForecastFragment()
//                    destinationFragment.arguments = bundle

                    // Navigate to the destination fragment
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.homeContainer, destinationFragment)
                        .addToBackStack(null)
                        .commit()

                } ?: {
                    Toast.makeText(requireActivity(),"No Forecast Avaialable",Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initializeRecyclerView()
        observeForecast()
        observeWeatherData()
        return binding.root
    }

    private fun initializeRecyclerView() {
        binding.apply {
            forecastRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            forecastRecyclerView.adapter = forecastAdapter
        }
    }

    private fun observeWeatherData() {
        viewModel.weatherData.observe(requireActivity()) { result ->
            when (result) {
                is WeatherResult.Success -> {
                    // Data is successfully loaded
                    populateWeatherData(result.data)
                    binding.hideProgress()
                    // Update UI with weather data
                }

                is WeatherResult.Loading -> {
                    // Show loading indicator
                    binding.showProgress()
                }

                is WeatherResult.Error -> {
                    binding.hideProgress()
                    // Show error message
                    showSnackBar(result.message)
                }
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.weatherLayout,
            "Something went wrong!!",
            Snackbar.LENGTH_LONG
        ).setAction("Retry") {
            viewModel.getWeatherAndForecast(Consts.cityName)
        }.show()
        Log.d(TAG, "showSnackBar: $message")
    }

    private fun FragmentHomeBinding.showProgress() {
        this.progress.visible()
        this.weatherLayout.gone()
    }

    private fun FragmentHomeBinding.hideProgress() {
        this.progress.gone()
        this.weatherLayout.visible()
    }

    private fun populateWeatherData(data: CurrentWeatherData) {
        binding.apply {
            currentTemp.text = data.main.getTempInCelsius()
            this.cityName.text = Consts.cityName
        }
    }

    private fun observeForecast() {
        viewModel.forecastData.observe(requireActivity()) { result ->
            when (result) {
                is WeatherResult.Success -> {
                    // Data is successfully loaded
                    binding.hideProgress()
                    populateForecastData(result.data)
                }

                is WeatherResult.Loading -> {
                    // Show loading indicator
                    binding.showProgress()
                }

                is WeatherResult.Error -> {
                    // Show error message
                    binding.hideProgress()
                    showSnackBar(result.message)
                }
            }
        }
    }

    private fun populateForecastData(data: ForecastData) {
        forecastData = data
        forecastAdapter.forecastList = data.nextFourDayForecastList
        forecastAdapter.notifyDataSetChanged()
    }

}