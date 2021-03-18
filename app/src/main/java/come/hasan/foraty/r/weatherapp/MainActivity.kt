package come.hasan.foraty.r.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //going to main fragment
        navigation(MainFragment())
    }

    /**
     * navigation navigate to given fragment
     * @param fragment that we wanna go
     */
    fun navigation(fragment:Fragment){
        val currentFragment=supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment==null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
        }else{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit()
        }
    }
}