package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.pi12a082.hf21.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCases: UseCases,
) : ViewModel() {


}