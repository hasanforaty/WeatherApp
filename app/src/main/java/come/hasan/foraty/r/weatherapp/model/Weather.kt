package come.hasan.foraty.r.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Weather(
    @SerializedName(value = "dt")
    private val time:Long,
    @SerializedName("temp")
    val temperature: Temperature,
    @SerializedName("weather")
    val weatherCondition: List<WeatherCondition>,
    @SerializedName("humidity")
    val humidity:Int,
    @SerializedName("rain")
    val rain:Double=0.0,
    @SerializedName("snow")
    val snow:Double=0.0
){
    val date:Date
    get() {
        val milliSecond:Long=this.time*1000L
        return Date(milliSecond)
    }
}