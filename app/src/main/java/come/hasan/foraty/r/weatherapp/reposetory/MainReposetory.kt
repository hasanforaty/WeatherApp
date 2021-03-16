package come.hasan.foraty.r.weatherapp.reposetory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import come.hasan.foraty.r.weatherapp.api.OneCallReport
import come.hasan.foraty.r.weatherapp.api.OpenweathermapApi
import come.hasan.foraty.r.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG="MainReposetory"
private const val API_KEY:String="570a5be1fbee8e473bb6ee1e50222f40"
class MainReposetory private constructor(){
    companion object{
        fun newInstance(): MainReposetory {
            return MainReposetory()
        }
    }
    val openweathermapApi: OpenweathermapApi
    init {
        val retrofit=Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        openweathermapApi=retrofit.create(OpenweathermapApi::class.java)
    }
    fun fetchOneCallForDesten(lat:Double,lon:Double):LiveData<List<Weather>>{
        val result=MutableLiveData<List<Weather>>()
        val url="https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&appid=$API_KEY"
        openweathermapApi.fetchOneCallReport(url).enqueue(object:Callback<OneCallReport>{
            override fun onResponse(call: Call<OneCallReport>, response: Response<OneCallReport>) {
                val oneCallReport=response.body()
                oneCallReport?.let {
                    result.value=it.weatherForecast
                }
            }

            override fun onFailure(call: Call<OneCallReport>, t: Throwable) {
                Log.d(TAG,"Something Bad Happend ",t)
            }
        })

        return result
    }
}