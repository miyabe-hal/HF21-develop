package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.booking

import com.pi12a082.hf21.domain.model.NetworkProduct

// 予約画面の状態を表現
data class BookingScreenState(
    val isLoading: Boolean, // ローディング状態
    val selectedProducts: List<NetworkProduct>?, // 選択された商品リスト
    val error: String, // エラーメッセージ
)