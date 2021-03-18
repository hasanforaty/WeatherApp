package come.hasan.foraty.r.weatherapp

import android.app.SearchManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import come.hasan.foraty.r.weatherapp.databinding.FragmentViewBinding
import come.hasan.foraty.r.weatherapp.databinding.ListItemsBinding
import come.hasan.foraty.r.weatherapp.model.Weather
import come.hasan.foraty.r.weatherapp.viewModel.ViewModel
import java.net.URL

class MainFragment: Fragment() {

    private lateinit var binding:FragmentViewBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var defaultReportButton:Button
    private lateinit var pickDateButton: Button
    private lateinit var getCityIdButton: Button
    private lateinit var searchWidget:SearchView
    private lateinit var viewModel: ViewModel
    private val forecastAdepter=ForecastAdepter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_view,container,false)
        //init ViewModel
        binding.viewModel= ViewModel.newInstance()
        binding.lifecycleOwner=this@MainFragment


        viewModel= ViewModel.newInstance()

        //implement View Objects
        recyclerView=binding.recyclerView
        defaultReportButton=binding.deReport
        pickDateButton=binding.pickData
        getCityIdButton=binding.cityId
        searchWidget=binding.searchView



        val searchManager= getSystemService(requireContext(),SearchManager::class.java)
        searchWidget.apply {
            // Assumes current activity is the searchable activity
            if (searchManager != null) {
                setSearchableInfo(searchManager.getSearchableInfo(SearchableActivity().componentName))
            }
            isIconifiedByDefault = false // Do not iconify the widget; expand it by default
        }




        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=forecastAdepter

        return binding.root
    }

    /**
     * override to implement observer and listeners
     */
    override fun onStart() {
        super.onStart()
        viewModel.weathers.observe(this.viewLifecycleOwner){
            forecastAdepter.submitList(it)
        }
    }

    inner class ForecastHolder(private val bindingObject:ListItemsBinding):RecyclerView.ViewHolder(bindingObject.root){

        fun binding(weather: Weather){
            bindingObject.Temperature.text=weather.temperature.day.toString().plus(weather.temperature.type)
            bindingObject.date.text=weather.date.toString()
            bindingObject.humidity.text=weather.humidity.toString().plus("%")
            if (weather.rain>0){
                bindingObject.rain.text=weather.rain.toString().plus("%")
            }else{
                bindingObject.rain.visibility=View.GONE
            }
            if(weather.snow>0){
                bindingObject.snow.text=weather.snow.toString().plus("%")
            }else{
                bindingObject.rain.visibility=View.GONE
            }
            val weatherConditionUrl=URL(weather.weatherCondition[0].pic)
            val bitmap=getImageBitMapFromUrl(weatherConditionUrl)
            bitmap?.let {
                bindingObject.statueImage.setImageBitmap(bitmap)
            }
        }
    }
    private object DiffUtilCallback:DiffUtil.ItemCallback<Weather>(){
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem==newItem
        }

    }
    inner class ForecastAdepter:ListAdapter<Weather,ForecastHolder>(DiffUtilCallback){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
            val view:ListItemsBinding=DataBindingUtil.inflate(layoutInflater,R.layout.list_items,parent,false)
            return ForecastHolder(view)
        }

        override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
            val weather=getItem(position)
            holder.binding(weather)
        }

    }
    fun getImageBitMapFromUrl(url:URL):Bitmap?{
        try {
            val urlStream=url.openConnection().getInputStream()
         return BitmapFactory.decodeStream(urlStream)
        }catch (exception:Exception){
            TODO(exception.toString())
        }
    }

}