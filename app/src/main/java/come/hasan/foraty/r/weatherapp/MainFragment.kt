package come.hasan.foraty.r.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import come.hasan.foraty.r.weatherapp.databinding.FragmentViewBinding
import come.hasan.foraty.r.weatherapp.databinding.ListItemsBinding
import come.hasan.foraty.r.weatherapp.model.Weather
import come.hasan.foraty.r.weatherapp.reposetory.MainRepository

private const val TAG = "MainFragment"

class MainFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var dailyReportButton: Button
    private lateinit var pickDateButton: Button
    private lateinit var hourlyReportButton: Button
    private lateinit var searchWidget: SearchView
    private val forecastAdepter = ForecastAdepter()

    private val reposetory = MainRepository.newInstance()
    private var forecastLiveData = reposetory.forecasts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view, container, false)
        //init ViewModel
        binding.lifecycleOwner = this@MainFragment.viewLifecycleOwner

        binding.reposetory = reposetory

        //implement View Objects
        recyclerView = binding.recyclerView
        dailyReportButton = binding.dailyForecast
        pickDateButton = binding.pickDate
        hourlyReportButton = binding.hourlyReport
        searchWidget = binding.searchView

        //putting Listener of Search widget to this fragment
        searchWidget.setOnQueryTextListener(this)
        //enabling search button
        searchWidget.isSubmitButtonEnabled = true

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = forecastAdepter

        return binding.root
    }

    /**
     * override to implement observer and listeners
     */
    override fun onStart() {
        super.onStart()
        forecastLiveData.observe(this.viewLifecycleOwner) {
            it?.let {
                Log.d(TAG, it.toString())
                forecastAdepter.submitList(it)
            }
        }

    }

    //abstract method of SearchView.SetOnQueryTextListener
    /**
     * @param query final Text inputted in Search Widget
     * @return do you handle the search of  Library need to handle it ?
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotEmpty()) {
            //setting repository to search for it
            reposetory.fetchLocation(query)

        }
        return true
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    /**
     * DiffUtillCallback instance of DiffUtil.ItemCallback needed for ListAdapter
     */
    private object DiffUtilCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

    }

    /**
     * ForecastHolder ViewHolder of Main recyclerView
     * @param bindingObject instance of Binding Object related to View
     */
    inner class ForecastHolder(private val bindingObject: ListItemsBinding) :
        RecyclerView.ViewHolder(bindingObject.root) {
        //binding all of component
        fun binding(weather: Weather) {
            bindingObject.Temperature.text =
                weather.temperature.day.toString().plus("C")
            bindingObject.date.text = weather.date.toString()
            bindingObject.humidity.text = getString(R.string.humidity,weather.humidity.toString()).plus("%")
            bindingObject.rain.text = getString(R.string.rain,weather.rain.toString()).plus("%")
            bindingObject.snow.text = getString(R.string.snow,weather.snow.toString()).plus("%")
            //getting pic from Url and put it in statueImage
            Picasso.get()
                .load(weather.weatherCondition[0].pic)
                .resize(50,50)
                .into(bindingObject.statueImage)
        }
    }

    /**
     * ForecastAdapter Adapter of main recyclerView
     */
    inner class ForecastAdepter : ListAdapter<Weather, ForecastHolder>(DiffUtilCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
            val view: ListItemsBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_items, parent, false)
            return ForecastHolder(view)
        }

        override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
            val weather = getItem(position)
            holder.binding(weather)
        }

    }


}