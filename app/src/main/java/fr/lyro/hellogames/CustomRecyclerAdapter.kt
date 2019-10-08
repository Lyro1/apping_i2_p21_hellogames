package fr.lyro.hellogames

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomRecyclerAdapter(
    val context: Context,
    val data: List<GameLinkObject>) : //
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

    // called when a row is about to be displayed
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder!!.nameTextView.text = currentItem.name
        Glide.with(context).load(currentItem.picture).into(holder.logoImageView)
    }
}