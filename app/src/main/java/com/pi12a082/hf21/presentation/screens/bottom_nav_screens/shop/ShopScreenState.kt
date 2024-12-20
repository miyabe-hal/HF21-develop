package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.shop

import com.pi12a082.hf21.domain.model.NetworkProduct

data class ShopScreenState(
    val isLoading: Boolean,
    val products: List<NetworkProduct>?,
    val error: String,
)

data class ShopViewModelState(
    val isLoading: Boolean = false,
    val products: List<NetworkProduct>? = emptyList(),
    val error: String = "",
) {
    fun toUiState() = ShopScreenState(
        isLoading, products, error
    )
}