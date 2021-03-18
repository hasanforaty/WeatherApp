package come.hasan.foraty.r.weatherapp.api

import com.google.gson.annotations.SerializedName
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress

data class PositionStackResponse(
    @SerializedName("data")
    val data:Array<GeocodingAddress>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PositionStackResponse

        if (data != null) {
            if (other.data == null) return false
            if (!data.contentEquals(other.data)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        return data?.contentHashCode() ?: 0
    }
}