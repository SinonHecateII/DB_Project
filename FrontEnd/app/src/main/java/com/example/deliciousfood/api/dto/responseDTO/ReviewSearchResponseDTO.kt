package com.example.deliciousfood.api.dto.responseDTO

data class ReviewSearchResponseDTO(
    val result: List<ReviewResponseModel>
)

data class ReviewResponseModel(
    val content: String,
    val createdAt: String,
    val restaurantID: String,
    val reviewID: String,
    val nickname: String,
    val score: String,
    val writer: String
)