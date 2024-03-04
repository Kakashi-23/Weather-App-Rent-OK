import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappRentOk.api.model.Forecast
import com.example.weatherappRentOk.databinding.ForecastItemBinding
import com.example.weatherappRentOk.utils.Consts

class ForecastAdapter(var forecastList: List<Pair<String,String>>,private val forecastOnClickListener: ForecastOnClickListener?=null) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = ForecastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastItem = forecastList[position]
        holder.bind(forecastItem)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    inner class ForecastViewHolder(binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val dayTextView: TextView = binding.dayTextView
        private val tempTextView: TextView = binding.tempTextView
        private val forecastLayout:LinearLayout = binding.forecastLayout

        fun bind(pair: Pair<String,String>) {
            dayTextView.text = pair.first
            tempTextView.text = pair.second+Consts.CELCIUS
            forecastOnClickListener?.run {
                forecastLayout.setOnClickListener{
                    this.showForecast(pair.first)
                }
            }

        }
    }


    interface ForecastOnClickListener{
        fun showForecast(day:String)
    }
}
