package com.pi12a082.hf21.domain.use_cases.user_signup

import com.pi12a082.hf21.domain.FruityRepository
import com.pi12a082.hf21.domain.model.SignupResult
import com.pi12a082.hf21.domain.model.SignupUser
import com.pi12a082.hf21.util.Resource
import kotlinx.coroutines.flow.Flow

class SignupUseCase(private val repository: FruityRepository) {
    suspend operator fun invoke(signup: SignupUser):Flow<Resource<SignupResult>> = repository.signup(signup)
}