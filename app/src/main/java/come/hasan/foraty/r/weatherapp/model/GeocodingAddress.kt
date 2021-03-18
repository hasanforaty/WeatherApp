package come.hasan.foraty.r.weatherapp.model

import com.google.gson.annotations.SerializedName

data class GeocodingAddress(
    @SerializedName("latitude")
    val latitude:Double,
    @SerializedName("longitude")
    val longitude:Double,
    @SerializedName("name")
    val name:String,
    @SerializedName("country")
    val country:String,
    val error:Exception?
)