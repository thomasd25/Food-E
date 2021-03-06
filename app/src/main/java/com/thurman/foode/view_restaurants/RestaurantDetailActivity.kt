package com.thurman.foode.view_restaurants

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.thurman.foode.add_restaurant.AddOrEditFoodItemFragment
import com.thurman.foode.add_restaurant.ManualEntryFragment
import com.thurman.foode.models.FoodItem
import com.thurman.foode.models.Restaurant


class RestaurantDetailActivity : FragmentActivity() {

    var restaurantDetailFragment = RestaurantDetailFragment()
    var restaurantToEdit: Restaurant? = null
    var foodItemToEdit: FoodItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var restaurantUuid = intent.getStringExtra("restaurantUuid")
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var bundle = Bundle()
        bundle.putString("restaurantUuid", restaurantUuid)
        restaurantDetailFragment.arguments = bundle
        fragmentTransaction.replace(android.R.id.content, restaurantDetailFragment)
        fragmentTransaction.commit()
    }

    fun editRestaurant(restaurant: Restaurant){
        restaurantToEdit = restaurant
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var editFragment = ManualEntryFragment()
        var bundle = Bundle()
        bundle.putBoolean("editing", true)
        bundle.putString("restaurantUuid", restaurant.uuid)
        editFragment.arguments = bundle
        fragmentTransaction.replace(android.R.id.content, editFragment)
        fragmentTransaction.commit()
    }

    fun onEditFinished(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(android.R.id.content, restaurantDetailFragment)
        fragmentTransaction.commit()
    }

    fun editFoodItem(foodItem: FoodItem, restaurantUuid: String){
        foodItemToEdit = foodItem
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var editFragment = AddOrEditFoodItemFragment()
        var bundle = Bundle()
        bundle.putString("restaurantUuid", restaurantUuid)
        bundle.putString("foodItemUuid", foodItem.uuid)
        bundle.putBoolean("editing", true)
        editFragment.arguments = bundle
        fragmentTransaction.replace(android.R.id.content, editFragment)
        fragmentTransaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Check if from city search
        if (data != null){
            var citySearchResults = data!!.getStringExtra("citySearch")

        }
    }
}