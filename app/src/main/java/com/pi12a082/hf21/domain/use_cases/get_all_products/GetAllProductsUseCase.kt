package com.pi12a082.hf21.domain.use_cases.get_all_products

import com.pi12a082.hf21.domain.FruityRepository
import com.pi12a082.hf21.domain.model.AllNetworkProducts
import com.pi12a082.hf21.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class GetAllProductsUseCase(private val repository: FruityRepository) {
    suspend operator fun invoke() = repository.getAllProducts()
}