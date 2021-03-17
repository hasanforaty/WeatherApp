package come.hasan.foraty.r.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import come.hasan.foraty.r.weatherapp.model.Weather
import come.hasan.foraty.r.weatherapp.reposetory.MainReposetory

class ViewModel {
    private val reposetory=MainReposetory.newInstance()
    private val weathers= MutableLiveData<List<Weather>>()
    fun getWeathers(lat:Double,lon:Double): LiveData<List<Weather>> {
        return reposetory.fetchForecast(lat,lon)
    }
}