package com.pi12a082.hf21.domain

import com.pi12a082.hf21.domain.model.*
import com.pi12a082.hf21.util.Resource
import kotlinx.coroutines.flow.Flow

interface FruityRepository {
    suspend fun getCart(authToken: String, sessionId: ShoppingSession): CartItemList

    suspend fun getAllProducts(): Flow<Resource<AllNetworkProducts>>

    suspend fun login(authToken: String): FUser

    suspend fun addToCart(authToken: String, item: CartAdd)

    suspend fun getIdToken(): String

    suspend fun removeFromCart(authToken: String, item: RemoveProduct)

    suspend fun signup(signup: SignupUser): Flow<Resource<SignupResult>>

    suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<LoginResult>>
}