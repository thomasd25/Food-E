package com.thurman.foode.add_restaurant

import com.thurman.foode.R

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thurman.foode.models.Restaurant
import kotlinx.android.synthetic.main.favorite_restaurant_list_item.view.*

class SearchRestaurantListAdapter(val items: ArrayList<Restaurant>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((Restaurant) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.search_restaurant_list_item, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var restaurantViewHolder = holder as RestaurantViewHolder
        var restaurant = items.get(position)
        restaurantViewHolder.restaurantNameTextView.text = restaurant.name
        restaurantViewHolder.restaurantAddressTextView.text = restaurant.address
        if (restaurant.imageUri != null){
            Picasso.with(context).load(restaurant.imageUri).into(restaurantViewHolder.imageView)
        } else {
            restaurantViewHolder.imageView.setImageDrawable(context.resources.getDrawable(R.drawable.question_mark_icon))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class RestaurantViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var restaurantNameTextView = view.restaurant_name
        var restaurantAddressTextView = view.restaurant_address
        var imageView = view.restaurant_image

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }

}

