package com.pi12a082.hf21.domain.model

data class CartAdd(
    val sessionId: String,
    val productId: String,
    val quantity: Int
)
