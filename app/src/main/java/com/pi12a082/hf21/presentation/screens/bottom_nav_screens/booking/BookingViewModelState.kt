package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.booking

import com.pi12a082.hf21.domain.model.NetworkProduct

// 予約画面のViewModel状態
data class BookingViewModelState(
    val isLoading: Boolean = false, // ローディング状態
    val selectedProducts: List<NetworkProduct>? = emptyList(), // 選択された商品リスト
    val error: String = "", // エラーメッセージ
) {
    // UIに渡す状態を変換
    fun toUiState() = BookingScreenState(
        isLoading, selectedProducts, error
    )
}