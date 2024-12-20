package com.pi12a082.hf21.domain.use_cases.get_id_token

import com.pi12a082.hf21.domain.FruityRepository
import javax.inject.Inject

class GetIdToken @Inject constructor(private val repository: FruityRepository) {
    suspend operator fun invoke(string: String) = repository.getIdToken()
}