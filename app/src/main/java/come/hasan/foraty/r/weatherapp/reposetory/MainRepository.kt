package come.hasan.foraty.r.weatherapp.reposetory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import come.hasan.foraty.r.weatherapp.api.OneCallReport
import come.hasan.foraty.r.weatherapp.api.PositionStackResponse
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress
import come.hasan.foraty.r.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainRepository"

class MainRepository private constructor(){
    companion object {
        fun newInstance(): MainRepository {
            return MainRepository()
        }
    }

    private val openWeatherProvider = OpenWeatherProvider.newInstance()
    private val positionStackProvider = PositionStackProvider.newInstance()
    private val locations = MutableLiveData<List<GeocodingAddress>>()
    private val forecastLiveData=MutableLiveData<List<Weather>>()
    val forecasts:LiveData<List<Weather>> = Transformations.switchMap(locations){
        val currentLocation=it[0]
        fetchForecast(currentLocation.latitude,currentLocation.longitude)
    }


    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param lat lat of chosen location
     * @param lon lan of chosen location
     * @return List of Weather objects
     */
    private fun fetchForecast(lat: Double, lon: Double): LiveData<List<Weather>> {
        val response = openWeatherProvider.fetchOneCallForDestine(lat, lon)
        Log.d(TAG, response.toString())
        response.enqueue(object: Callback<OneCallReport> {
            override fun onResponse(call: Call<OneCallReport>, response: Response<OneCallReport>) {
                if(response.isSuccessful){
                    forecastLiveData.postValue(response.body()?.weatherForecast?.toList())
                    Log.d(TAG,"forecast condition is ${response.body()?.weatherForecast?.get(0)?.weatherCondition.toString()}")
                }else{
                    Log.d(TAG,"server has some problem request didn't success")
                }
            }
            override fun onFailure(call: Call<OneCallReport>, t: Throwable) {
                Log.d(TAG,"Something Bad Happened ",t)
            }
        })
        return forecastLiveData
    }

    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param address address input from user
     * @return reference to  List of address objects
     */

    fun fetchLocation(address: String): MutableLiveData<List<GeocodingAddress>> {

        //get address from provider and delete similar address by their name
        val response = positionStackProvider.fetchAddress(address)
        response.enqueue(object : Callback<PositionStackResponse> {
            override fun onResponse(
                call: Call<PositionStackResponse>,
                response: Response<PositionStackResponse>
            ) {
                if (response.isSuccessful) {
                    locations.postValue(
                        response.body()?.data?.toList()?.distinctBy { geocodingAddress ->
                            geocodingAddress.name
                        })
                } else {
                    Log.d(TAG, "server has some problem request didn't success")
                }

            }

            override fun onFailure(call: Call<PositionStackResponse>, t: Throwable) {
                Log.d(TAG, "Something Bad Happened ", t)
            }
        })
        return locations
    }
}