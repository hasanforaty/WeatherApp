package come.hasan.foraty.r.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import come.hasan.foraty.r.weatherapp.model.GeocodingAddress
import come.hasan.foraty.r.weatherapp.model.Weather
import come.hasan.foraty.r.weatherapp.reposetory.MainRepository

class ViewModel private constructor(){
    companion object{
        fun newInstance():ViewModel =ViewModel()
    }
    private val repository=MainRepository.newInstance()
    private val weatherList= MutableLiveData<List<Weather>>()
    private var locationList = MutableLiveData<List<GeocodingAddress>>()
    val weathers=Transformations.switchMap(locationList){
        if (it!=null&&it.isNotEmpty()){
            val firstAddress=it[0]
            updateWeathers(firstAddress.latitude,firstAddress.longitude)
        }
        weatherList

    }

    /**
     * update report for given location
     * @param lat lat of chosen location
     * @param lon lan of chosen location
     * @return reference to liveData of list of weathers
     */
    private fun updateWeathers(lat:Double,lon:Double): LiveData<List<Weather>> {
        val result=repository.fetchForecast(lat,lon)
        weatherList.postValue(result.value)
        return weatherList
    }

    /**
     * @param addressString received String for user for location
     * @return return a reference to liveData of list of address
     */
    fun updateAddress(addressString:String){
        if (locationList.value==null){
            locationList=repository.fetchLocation(addressString)
        }else{
            repository.fetchLocation(addressString)
        }
    }

}