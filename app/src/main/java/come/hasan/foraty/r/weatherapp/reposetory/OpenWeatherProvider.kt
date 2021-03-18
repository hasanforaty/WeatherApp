package come.hasan.foraty.r.weatherapp.reposetory

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import come.hasan.foraty.r.weatherapp.MainActivity
import come.hasan.foraty.r.weatherapp.api.OneCallReport
import come.hasan.foraty.r.weatherapp.api.OpenWeatherMapApi
import come.hasan.foraty.r.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG:String="OpenWeatherProvider"
private const val API_KEY:String="570a5be1fbee8e473bb6ee1e50222f40"
class OpenWeatherProvider private constructor(){
    companion object{
        fun newInstance(): OpenWeatherProvider {
            return OpenWeatherProvider()
        }
    }

    private val openWeatherMapApi: OpenWeatherMapApi
    init {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        openWeatherMapApi=retrofit.create(OpenWeatherMapApi::class.java)
    }

    /**
     * fetchOneCallForDesten get weathers object for given location
     * @param lat latitude of chosen location
     * @param lon longitude of chosen location
     * @return response of retrofit for OneCallReport
     */
    fun fetchOneCallForDesten(lat:Double,lon:Double): LiveData<OneCallReport> {
        var result =MutableLiveData<OneCallReport>()
        val url="https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&appid=$API_KEY"
        openWeatherMapApi.fetchOneCallReport(url).enqueue(object: Callback<OneCallReport> {
            override fun onResponse(call: Call<OneCallReport>, response: Response<OneCallReport>) {
                if(response.isSuccessful){
                    result.postValue(response.body())
                }else{
                    Log.d(TAG,"server has some problem request didn't success")
                }
            }
            override fun onFailure(call: Call<OneCallReport>, t: Throwable) {
                Log.d(TAG,"Something Bad Happend ",t)
            }
        })

        return result
    }
}