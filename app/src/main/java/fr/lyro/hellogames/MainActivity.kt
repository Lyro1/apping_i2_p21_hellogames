package fr.lyro.hellogames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // A List to store our GameLinkObjects
    val data = arrayListOf<GameLinkObject>()
    // The base URL where the WebService is located
    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
    // Use GSON library to create our JSON parser
    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
    // Create a Retrofit client object targeting the provided URL
    // and add a JSON converter (because we are expecting json responses) val retrofit = Retrofit.Builder()
    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverter)
        .build()

    // Use the client to create a service:
    // an object implementing the interface to the WebService
    val service: WSInterface = retrofit.create(WSInterface::class.java)


    val callback = object : Callback<List<GameLinkObject>> {

        override fun onFailure(call: Call<List<GameLinkObject>>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }

        override fun onResponse(
            call: Call<List<GameLinkObject>>?,
            response: Response<List<GameLinkObject>>?
        ) {
            Log.d("TAG", "On Response")
            // Code here what happens when WebService responds
            if (response != null) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        data.addAll(responseData)
                        Log.d("TAG", "WebService success : " + data.size)

                        activity_main_list_games.setHasFixedSize(true)
                        val recyclerAdapter = CustomRecyclerAdapter(this@MainActivity, responseData)
                        activity_main_list_games.adapter = recyclerAdapter
                    }
                }
                else {
                    Log.d("TAG", "Not 200 code")
                }
            }
            else {
                Log.d("TAG", "No response")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Finally, use the service to enqueue the callback
        // This will asynchronously call the method
        service.listGames().enqueue(callback)
    }
}
