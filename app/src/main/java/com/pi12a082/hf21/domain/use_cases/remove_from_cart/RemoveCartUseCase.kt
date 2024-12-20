package com.pi12a082.hf21.domain.use_cases.remove_from_cart

import com.pi12a082.hf21.domain.FruityRepository
import com.pi12a082.hf21.domain.model.RemoveProduct

class RemoveCartUseCase(private val repository: FruityRepository) {
    suspend operator fun invoke(authToken: String, item: RemoveProduct) =
        repository.removeFromCart(authToken = authToken, item = item)
}