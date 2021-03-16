package come.hasan.foraty.r.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherCondition(
    @SerializedName("main")
    val condition:String,
    @SerializedName("description")
    val description:String="",
    @SerializedName("icon")
    private val icon:String="01d",
){
    val pic:String
        get() {
            return "http://openweathermap.org/img/wn/${icon}@2x.png"
        }
}