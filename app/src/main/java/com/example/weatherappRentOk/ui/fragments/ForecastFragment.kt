package com.example.weatherappRentOk.ui.fragments

import ForecastAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappRentOk.databinding.FragmentForecastBinding
import com.example.weatherappRentOk.viewmodel.WeatherViewModel

class ForecastFragment:Fragment() {

    private val TAG = this.javaClass.name
    private lateinit var binding: FragmentForecastBinding
    private val viewModel by  lazy {
        ViewModelProvider(requireActivity())[WeatherViewModel::class.java]
    }
    private val forecastAdapter by lazy {
        ForecastAdapter(listOf())
    }
    private var day:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        day  = arguments?.getString("day")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastBinding.inflate(inflater,container,false)
        initializeRecyclerView()
        observe()
        return binding.root
    }


    private fun observe(){
        viewModel.dayForecastList.observe(requireActivity()){
            forecastAdapter.forecastList = it
            forecastAdapter.notifyDataSetChanged()
        }
    }

    private fun initializeRecyclerView() {
        binding.apply {
            forecastRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            forecastRecyclerView.adapter = forecastAdapter
        }
    }

}