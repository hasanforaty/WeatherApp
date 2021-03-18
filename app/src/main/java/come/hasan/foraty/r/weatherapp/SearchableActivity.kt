package come.hasan.foraty.r.weatherapp

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import come.hasan.foraty.r.weatherapp.viewModel.ViewModel

//activity that handle Search Widget.
private const val TAG="SearchableActivity"
class SearchableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Intent.ACTION_SEARCH==intent.action){
            intent.getStringExtra(SearchManager.QUERY)?.let {
                Log.d(TAG,"Reach Searchable Activity")
                ViewModel.newInstance().updateAddress(it)
            }
        }

    }

}