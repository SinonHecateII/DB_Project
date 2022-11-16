package com.example.deliciousfood.models

data class RestaurantModel(
    var restaurantId:Int,
    var name: String,
    var photoCnt: Int,
    var mood: String,
    var menu: ArrayList<String>,
)