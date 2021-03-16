package come.hasan.foraty.r.weatherapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface OpenweathermapApi {
    @GET
    fun fetchOneCallReport(
        @Url Url:String
    ):Call<OneCallReport>
    
}