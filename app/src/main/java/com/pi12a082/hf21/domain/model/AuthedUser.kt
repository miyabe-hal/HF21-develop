package com.pi12a082.hf21.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthedUser(
    val id: String,
    val imageUrl: String,
    val name: String,
    val email: String,
)
