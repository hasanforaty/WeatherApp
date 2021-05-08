package come.hasan.foraty.r.weatherapp.reposetory

import come.hasan.foraty.r.weatherapp.api.OneCallReport
import come.hasan.foraty.r.weatherapp.api.OpenWeatherMapApi
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Call
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

        val specs: List<*> = mutableListOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)

        val client = OkHttpClient.Builder()
            .connectionSpecs(specs as MutableList<ConnectionSpec>)
            .build()
        val retrofit= Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        openWeatherMapApi=retrofit.create(OpenWeatherMapApi::class.java)
    }

    /**
     * fetchOneCallForDestine get weathers object for given location
     * @param lat latitude of chosen location
     * @param lon longitude of chosen location
     * @return response of retrofit for OneCallReport
     */
    fun fetchOneCallForDestine(lat:Double, lon:Double): Call<OneCallReport> {
        val url="https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$lon&units=metric&appid=$API_KEY"
        return openWeatherMapApi.fetchOneCallReport(url)
    }
}