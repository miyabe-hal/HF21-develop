package com.pi12a082.hf21.domain.use_cases.add_to_cart

import com.pi12a082.hf21.domain.FruityRepository
import com.pi12a082.hf21.domain.model.CartAdd

class AddToCartUseCase(private val repository: FruityRepository) {
    suspend operator fun invoke(authToken: String, item: CartAdd) =
        repository.addToCart(authToken = authToken, item = item)
}