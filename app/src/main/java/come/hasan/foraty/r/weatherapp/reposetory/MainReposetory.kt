package come.hasan.foraty.r.weatherapp.reposetory

import androidx.lifecycle.LiveData
import come.hasan.foraty.r.weatherapp.model.Weather

private const val TAG="MainReposetory"
class MainReposetory private constructor(){
    companion object{
        fun newInstance(): MainReposetory {
            return MainReposetory()
        }
    }
    private val openWeatherProvider=OpenWeatherProvider.newInstance()

    /**
     * fetchForecast fetch data from openWeatherProvider
     * @param lat lat of chosen location
     * @param lon lan of chosen location
     * @return Live Data of List of Weather objects
     */
    fun fetchForecast(lat:Double, lon:Double):LiveData<List<Weather>>{
        return openWeatherProvider.fetchOneCallForDesten(lat,lon)
    }
}