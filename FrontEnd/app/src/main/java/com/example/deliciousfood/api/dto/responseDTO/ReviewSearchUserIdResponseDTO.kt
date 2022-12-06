package com.example.deliciousfood.api.dto.responseDTO

data class ReviewSearchUserIdResponseDTO(
    val result: List<ReviewSearchUserIdResult>
)

data class ReviewSearchUserIdResult(
    var content: String,
    val createdAt: String,
    val restaurantID: String,
    val restaurantName: String,
    val reviewID: String,
    var score: String,
    val writer: String
)