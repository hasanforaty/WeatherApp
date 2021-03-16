package come.hasan.foraty.r.weatherapp.api

import com.google.gson.annotations.SerializedName
import come.hasan.foraty.r.weatherapp.model.Weather

data class OneCallReport(
    @SerializedName("daily")
    val weatherForecast:List<Weather>
)