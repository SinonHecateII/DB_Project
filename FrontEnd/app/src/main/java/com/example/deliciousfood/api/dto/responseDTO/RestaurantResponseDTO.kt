package com.example.deliciousfood.api.dto.responseDTO

data class RestaurantResponseDTO(
    val result: List<RestaurantResponseModel>
)

data class RestaurantResponseModel(
    val location: String,
    val mood: String,
    val name: String,
    val photoCnt: String,
    val restaurantID: String,
    val writerID: String
)