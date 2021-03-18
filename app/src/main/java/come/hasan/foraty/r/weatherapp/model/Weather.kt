package come.hasan.foraty.r.weatherapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Weather(
    @SerializedName(value = "dt")
    private val time:Long,
    @SerializedName("temp")
    val temperature: Temperature,
    @SerializedName("weathers")
    val weatherCondition: List<WeatherCondition>,
    @SerializedName("humidity")
    val humidity:Int,
    @SerializedName("rain")
    val rain:Int=0,
    @SerializedName("snow")
    val snow:Int=0
){
    val date:Date
    get() {
        val milliSecond:Long=this.time*1000L
        return Date(milliSecond)
    }
}