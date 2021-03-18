package come.hasan.foraty.r.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Temperature(
    val type:String="C",
    @SerializedName("day")
    val day:Double,
    @SerializedName("night")
    val night:Double
)