package com.pi12a082.hf21.di

import com.pi12a082.hf21.data.remote.FruityApi
import com.pi12a082.hf21.data.repository.FruityRepositoryImpl
import com.pi12a082.hf21.domain.FruityRepository
import com.pi12a082.hf21.domain.use_cases.UseCases
import com.pi12a082.hf21.domain.use_cases.add_to_cart.AddToCartUseCase
import com.pi12a082.hf21.domain.use_cases.get_all_products.GetAllProductsUseCase
import com.pi12a082.hf21.domain.use_cases.get_cart.GetCartUseCase
import com.pi12a082.hf21.domain.use_cases.get_id_token.GetIdToken
import com.pi12a082.hf21.domain.use_cases.login.LoginUseCase
import com.pi12a082.hf21.domain.use_cases.remove_from_cart.RemoveCartUseCase
import com.pi12a082.hf21.domain.use_cases.user_signup.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFruityRepository(fruityApi: FruityApi): FruityRepository {
        return FruityRepositoryImpl(fruityApi = fruityApi)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: FruityRepository): UseCases =
        UseCases(
            getCartUseCase = GetCartUseCase(repository = repository),
            getAllProductsUseCase = GetAllProductsUseCase(repository = repository),
            loginUseCase = LoginUseCase(repository = repository),
            addToCartUseCase = AddToCartUseCase(repository = repository),
            getIdToken = GetIdToken(repository = repository),
            removeCartUseCase = RemoveCartUseCase(repository = repository),
            signupUseCase = SignupUseCase(repository = repository)
        )
}
