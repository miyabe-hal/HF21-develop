package com.pi12a082.hf21.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    val email: String,
    val password: String,
)
