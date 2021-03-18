package come.hasan.foraty.r.weatherapp.reposetory

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import come.hasan.foraty.r.weatherapp.api.PositionStackApi
import come.hasan.foraty.r.weatherapp.api.PositionStackResponse
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY:String="ef8a2da88c817d728a8229f45c579b31"
private const val TAG:String="PositionStackProvider"
class PositionStackProvider private constructor(){
    private val URI:String="http://api.positionstack.com/v1/forward?access_key=$API_KEY"
    private val postionApi:PositionStackApi
    companion object{
        fun newInstance():PositionStackProvider
        = PositionStackProvider()
    }

    init {
        val retrofit=Retrofit.Builder()
            .baseUrl("http://api.positionstack.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        postionApi=retrofit.create(PositionStackApi::class.java)
    }
    fun fetchAddress(address:String):Response<PositionStackResponse>?{
        val queryString=properStringQuery(address)
        val uri=URI+queryString
        var result:Response<PositionStackResponse>?=null
        postionApi.fetchAddress(uri).enqueue(object :Callback<PositionStackResponse>{
            override fun onResponse(
                call: Call<PositionStackResponse>,
                response: Response<PositionStackResponse>
            ) {
                result=response
            }

            override fun onFailure(call: Call<PositionStackResponse>, t: Throwable) {
                Log.d(TAG,"Something Bad Happend ",t)
            }
        })
        return result
    }

    private fun properStringQuery(query:String):String{
        return "&query=".plus(query
            .replace("%"," ")
            .replace("+"," ")
            .replace("-"," ")
            .replace("_"," ")
            .replace(" ","%20"))
    }
}