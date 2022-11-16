package com.example.deliciousfood.api.dto.responseDTO

data class RestaurantResponseDTO(
    val result: List<Result>
)

data class Result(
    val location: String,
    val mood: String,
    val name: String,
    val photoCnt: String,
    val restaurantID: String
)