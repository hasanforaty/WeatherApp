package come.hasan.foraty.r.weatherapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
private const val string:String="http://api.positionstack.com/v1/forward?access_key=ef8a2da88c817d728a8229f45c579b31&query=1600%20Pennsylvania%20Ave%20NW,%20Washington%20DC"
interface PositionStackApi {
    @GET
    fun fetchAddress(@Url url:String):Call<PositionStackResponse>
}