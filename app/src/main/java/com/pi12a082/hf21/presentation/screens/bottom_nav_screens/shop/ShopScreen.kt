package com.pi12a082.hf21.presentation.screens.bottom_nav_screens.shop

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

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun ShopScreen(
    navController: NavHostController, // ナビゲーションコントローラー
    shopViewModel: ShopViewModel = hiltViewModel(), // ShopViewModelをHiltでインジェクト
) {
    // 仮のデータを作成
    val products = listOf(
        Product("Apple", "Fruit", "Fresh apple", R.drawable.apple_image),
        Product("Banana", "Fruit", "Ripe banana", R.drawable.banana_image),
        Product("Laptop", "Electronics", "Powerful laptop", R.drawable.laptop_image),
        Product("Headphones", "Electronics", "Noise-canceling headphones", R.drawable.headphones_image)
    )

    // 検索キーワードの状態
    var searchItem by remember { mutableStateOf("") }

    // UIの構築
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // 中央揃え
        modifier = Modifier.fillMaxSize() // 画面全体を占める
    ) {
        // ショップのヘッダーコンポーネント
        ShopHeaderComponent()

        // 検索用のテキストフィールド
        SearchTextField(searchItem = searchItem, changeEvent = { searchItem = it })



        // 商品リストの表示 (LazyColumn)
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth() // 横幅いっぱいに
                    .padding(start = 16.dp) // 左側にパディング
                    .fillMaxHeight(0.9f) // 高さを画面の90%に設定
            ) {
                item {
                // タイトル表示
                Text(
                    text = "Orumobi", // タイトル
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .padding(bottom = 16.dp), // 上下のパディング
                    color = MaterialTheme.colors.primary
                )

                // 画像表示
                Image(
                    painter = painterResource(id = R.drawable.origami_car_top), // 画像リソース
                    contentDescription = "Orumobi image", // コンテンツ説明
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // 横のパディング
                    contentScale = ContentScale.FillWidth // 画像を横幅いっぱいに表示
                )

                // 説明文表示
                Text(
                    text = "私たちのテーマは「オルモビリティで変わる、コンパクトな未来」です。\n\n" +
                            "何を、どうするのか？ 私たちは、折り紙のように折り畳んでコンパクトに収納できる新しいモビリティ「オルモビリティ」を提案します。" +
                            "このオルモビリティは、動力源をレンタルできるシステムを採用し、本体は折り畳んで持ち運び可能にすることで、誰もが手軽に利用できることを目指しています。\n\n" +
                            "なぜ、この企画なのか？ 都市部の駐車場不足や交通渋滞は、深刻な社会問題となっています。" +
                            "オルモビリティは、折り畳めばコンパクトになるため、駐車場の必要性が大幅に減り、都市部のスペースを有効活用できます。\n\n" +
                            "未来の生活はこう変わる 折りたたみ式のオルモビリティが普及すれば、都市部の移動手段は大きく変化します。" +
                            "例えば、どんなに駐車場が混んでいても、駐車場が無かったとしても、目的地まではオルモビリティで行き、着いたら畳むといったシームレスな移動が可能になります。",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )

                // カルーセル画像の表示 (仮の画像)

                    Image(
                        painter = painterResource(id = R.drawable.carousel_origami), // 画像リソース
                        contentDescription = null, // コンテンツ説明はなし
                        modifier = Modifier
                            .fillMaxWidth() // 横幅いっぱいに
                            .padding(vertical = 8.dp, horizontal = 16.dp), // 余白の設定
                        contentScale = ContentScale.FillBounds // 画像をボックスに合わせて拡大縮小
                    )
                }

                // 各カテゴリごとに商品を表示
                val categories = products.map { it.category }.distinct()

//                categories.forEach { category ->
//                    item {
//                        // カテゴリーに属する商品を表示
//                        val categoryProducts = products.filter { it.category == category }
//
//                        CategoryComponent(
//                            category = category, // カテゴリー名
//                            products = categoryProducts, // 商品リスト
//                            addItem = {}, // アイテム追加時のアクション（現在は未実装）
//                            navigateToDetail = { product ->
//                                // 詳細画面にナビゲートする処理（仮）
//                            }
//                        )
//                    }
//                }
            }

            // エラーメッセージの表示（仮のエラーメッセージ）
            val error = "エラーが発生しました"
            if (error.isNotBlank()) {
//                Text(
//                    text = error, // エラーメッセージ
//                    color = MaterialTheme.colors.error, // エラーカラー
//                    textAlign = TextAlign.Center, // 中央揃え
//                    modifier = Modifier
//                        .fillMaxWidth() // 横幅いっぱいに
//                        .padding(horizontal = 20.dp) // 余白
//                        .align(Alignment.Center) // 中央に配置
//                )
            }

            // ローディングインジケーターの表示（仮）
            val isLoading = false
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center) // 中央に配置
                        .padding(32.dp)
                ) // 余白
            }

            // 予約するボタンを画面下部に1つ表示
            Button(
                onClick = {
                    // 予約画面に遷移
                    // navigateToReservationScreen() のように遷移処理を追加
                    // 予約画面に遷移
                    navController.navigate("booking_screen") // BookingScreenに遷移
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF2F3F2), // 背景色を設定
                    contentColor = Color(0xFF53B175)
                ),
                modifier = Modifier
                    .fillMaxWidth() // ボタンが画面いっぱいに広がる
                    .padding(horizontal = 16.dp) // 左右のパディング
                    .padding(top = 550.dp) // ボタンと画面下部の余白
            ) {
                Text(
                    text = "予約する",
                    color = MaterialTheme.colors.primary, // ボタン内の文字色（任意）
                    style = MaterialTheme.typography.button // 文字スタイル（任意）
                )
            }
        }


    }
}

@Composable
fun CategoryComponent(
    category: String,
    products: List<Product>, // 型をProductに変更
    addItem: (id: String) -> Unit,
    navigateToDetail: (Product) -> Unit
) {
    // カテゴリー内の商品を表示する処理
    Column {
        Text(
            text = category,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        products.forEach { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateToDetail(product) // 詳細画面に遷移する
                    }
            )
        }
    }
}

data class Product(
    val name: String,
    val category: String,
    val description: String,
    val imageResource: Int // 画像リソースID
)


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