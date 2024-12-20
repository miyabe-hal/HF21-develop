package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.cart

import com.pi12a082.hf21.domain.model.CartProduct

data class CartItemListState(
    val isLoading: Boolean = false,
    val items: List<CartProduct> = emptyList(),
    val error: String = "",
)