package com.pi12a082.hf21.util

fun parseErrorMessage(message: String): String {
    return when {
        message.contains("Conflict", ignoreCase = true) -> {
            "Email is already taken"
        }
        message.contains("Couldn't reach") -> {
            "Network Error"
        }
        else -> {
            "Something went wrong"
        }
    }
}