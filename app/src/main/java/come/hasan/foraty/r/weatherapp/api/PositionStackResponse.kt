package come.hasan.foraty.r.weatherapp.api

import com.google.gson.annotations.SerializedName
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress

data class PositionStackResponse(
    @SerializedName("data")
    val data:List<GeocodingAddress>?
)