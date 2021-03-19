package come.hasan.foraty.r.weatherapp.reposetory

import android.util.Log
import come.hasan.foraty.r.weatherapp.api.PositionStackApi
import come.hasan.foraty.r.weatherapp.api.PositionStackResponse
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val API_KEY: String = "ef8a2da88c817d728a8229f45c579b31"
private const val TAG: String = "PositionStackProvider"

class PositionStackProvider private constructor() {
    private val networkAddress: String = "http://api.positionstack.com/v1/forward?access_key=$API_KEY"
    private val positionApi: PositionStackApi

    companion object {
        fun newInstance(): PositionStackProvider = PositionStackProvider()
    }

    init {
        val tlsSpecs: List<*> = mutableListOf(ConnectionSpec.MODERN_TLS,ConnectionSpec.CLEARTEXT)

        val client = OkHttpClient.Builder()
            .connectionSpecs(tlsSpecs as MutableList<ConnectionSpec>)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.positionstack.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        positionApi = retrofit.create(PositionStackApi::class.java)
    }

    fun fetchAddress(address: String): Call<PositionStackResponse> {
        val queryString = properStringQuery(address)
        val uri = networkAddress + queryString
        Log.d(TAG, "my Uri is $uri")
        return positionApi.fetchAddress(uri)

    }

    private fun properStringQuery(query: String): String {
        return "&query=".plus(
            query
                .replace("%", " ")
                .replace("+", " ")
                .replace("-", " ")
                .replace("_", " ")
                .replace(" ", "%20")
        )
    }
}