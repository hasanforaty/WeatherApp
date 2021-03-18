package come.hasan.foraty.r.weatherapp.reposetory

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import come.hasan.foraty.r.weatherapp.api.PositionStackResponse
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress
import come.hasan.foraty.r.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainReposetory"

class MainRepository private constructor(){
    companion object {
        fun newInstance(): MainRepository {
            return MainRepository()
        }
    }

    private val openWeatherProvider = OpenWeatherProvider.newInstance()
    private val positionStackProvider = PositionStackProvider.newInstance()

    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param lat lat of chosen location
     * @param lon lan of chosen location
     * @return List of Weather objects
     */
    private val forecasts = MutableLiveData<List<Weather>>()
    fun fetchForecast(lat: Double, lon: Double): LiveData<List<Weather>> {

        val response = openWeatherProvider.fetchOneCallForDesten(lat, lon)
        Log.d(TAG, response.toString())
        response.let {
            forecasts.postValue(it.value?.weatherForecast?.toList())
        }
        return forecasts
    }

    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param address address input from user
     * @return reference to  List of address objects
     */
    private val locations = MutableLiveData<List<GeocodingAddress>>()
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
                Log.d(TAG, "Something Bad Happend ", t)
            }
        })
        return locations
    }
}