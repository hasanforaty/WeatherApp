package come.hasan.foraty.r.weatherapp.reposetory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress
import come.hasan.foraty.r.weatherapp.model.Weather
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val TAG="MainReposetory"
class MainReposetory private constructor(){
    companion object{
        fun newInstance(): MainReposetory {
            return MainReposetory()
        }
    }
    private val openWeatherProvider=OpenWeatherProvider.newInstance()
    private val positionStackProvider=PositionStackProvider.newInstance()
    private val executor:Executor= Executors.newSingleThreadExecutor()
    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param lat lat of chosen location
     * @param lon lan of chosen location
     * @return List of Weather objects
     */
    fun fetchForecast(lat:Double, lon:Double):List<Weather>{
        var result:List<Weather> = mutableListOf()
            executor.execute{
                val response=openWeatherProvider.fetchOneCallForDesten(lat,lon)
                response?.let {
                    if (it.isSuccessful){
                        result=it.body()!!.weatherForecast
                    }else{
                        Log.d(TAG,response.errorBody().toString())
                    }
                }
            }
        return result
    }
    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param address address input from user
     * @return reference to  List of address objects
     */
    fun fetchLocation(address:String):List<GeocodingAddress>{
        var result:List<GeocodingAddress> = mutableListOf()
        executor.execute{
            //get address from provider and delete similar address by their name
            val response=positionStackProvider.fetchAddress(address)
            response?.let {
                if (response.isSuccessful){
                    response.body()!!.data?.let {
                        result=it.distinctBy {
                            it.name
                        }
                    }
                }else{
                    Log.d(TAG,response.errorBody().toString())
                }
            }
        }
        return result
    }
}