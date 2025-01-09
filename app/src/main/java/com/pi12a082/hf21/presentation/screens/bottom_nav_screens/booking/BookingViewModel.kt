package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.booking

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pi12a082.hf21.domain.model.NetworkProduct
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {

    // 初期状態を設定
    private val _state = mutableStateOf(BookingViewModelState())
    val state = _state

    // 商品を追加
    fun addProductToBooking(product: NetworkProduct) {
        _state.value = _state.value.copy(
            selectedProducts = _state.value.selectedProducts?.plus(product)
        )
    }

    // 予約情報の保存
    fun saveBookingInfo(location: String, date: String, plan: String) {
        _state.value = _state.value.copy(
            selectedLocation = location,
            selectedDate = date,
            selectedPlan = plan
        )
    }

    // 予約を確定する
    fun confirmBooking() {
        // ここでは予約の処理を行います（例えば、API呼び出しなど）
        // 実際にはAPIとの通信やデータベースへの保存などを行います
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                // ここにAPI呼び出しなどの処理を追加することができます
                // 仮に2秒の遅延をシミュレート
                kotlinx.coroutines.delay(2000)

                // 処理が成功した場合、選択された商品をクリア
                _state.value = _state.value.copy(
                    isLoading = false,
                    selectedLocation = "", // 予約後に場所情報をリセット
                    selectedDate = "", // 予約後に日時情報をリセット
                    selectedPlan = "", // 予約後にプラン情報をリセット
                    selectedProducts = emptyList(),
                    error = ""
                )
            } catch (e: Exception) {
                // エラーハンドリング
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "予約の処理中にエラーが発生しました"
                )
            }
        }
    }
}
