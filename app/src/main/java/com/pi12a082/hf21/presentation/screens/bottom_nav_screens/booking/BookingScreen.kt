package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.pi12a082.hf21.R
import com.pi12a082.hf21.presentation.components.CategoryComponent
import com.pi12a082.hf21.presentation.components.SearchTextField
import com.pi12a082.hf21.presentation.components.ShopHeaderComponent
import androidx.compose.ui.graphics.Color
//import com.kelvinbush.nectar.R
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import java.util.Calendar
import java.text.SimpleDateFormat
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import java.util.*

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun BookingScreen(
    navController: NavHostController, // ナビゲーションコントローラー
    bookingViewModel: BookingViewModel = hiltViewModel() // ShopViewModelをHiltでインジェクト
) {
    // 検索キーワードの状態
    var searchItem by remember { mutableStateOf("") }

    // 場所の入力状態
    var location by remember { mutableStateOf("") }

    // 日時の選択状態
    var selectedDate by remember { mutableStateOf("") }

    // プランの選択状態
    var selectedPlan by remember { mutableStateOf("Plan A") }

    // 日付選択のためのカレンダーダイアログ
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // 時間選択ダイアログを表示する関数
    fun showTimePicker(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                // 分を2桁表示にフォーマット
                val formattedMinute = String.format("%02d", selectedMinute)
                // 時間と分を組み合わせて表示
                selectedDate = "$selectedYear/${selectedMonth + 1}/$selectedDay $selectedHour:$formattedMinute"
            },
            hour,
            minute,
            true // 24時間形式を使用
        )
        timePickerDialog.show()
    }

    // 日付選択のためのカレンダーダイアログ
    fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // 日付が選択された場合、時間選択ダイアログを呼び出す
                showTimePicker(selectedYear, selectedMonth, selectedDay)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    // MaterialDatePickerを使うための準備
