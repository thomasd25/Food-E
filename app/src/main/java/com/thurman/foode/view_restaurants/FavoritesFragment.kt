package com.thurman.foode.view_restaurants

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.thurman.foode.R
import com.thurman.foode.Utility.FirebaseUtil
import com.thurman.foode.add_restaurant.AddRestaurantActivity
import com.thurman.foode.models.FoodItem
import com.thurman.foode.models.Restaurant

class FavoritesFragment : Fragment() {

    var restaurants = ArrayList<Restaurant>()
    lateinit var recyclerAdapter: FavoriteRestaurantListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater!!.inflate(R.layout.favorites_tab, container, false)
        setupRecyclerView(view)
        var addButton: FloatingActionButton = view.findViewById(R.id.add_icon)
        addButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, AddRestaurantActivity::class.java)
            startActivityForResult(intent, 200)
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        getUserRestaurantData(recyclerAdapter)
    }

    private fun setupRecyclerView(view: View){
        recyclerAdapter = FavoriteRestaurantListAdapter(restaurants, context!!)
        getUserRestaurantData(recyclerAdapter)
        var favRestaurantsList = view.findViewById<RecyclerView>(R.id.favorite_restaurants_list)
        favRestaurantsList.layoutManager = LinearLayoutManager(activity)
        favRestaurantsList.adapter = recyclerAdapter
        favRestaurantsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerAdapter.onItemClick = {restaurant -> onRestaurantClicked(restaurant)}
    }

    private fun onRestaurantClicked(restaurant: Restaurant){
        var restaurantDetailActivity = RestaurantDetailActivity()
        val intent = Intent(activity, restaurantDetailActivity.javaClass)
        intent.putExtra("restaurantUuid", restaurant.uuid)
        startActivity(intent)
    }

    private fun getUserRestaurantData(recyclerAdapter: FavoriteRestaurantListAdapter){

        val restaurantsListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                restaurants.clear()
                for (restaurantSnapshot in dataSnapshot.children){
                    restaurants.add(FirebaseUtil.getRestaurantFromSnapshot(restaurantSnapshot))
                }
                for (restaurant in restaurants){
                    FirebaseUtil.getRestaurantImage(restaurant, recyclerAdapter)
                }
                recyclerAdapter.notifyDataSetChanged()
                setLoading(false)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }

        }
        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("restaurants").addListenerForSingleValueEvent(restaurantsListener)
    }

    fun setLoading(loading: Boolean){
        var loadingContainer = view!!.findViewById<LinearLayout>(R.id.loading_container)
        var contentContainer = view!!.findViewById<FrameLayout>(R.id.content_container)
        if (!loading){
            loadingContainer.visibility = View.GONE
            contentContainer.visibility = View.VISIBLE
        }
    }

//    fun updateResults(){
//        getUserRestaurantData(recyclerAdapter)
//    }

}