package com.trifcdr.lifestylehub.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.LinearLayoutCompat
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.WeatherViewBinding

/**
 * Created by trifcdr.
 */
class WeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr){

    private var binding: WeatherViewBinding

    init {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding = WeatherViewBinding.inflate(LayoutInflater.from(context), this, true)
    }


    fun setCity(city: String){
        binding.cityTv.text = city
    }

    fun setWeatherStatus(status: String){
        binding.weatherStatusTv.text = status
    }

    fun setTemperature(temp: String){
        binding.tempTv.text = "$temp °C"
    }

    fun setFeelLike(feelLike: String){
        binding.feelLikeTv.text = "Чувствуется как $feelLike °C"
    }

    fun setTempSpread(tempMin: String, tempMax: String){
        binding.tempMinTv.text = "Минимум: $tempMin °C"
        binding.tempMaxTv.text = "Максимум: $tempMax °C"
    }
    fun setShimmer(){
            binding.weatherView.visibility = View.INVISIBLE
            binding.shimmerView.visibility = View.VISIBLE
            binding.shimmerView.startShimmerAnimation()


    }
    fun removeShimmer(success: Boolean){
        binding.shimmerView.stopShimmerAnimation()
        binding.shimmerView.visibility = View.INVISIBLE
        if (success){
            binding.weatherView.visibility = View.VISIBLE
        }
        else{
            binding.weatherErrorTv.text = context.getString(R.string.unnable)
        }
    }

    @SuppressLint("DiscouragedApi")
    fun setWeatherIcon(iconName: String){
        binding.weatherIconIv.setImageDrawable(AppCompatResources.getDrawable(context,
            context.resources.getIdentifier("w$iconName", "drawable", context.packageName)))

    }
}