//    val context = LocalContext.current
//    val datePicker = remember {
//        MaterialDatePicker.Builder.datePicker()
//            .setTitleText("日付を選択")
//            .build()
//    }
//
//    // 日付選択のためのカレンダーダイアログを表示する関数
//    fun showDatePicker() {
//        datePicker.addOnPositiveButtonClickListener { selection ->
//            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
//            selectedDate = sdf.format(Date(selection))
//        }
//
//        // FragmentActivityへのキャスト
//        val fragmentActivity = context as? FragmentActivity
//        fragmentActivity?.let {
//            datePicker.show(it.supportFragmentManager, "DATE_PICKER")
//        }
//    }

    // UIの構築
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // ショップのヘッダーコンポーネント
        ShopHeaderComponent()

        // 検索用のテキストフィールド
        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })

        // スクロール可能なフォーム
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // スクロール領域を拡大
        ) {
            item {
                // 場所の入力フォーム
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("場所") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            item {
                // 時計のアイコンを追加
                IconButton(
                    onClick = { showDatePicker() },
//                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24), // 時計のアイコンを指定
                        contentDescription = "Clock",
                        tint = Color.Black
                    )
                }// 「日時を選択する」テキスト
                Text(
                    text = "   日時を選択する",
                    modifier = Modifier,
//                        .padding(start = 4.dp),  // テキストとアイコンの間に少しスペースを追加
                    color = Color.Black
                )
                // 日時の入力フォーム
                TextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("日時") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            // DatePickerDialogを表示
                            showDatePicker()
                        },
                    readOnly = true, // ユーザーが手入力しないようにする
                    enabled = true // ボタンを有効にする
                )
            }

            item {
                // 料金プランの選択フォーム
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("料金プランの選択", style = MaterialTheme.typography.h6)

                    // 各プランの選択肢
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPlan == "6時間貸出パック",
                            onClick = { selectedPlan = "6時間貸出パック" }
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text("6時間貸出パック", modifier = Modifier.align(Alignment.Start))
                            Image(
                                painter = painterResource(id = R.drawable.coke), // 6時間貸出パックの画像
                                contentDescription = "6時間貸出パック",
                                modifier = Modifier.size(40.dp) // アイコンサイズ
                            )
                            Text("1,100円（税込）", modifier = Modifier.align(Alignment.Start))
                            Text(
                                text = "お手軽に日常使い、ちょっとしたお出かけにぴったり！",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }

                    // 他のプラン（同様に表示）
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPlan == "1日貸出パック",
                            onClick = { selectedPlan = "1日貸出パック" }
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text("1日貸出パック", modifier = Modifier.align(Alignment.Start))
                            Image(
                                painter = painterResource(id = R.drawable.apple_image), // 1日貸出パックの画像
                                contentDescription = "1日貸出パック",
                                modifier = Modifier.size(40.dp) // アイコンサイズ
                            )
                            Text("2,200円（税込）", modifier = Modifier.align(Alignment.Start))
                            Text(
                                text = "1日の冒険をバッテリーと共に！お出かけ全力サポート",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }

                    // 5日間貸出パック
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPlan == "5日間貸出パック",
                            onClick = { selectedPlan = "5日間貸出パック" }
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text("5日間貸出パック", modifier = Modifier.align(Alignment.Start))
                            Image(
                                painter = painterResource(id = R.drawable.banana_image), // 5日間貸出パックの画像
                                contentDescription = "5日間貸出パック",
                                modifier = Modifier.size(40.dp) // アイコンサイズ
                            )
                            Text("8,800円（税込）", modifier = Modifier.align(Alignment.Start))
                            Text(
                                text = "旅行や出張に最適！長期間使える充実プラン",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }

        // 予約するボタンを画面下部に1つ表示
        Button(
            onClick = {
                // 予約画面に遷移
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFF2F3F2), // 背景色を設定
                contentColor = Color(0xFF53B175)
            ),
            modifier = Modifier
                .fillMaxWidth() // ボタンが画面いっぱいに広がる
                .padding(horizontal = 16.dp) // 左右のパディング
//                .align(Alignment.BottomCenter) // 画面下部に配置
        ) {
            Text(
                text = "予約する",
                color = MaterialTheme.colors.primary, // ボタン内の文字色（任意）
                style = MaterialTheme.typography.button // 文字スタイル（任意）
            )
        }
    }
}

