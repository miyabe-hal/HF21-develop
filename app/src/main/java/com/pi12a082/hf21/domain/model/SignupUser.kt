package com.pi12a082.hf21.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignupUser(
    val name: String,
    val password: String,
    val passwordConfirmation: String,
    val email: String,
)

@Serializable
data class SignupResult(
    val name: String
)
