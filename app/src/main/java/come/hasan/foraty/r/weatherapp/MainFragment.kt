package come.hasan.foraty.r.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import come.hasan.foraty.r.weatherapp.databinding.FragmentViewBinding
import come.hasan.foraty.r.weatherapp.viewModel.ViewModel

class MainFragment: Fragment() {

    private lateinit var binding:FragmentViewBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var defaultReportButton:Button
    private lateinit var pickDateButton: Button
    private lateinit var getCityIdButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_view,container,false)
        //init ViewModel
        binding.viewModel= ViewModel()
        binding.lifecycleOwner=this@MainFragment

        //implement View Objects
        recyclerView=binding.recyclerView
        defaultReportButton=binding.deReport
        pickDateButton=binding.pickData
        getCityIdButton=binding.cityId

        recyclerView.layoutManager=LinearLayoutManager(context)
        TODO("Implement Adapter and holder")

        return binding.root
    }
}