//@ExperimentalMaterialApi
//@ExperimentalCoilApi
//@Composable
//fun BookingScreen(
//    navController: NavHostController, // ナビゲーションコントローラー
//    bookingViewModel: BookingViewModel = hiltViewModel() // ShopViewModelをHiltでインジェクト
//) {
//    // 検索キーワードの状態
//    var searchItem by remember { mutableStateOf("") }
//
//    // 場所の入力状態
//    var location by remember { mutableStateOf("") }
//
//    // 日時の選択状態
//    var selectedDate by remember { mutableStateOf("") }
//
//    // プランの選択状態
//    var selectedPlan by remember { mutableStateOf("Plan A") }
//
//    // UIの構築
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // ショップのヘッダーコンポーネント
//        ShopHeaderComponent()
//
//        // 検索用のテキストフィールド
//        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })
//
//        // 場所の入力フォーム
//        TextField(
//            value = location,
//            onValueChange = { location = it },
//            label = { Text("場所") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
//
//        // 日時の入力フォーム
//        TextField(
//            value = selectedDate,
//            onValueChange = { selectedDate = it },
//            label = { Text("日時") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            readOnly = true,
//            enabled = true // 日時選択ボタンを有効にする
//        )
//
//        // プランの選択フォーム
//        Column(
//            modifier = Modifier.fillMaxWidth().padding(16.dp)
//        ) {
//            Text("料金プランの選択", style = MaterialTheme.typography.h6)
//
//            // 各プランの選択肢
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                RadioButton(
//                    selected = selectedPlan == "6時間貸出パック",
//                    onClick = { selectedPlan = "6時間貸出パック" }
//                )
//                Column(modifier = Modifier.padding(start = 8.dp)) {
//                    Text("6時間貸出パック", modifier = Modifier.align(Alignment.Start))
//                    Image(
//                        painter = painterResource(id = R.drawable.coke), // 6時間貸出パックの画像
//                        contentDescription = "6時間貸出パック",
//                        modifier = Modifier.size(40.dp) // アイコンサイズ
//                    )
//                    Text(
//                        text = "お手軽に日常使い、ちょっとしたお出かけにぴったり！",
//                        style = MaterialTheme.typography.body2
//                    )
//                }
//            }
////@Composable
////fun BookingScreen(
////    navController: NavHostController, // ナビゲーションコントローラー
////    bookingViewModel: BookingViewModel = hiltViewModel(), // ShopViewModelをHiltでインジェクト
////) {
////    // 仮のデータを作成
////    val products = listOf(
////        Product("Apple", "Fruit", "Fresh apple", R.drawable.apple_image),
////        Product("Banana", "Fruit", "Ripe banana", R.drawable.banana_image),
////        Product("Laptop", "Electronics", "Powerful laptop", R.drawable.laptop_image),
////        Product("Headphones", "Electronics", "Noise-canceling headphones", R.drawable.headphones_image)
////    )
////
////    // 検索キーワードの状態
////    var searchItem by remember { mutableStateOf("") }
////
////    // 場所の入力状態
////    var location by remember { mutableStateOf("") }
////
////    // 日時の選択状態
////    var selectedDate by remember { mutableStateOf("") }
////
////    // プランの選択状態
////    var selectedPlan by remember { mutableStateOf("Plan A") }
////
////    // UIの構築
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally, // 中央揃え
////        modifier = Modifier.fillMaxSize() // 画面全体を占める
////    ) {
////        // ショップのヘッダーコンポーネント
////        ShopHeaderComponent()
////
////        // 検索用のテキストフィールド
////        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })
////
////        // 場所の入力フォーム
////        TextField(
////            value = location,
////            onValueChange = { location = it },
////            label = { Text("場所") },
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(16.dp)
////        )
////
////        // 日時の入力フォーム
////        TextField(
////            value = selectedDate,
////            onValueChange = { selectedDate = it },
////            label = { Text("日時") },
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(16.dp),
////            readOnly = true,
////            enabled = true // 日時選択ボタンを有効にする
////        )
////
////        // プランの選択フォーム
////        // プランの選択フォーム
////        Column(
////            modifier = Modifier.fillMaxWidth().padding(16.dp)
////        ) {
////            Text("料金プランの選択", style = MaterialTheme.typography.h6)
////
////            // 6時間貸出パック
////            Row(
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                RadioButton(
////                    selected = selectedPlan == "6時間貸出パック",
////                    onClick = { selectedPlan = "6時間貸出パック" }
////                )
////                Column(modifier = Modifier.padding(start = 8.dp)) {
////                    Text("6時間貸出パック", modifier = Modifier.align(Alignment.Start))
////                    Image(
////                        painter = painterResource(id = R.drawable.coke), // 6時間貸出パックの画像
////                        contentDescription = "6時間貸出パック",
////                        modifier = Modifier.size(40.dp) // アイコンサイズ
////                    )
////                    Text(
////                        text = "お手軽に日常使い、ちょっとしたお出かけにぴったり！",
////                        style = MaterialTheme.typography.body2
////                    )
////                }
////            }
//
//            // 1日貸出パック
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                RadioButton(
//                    selected = selectedPlan == "1日貸出パック",
//                    onClick = { selectedPlan = "1日貸出パック" }
//                )
//                Column(modifier = Modifier.padding(start = 8.dp)) {
//                    Text("1日貸出パック", modifier = Modifier.align(Alignment.Start))
//                    Image(
//                        painter = painterResource(id = R.drawable.apple_image), // 1日貸出パックの画像
//                        contentDescription = "1日貸出パック",
//                        modifier = Modifier.size(40.dp) // アイコンサイズ
//                    )
//                    Text(
//                        text = "1日の冒険をバッテリーと共に！お出かけ全力サポート",
//                        style = MaterialTheme.typography.body2
//                    )
//                }
//            }
//
//            // 5日間貸出パック
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                RadioButton(
//                    selected = selectedPlan == "5日間貸出パック",
//                    onClick = { selectedPlan = "5日間貸出パック" }
//                )
//                Column(modifier = Modifier.padding(start = 8.dp)) {
//                    Text("5日間貸出パック", modifier = Modifier.align(Alignment.Start))
//                    Image(
//                        painter = painterResource(id = R.drawable.banana_image), // 5日間貸出パックの画像
//                        contentDescription = "5日間貸出パック",
//                        modifier = Modifier.size(40.dp) // アイコンサイズ
//                    )
//                    Text(
//                        text = "旅行や出張に最適！長期間使える充実プラン",
//                        style = MaterialTheme.typography.body2
//                    )
//                }
//            }
//        }
//
////        Column(
////            modifier = Modifier.fillMaxWidth().padding(16.dp)
////        ) {
////            Text("料金プランの選択", style = MaterialTheme.typography.h6)
////            Row {
////                RadioButton(
////                    selected = selectedPlan == "Plan A",
////                    onClick = { selectedPlan = "Plan A" }
////                )
////                Text("プランA", modifier = Modifier.align(Alignment.CenterVertically))
////            }
////            Row {
////                RadioButton(
////                    selected = selectedPlan == "Plan B",
////                    onClick = { selectedPlan = "Plan B" }
////                )
////                Text("プランB", modifier = Modifier.align(Alignment.CenterVertically))
////            }
////            Row {
////                RadioButton(
////                    selected = selectedPlan == "Plan C",
////                    onClick = { selectedPlan = "Plan C" }
////                )
////                Text("プランC", modifier = Modifier.align(Alignment.CenterVertically))
////            }
//        }
//
//
//
//        // 商品リストの表示 (LazyColumn)
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth() // 横幅いっぱいに
//                    .padding(start = 16.dp) // 左側にパディング
//                    .fillMaxHeight(0.9f) // 高さを画面の90%に設定
//            ) {
//                // カルーセル画像の表示 (仮の画像)
////                item {
////                    Image(
////                        painter = painterResource(id = R.drawable.carousel_origami), // 画像リソース
////                        contentDescription = null, // コンテンツ説明はなし
////                        modifier = Modifier
////                            .fillMaxWidth() // 横幅いっぱいに
////                            .padding(vertical = 8.dp, horizontal = 16.dp), // 余白の設定
////                        contentScale = ContentScale.FillBounds // 画像をボックスに合わせて拡大縮小
////                    )
////                }
//
//                // 各カテゴリごとに商品を表示
////                val categories = products.map { it.category }.distinct()
////
////                categories.forEach { category ->
////                    item {
////                        // カテゴリーに属する商品を表示
////                        val categoryProducts = products.filter { it.category == category }
////
////                        CategoryComponent(
////                            category = category, // カテゴリー名
////                            products = categoryProducts, // 商品リスト
////                            addItem = {}, // アイテム追加時のアクション（現在は未実装）
////                            navigateToDetail = { product ->
////                                // 詳細画面にナビゲートする処理（仮）
////                            }
////                        )
////                    }
////                }
//            }
//
//            // エラーメッセージの表示（仮のエラーメッセージ）
//            val error = "エラーが発生しました"
//            if (error.isNotBlank()) {
////                Text(
////                    text = error, // エラーメッセージ
////                    color = MaterialTheme.colors.error, // エラーカラー
////                    textAlign = TextAlign.Center, // 中央揃え
////                    modifier = Modifier
////                        .fillMaxWidth() // 横幅いっぱいに
////                        .padding(horizontal = 20.dp) // 余白
////                        .align(Alignment.Center) // 中央に配置
////                )
//            }
//
//            // ローディングインジケーターの表示（仮）
//            val isLoading = false
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .align(Alignment.Center) // 中央に配置
//                        .padding(32.dp)
//                ) // 余白
//            }
//
//            // 予約するボタンを画面下部に1つ表示
//            Button(
//                onClick = {
//                    // 予約画面に遷移
//                    // navigateToReservationScreen() のように遷移処理を追加
//                },
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = Color(0xFFF2F3F2), // 背景色を設定
//                    contentColor = Color(0xFF53B175)
//                ),
//                modifier = Modifier
//                    .fillMaxWidth() // ボタンが画面いっぱいに広がる
//                    .padding(horizontal = 16.dp) // 左右のパディング
//                    .align(Alignment.BottomCenter) // 画面下部に配置
//            ) {
//                Text(
//                    text = "予約する",
//                    color = MaterialTheme.colors.primary, // ボタン内の文字色（任意）
//                    style = MaterialTheme.typography.button // 文字スタイル（任意）
//                )
//            }
//        }
//
//
//
//}
//
//@Composable
//fun CategoryComponent(
//    category: String,
//    products: List<Product>, // 型をProductに変更
//    addItem: (id: String) -> Unit,
//    navigateToDetail: (Product) -> Unit
//) {
//    // カテゴリー内の商品を表示する処理
//    Column {
//        Text(
//            text = category,
//            style = MaterialTheme.typography.h6,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
//        products.forEach { product ->
//            Text(
//                text = product.name,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clickable {
//                        navigateToDetail(product) // 詳細画面に遷移する
//                    }
//            )
//        }
//    }
//}
//
//data class Product(
//    val name: String,
//    val category: String,
//    val description: String,
//    val imageResource: Int // 画像リソースID
//)


//package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.shop
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import coil.annotation.ExperimentalCoilApi
//import com.pi12a082.hf21.R
//import com.pi12a082.hf21.presentation.components.CategoryComponent
//import com.pi12a082.hf21.presentation.components.SearchTextField
//import com.pi12a082.hf21.presentation.components.ShopHeaderComponent
//
//@ExperimentalMaterialApi
//@ExperimentalCoilApi
//@Composable
//fun ShopScreen(
//    navController: NavHostController, // ナビゲーションコントローラー
//    shopViewModel: ShopViewModel = hiltViewModel(), // ShopViewModelをHiltでインジェクト
//) {
//    // 仮のデータを作成
//    val products = listOf(
//        Product("Apple", "Fruit", "Fresh apple", R.drawable.apple_image),
//        Product("Banana", "Fruit", "Ripe banana", R.drawable.banana_image),
//        Product("Laptop", "Electronics", "Powerful laptop", R.drawable.laptop_image),
//        Product("Headphones", "Electronics", "Noise-canceling headphones", R.drawable.headphones_image)
//    )
//
//    // 検索キーワードの状態
//    var searchItem by remember { mutableStateOf("") }
//
//    // UIの構築
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally, // 中央揃え
//        modifier = Modifier.fillMaxSize() // 画面全体を占める
//    ) {
//        // ショップのヘッダーコンポーネント
//        ShopHeaderComponent()
//
//        // 検索用のテキストフィールド
//        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })
//
//        // 商品リストの表示 (LazyColumn)
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth() // 横幅いっぱいに
//                    .padding(start = 16.dp) // 左側にパディング
//                    .fillMaxHeight(0.9f) // 高さを画面の90%に設定
//            ) {
//                // カルーセル画像の表示 (仮の画像)
//                item {
//                    Image(
//                        painter = painterResource(id = R.drawable.carousel_1), // 画像リソース
//                        contentDescription = null, // コンテンツ説明はなし
//                        modifier = Modifier
//                            .fillMaxWidth() // 横幅いっぱいに
//                            .padding(vertical = 8.dp, horizontal = 16.dp), // 余白の設定
//                        contentScale = ContentScale.FillBounds // 画像をボックスに合わせて拡大縮小
//                    )
//                }
//
//                // Product クラスに id が定義されていることを確認
//                data class Product(val id: String, val name: String, val category: String)
//
//                // NetworkProduct クラスに id が定義されていることを確認
//                data class NetworkProduct(val id: String, val name: String, val category: String)
//
//                // Product を NetworkProduct に変換する拡張関数
//                fun Product.toNetworkProduct(): NetworkProduct {
//                    return NetworkProduct(id = this.id, name = this.name, category = this.category)
//                }
//
//// products が Product 型のリストであることを確認
////                val products: List<Product> = emptyList()
//
//// 各カテゴリごとに商品を表示
//                val categories = products.map { it.category }.distinct()
//
//                categories.forEach { category ->
//                    item {
//                        // カテゴリーに属する商品を NetworkProduct 型に変換して表示
//                        val categoryProducts = products
//                            .filter { it.category == category }  // カテゴリーに基づいて商品をフィルタリング
//                            .map { it.toNetworkProduct() }  // Product を NetworkProduct に変換
//
//                        CategoryComponent<Any>(
//                            category = category, // カテゴリー名
//                            products = categoryProducts, // NetworkProduct 型のリスト
//                            addItem = {}
//                        ) // アイテム追加時のアクション（現在は未実装）
//                        {
//                            // 詳細画面にナビゲートする処理（仮）
//                        }
//                    }
//                }
//
//
//
//                // Product を NetworkProduct に変換する拡張関数
////                fun Product.toNetworkProduct(): NetworkProduct {
////                    return NetworkProduct(id = this.id, name = this.name, category = this.category, description = this.description, imageUrl = this.imageUrl, inventory = this.inventory, price = this.price )
////                }
////
////                // 各カテゴリごとに商品を表示
////                val categories = products.map { it.category }.distinct()
////                categories.forEach { category ->
////                    item {
////                        // カテゴリーに属する商品を NetworkProduct 型に変換して表示
////                        val categoryProducts = products
////                            .filter { it.category == category }  // カテゴリーに基づいて商品をフィルタリング
////                            .map { it.toNetworkProduct() }  // Product を NetworkProduct に変換
////
////                        CategoryComponent(
////                            category = category, // カテゴリー名
////                            products = categoryProducts, // NetworkProduct 型のリスト
////                            addItem = {}, // アイテム追加時のアクション（現在は未実装）
////                            navigateToDetail = { product ->
////                                // 詳細画面にナビゲートする処理（仮）
////                            }
////                        )
////                    }
////                }
//
////                // 各カテゴリごとに商品を表示
////                val categories = products.map { it.category }.distinct()
////                categories.forEach { category ->
////                    item {
////                        // カテゴリーに属する商品を表示
////                        val categoryProducts = products.filter { it.category == category }
////                        CategoryComponent(
////                            category = category, // カテゴリー名
////                            products = categoryProducts, // 商品リスト
////                            addItem = {}, // アイテム追加時のアクション（現在は未実装）
////                            navigateToDetail = { product ->
////                                // 詳細画面にナビゲートする処理（仮）
////                            }
////                        )
////                    }
////                }
//            }
//
//            // エラーメッセージの表示（仮のエラーメッセージ）
//            val error = "エラーが発生しました"
//            if (error.isNotBlank()) {
//                Text(
//                    text = error, // エラーメッセージ
//                    color = MaterialTheme.colors.error, // エラーカラー
//                    textAlign = TextAlign.Center, // 中央揃え
//                    modifier = Modifier
//                        .fillMaxWidth() // 横幅いっぱいに
//                        .padding(horizontal = 20.dp) // 余白
//                        .align(Alignment.Center) // 中央に配置
//                )
//            }
//
//            // ローディングインジケーターの表示（仮）
//            val isLoading = false
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .align(Alignment.Center) // 中央に配置
//                        .padding(32.dp)
//                ) // 余白
//            }
//        }
//    }
//}
//
//fun <ProductDetail> CategoryComponent(category: String, products: List<Any>, addItem: (id: String) -> Unit, navigateToDetail: (ProductDetail) -> Unit) {
//
//}
//
//// 仮の商品データクラス
//data class Product(
//    val name: String,
//    val category: String,
//    val description: String,
//    val imageResource: Int // 画像リソースID
//) {
//    fun toNetworkProduct() {
//        TODO("Not yet implemented")
//    }
//}


//package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.shop
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import coil.annotation.ExperimentalCoilApi
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import com.pi12a082.hf21.R
//import com.pi12a082.hf21.navigation.Screen
//import com.pi12a082.hf21.presentation.components.CategoryComponent
//import com.pi12a082.hf21.presentation.components.SearchTextField
//import com.pi12a082.hf21.presentation.components.ShopHeaderComponent
//import com.pi12a082.hf21.util.Constants.DETAIL_ARGUMENT_KEY
//
//@ExperimentalMaterialApi
//@ExperimentalCoilApi
//@Composable
//fun ShopScreen(
//    navController: NavHostController, // ナビゲーションコントローラー
//    shopViewModel: ShopViewModel = hiltViewModel(), // ShopViewModelをHiltでインジェクト
//) {
//    // システムバーの色を設定
//    val systemUiController = rememberSystemUiController()
//    systemUiController.setSystemBarsColor(
//        color = Color.Transparent, // 透明なシステムバー
//        darkIcons = true // アイコンをダークモードに設定
//    )
//
//    // UIの状態を取得
//    val state by shopViewModel.uiState.collectAsState()
//
//    // 商品データが取得できている場合のみ表示
//    val products = state.products ?: emptyList()
//
//    // 検索キーワードの状態
//    var searchItem by remember { mutableStateOf("") }
//
//    // UIの構築
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally, // 中央揃え
//        modifier = Modifier.fillMaxSize() // 画面全体を占める
//    ) {
//        // ショップのヘッダーコンポーネント
//        ShopHeaderComponent()
//
//        // 検索用のテキストフィールド
//        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })
//
//        // 商品リストの表示 (LazyColumn)
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth() // 横幅いっぱいに
//                    .padding(start = 16.dp) // 左側にパディング
//                    .fillMaxHeight(0.9f) // 高さを画面の90%に設定
//            ) {
//                // 商品リストが存在する場合
//                if (products.isNotEmpty()) {
//                    // カルーセル画像の表示 (仮の画像)
//                    item {
//                        Image(
//                            painter = painterResource(id = R.drawable.carousel_1), // 画像リソース
//                            contentDescription = null, // コンテンツ説明はなし
//                            modifier = Modifier
//                                .fillMaxWidth() // 横幅いっぱいに
//                                .padding(vertical = 8.dp, horizontal = 16.dp), // 余白の設定
//                            contentScale = ContentScale.FillBounds // 画像をボックスに合わせて拡大縮小
//                        )
//                    }
//
//                    // 各カテゴリごとに商品を表示
//                    val categories = products.map { it.category.name }.distinct()
//                    categories.forEach { category ->
//                        item {
//                            // カテゴリーに属する商品を表示
//                            val categoryProducts = products.filter { it.category.name == category }
//                            CategoryComponent(
//                                category = category, // カテゴリー名
//                                products = categoryProducts, // 商品リスト
//                                addItem = {}, // アイテム追加時のアクション（現在は未実装）
//                                navigateToDetail = { product ->
//                                    // 詳細画面にナビゲートする処理（仮）
////                                    Log.d("ProductListScreen", "Navigate to detail for product: ${product.name}")
//                                }
//                            )
//                        }
//                    }
//                } else {
//                    // 商品がない場合に表示するメッセージ
//                    item {
//                        Text(
//                            text = "商品が見つかりません",
//                            style = MaterialTheme.typography.body1,
//                            color = Color.Gray,
//                            modifier = Modifier.fillMaxWidth().padding(16.dp),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }
//
//            // エラーメッセージの表示（仮のエラーメッセージ）
//            val error = "エラーが発生しました"
//            if (error.isNotBlank()) {
//                Text(
//                    text = error, // エラーメッセージ
//                    color = MaterialTheme.colors.error, // エラーカラー
//                    textAlign = TextAlign.Center, // 中央揃え
//                    modifier = Modifier
//                        .fillMaxWidth() // 横幅いっぱいに
//                        .padding(horizontal = 20.dp) // 余白
//                        .align(Alignment.Center) // 中央に配置
//                )
//            }
//
//            // ローディングインジケーターの表示（仮）
//            val isLoading = false
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .align(Alignment.Center) // 中央に配置
//                        .padding(32.dp)
//                ) // 余白
//            }
//        }
//    }
//}
//
//
//
//@Composable
//fun ProductListScreen() {
//
//}
//
//
////}




//package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.shop
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import coil.annotation.ExperimentalCoilApi
//import com.google.accompanist.systemuicontroller.rememberSystemUiController
//import com.pi12a082.hf21.R
//import com.pi12a082.hf21.navigation.Screen
//import com.pi12a082.hf21.presentation.components.CategoryComponent
//import com.pi12a082.hf21.presentation.components.SearchTextField
//import com.pi12a082.hf21.presentation.components.ShopHeaderComponent
//import com.pi12a082.hf21.util.Constants.DETAIL_ARGUMENT_KEY
//
//@ExperimentalMaterialApi
//@ExperimentalCoilApi
//@Composable
//fun ShopScreen(
//    navController: NavHostController,
//    shopViewModel: ShopViewModel = hiltViewModel(),
//) {
//    val systemUiController = rememberSystemUiController()
//    systemUiController.setSystemBarsColor(
//        color = Color.Transparent,
//        darkIcons = true
//    )
//
//    val state by shopViewModel.uiState.collectAsState()
//    val categories = ArrayList<String>()
//    state.products?.forEach { item ->
//        categories.add(item.category.name)
//    }
//    var searchItem by remember { mutableStateOf("") }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        ShopHeaderComponent()
//        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })
//        Box() {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp)
//                    .fillMaxHeight(0.9f)
//            ) {
//                if (state.products?.isNotEmpty() == true) {
//                    item {
//                        Image(
//                            painter = painterResource(id = R.drawable.carousel_1),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(vertical = 8.dp, horizontal = 16.dp),
//                            contentScale = ContentScale.FillBounds
//                        )
//                    }
//                }
//                categories.toSet().forEach { category ->
//                    val routeDetails = Screen.Detail.route + "/{$DETAIL_ARGUMENT_KEY}"
//                    item {
//                        state.products?.let {
//                            CategoryComponent(
//                                category = category,
//                                products = it,
//                                addItem = {},
//                                navigateToDetail = {
//                                    navController.currentBackStackEntry?.arguments?.putParcelable(
//                                        DETAIL_ARGUMENT_KEY, it)
//                                    navController.navigate(routeDetails) {
//                                        launchSingleTop = true
//                                    }
//                                })
//                        }
//                    }
//                }
//            }
//            if (state.error.isNotBlank()) {
//                Text(
//                    text = state.error,
//                    color = MaterialTheme.colors.error,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                        .align(Alignment.Center)
//                )
//            }
//            if (state.isLoading) {
//                CircularProgressIndicator(modifier = Modifier
//                    .align(Alignment.Center)
//                    .padding(32.dp))
//            }
//        }
//
//    }
//}