package com.pi12a082.hf21.data.remote

import com.pi12a082.hf21.domain.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FruityApi {

    @GET("/api/me")
    suspend fun login(@Header("Authorization") authToken: String): FUser


    @GET("/api/product")
    suspend fun getAllProducts(): AllNetworkProducts

    @POST("/api/getMyCart")
    suspend fun getCart(
        @Header("Authorization") authToken: String,
        @Body sessionId: ShoppingSession,
    ): CartItemList

    @POST("/api/addCart")
    suspend fun addToCart(@Header("Authorization") authToken: String, @Body item: CartAdd)

    @POST("/api/deleteCart")
    suspend fun deleteFromCart(
        @Header("Authorization") authToken: String,
        @Body item: RemoveProduct,
    )

    @POST("/api/users")
    suspend fun signup(@Body signup: SignupUser): SignupResult

    @POST("/api/sessions")
    suspend fun loginUser(@Body loginCredentials: LoginCredentials): LoginResult
}