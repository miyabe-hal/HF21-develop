package com.pi12a082.hf21.domain.use_cases.login

import com.pi12a082.hf21.domain.FruityRepository
import com.pi12a082.hf21.domain.model.LoginCredentials

class LoginUseCase(private val repository: FruityRepository) {

    suspend operator fun invoke(loginCredentials: LoginCredentials) =
        repository.loginUser(loginCredentials)
}