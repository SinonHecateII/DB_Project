package com.example.deliciousfood.models

data class RestaurantModel(
    var name: String,
    var image: String?,
    var mood: String,
    var menu: ArrayList<String>,
)