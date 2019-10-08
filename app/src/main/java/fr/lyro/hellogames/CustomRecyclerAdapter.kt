package fr.lyro.hellogames

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomRecyclerAdapter(
    val context: Context,
    val data: List<GameLinkObject>,
    val service: WSInterface) :
    RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.findViewById(R.id.name)
            val logoImageView: ImageView = itemView.findViewById(R.id.logo)
        }

        override fun getItemCount(): Int {
            return data.size
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val rowView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ViewHolder(rowView)
        return viewHolder
    }

    // Functions required for API calls

    val gameDetailsCallback = object : Callback<GameObject> {

        override fun onFailure(call: Call<GameObject>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }

        override fun onResponse(
            call: Call<GameObject>?,
            response: Response<GameObject>?
        ) {
            Log.d("TAG", "On Response")
            // Code here what happens when WebService responds
            if (response != null) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.d("TAG", "WebService success : " + data.size)

                        val explicitIntent = Intent(context, GameDetailsActivity::class.java)

                        explicitIntent.putExtra("id", responseData.id)
                        explicitIntent.putExtra("name", responseData.name)
                        explicitIntent.putExtra("type", responseData.type)
                        explicitIntent.putExtra("players", responseData.players)
                        explicitIntent.putExtra("year", responseData.year)
                        explicitIntent.putExtra("url", responseData.url)
                        explicitIntent.putExtra("picture", responseData.picture)
                        explicitIntent.putExtra("description_fr", responseData.description_fr)
                        explicitIntent.putExtra("description_en", responseData.description_en)

                        startActivity(context, explicitIntent, null)
                    }
                }
                else {
                    Log.d("TAG", "Error while getting response. Code " + response.code().toString())
                }
            }
            else {
                Log.d("TAG", "No response")
            }
        }
    }

    fun openDetails(id: Int) {
        // Call the API with given id
        service.getGameDetails(id).enqueue(gameDetailsCallback)
    }

    // called when a row is about to be displayed
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        Log.d("TAG", "Displaying " + currentItem.name )
        holder!!.nameTextView.text = currentItem.name
        Glide.with(context).load(currentItem.picture).into(holder.logoImageView)

        // Manage onClick event
        holder.itemView.setOnClickListener { view ->
            Log.d("TAG", "Clicked on element from list")
            openDetails(currentItem.id)
        }
    }
}