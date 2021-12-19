package com.dicoding.worldofgames.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.worldofgames.R
import com.dicoding.worldofgames.models.GameModel
import com.squareup.picasso.Picasso

class GameAdapter(private val dataList: List<GameModel>, private val onItemClick: (GameModel) -> Unit) :
    RecyclerView.Adapter<GameAdapter.GameAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.game_item,
            parent, false)

        return GameAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameAdapterViewHolder, position: Int) {
        val currentItem = dataList[position]

        Picasso.get().load(currentItem.thumbnail).into(holder.image)
        holder.tvName.text = currentItem.title
        holder.tvPublisher.text = currentItem.publisher

        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentItem)
        }
    }

    override fun getItemCount() = dataList.size

    class GameAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_game_item)
        val tvName: TextView = itemView.findViewById(R.id.tv_name_item)
        val tvPublisher: TextView = itemView.findViewById(R.id.tv_publisher_item)
    }
}
