package come.hasan.foraty.r.weatherapp.api

import com.google.gson.annotations.SerializedName
import come.hasan.foraty.r.weatherapp.model.Weather

data class OneCallReport(
    @SerializedName("daily")
    val weatherForecast:Array<Weather>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OneCallReport

        if (!weatherForecast.contentEquals(other.weatherForecast)) return false

        return true
    }

    override fun hashCode(): Int {
        return weatherForecast.contentHashCode()
    }
}