package com.pi12a082.hf21.presentation.screens.signup

import  androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi12a082.hf21.domain.model.SignupUser
import com.pi12a082.hf21.domain.use_cases.UseCases
import com.pi12a082.hf21.util.Resource
import com.pi12a082.hf21.util.parseErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(SignupViewModelState())

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )


    fun onNameInputChanged(nameInput: String) {
        viewModelState.update {
            it.copy(nameInput = nameInput)
        }
    }

    fun onEmailInputChanged(emailInput: String) {
        viewModelState.update {
            it.copy(emailInput = emailInput)
        }
    }

    fun onPasswordInputChanged(passwordInput: String) {
        viewModelState.update {
            it.copy(passwordInput = passwordInput)
        }
    }

    fun onPasswordConfirmationInputChanged(passwordConfirmationInput: String) {
        viewModelState.update {
            it.copy(passwordConfirmationInput = passwordConfirmationInput)
        }
    }

    fun toggleShowPassword(show: Boolean) {
        viewModelState.update {
            it.copy(showPassword = show)
        }
    }

    fun signup(signup: SignupUser) {
        viewModelScope.launch {
            useCases.signupUseCase(signup = signup).onEach { result ->
                viewModelState.update { state ->
                    when (result) {
                        is Resource.Success -> state.copy(
                            result = result.data?.name ?: "Some result",
                        errorMessage = "", isLoading = false)
                        is Resource.Error -> {
                            state.copy(errorMessage = parseErrorMessage(result.message ?: "Error"),
                                isLoading = false, result = "")
                        }
                        is Resource.Loading -> state.copy(isLoading = true,
                            errorMessage = "",
                            result = "")
                    }
                }
            }.launchIn(this)
        }
    }

}