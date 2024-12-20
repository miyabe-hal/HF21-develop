package com.pi12a082.hf21.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.pi12a082.hf21.R
import com.pi12a082.hf21.domain.model.NetworkProduct
import com.pi12a082.hf21.domain.model.ProductDetail
import com.pi12a082.hf21.ui.theme.itemCategoryTextStyle
import com.pi12a082.hf21.ui.theme.itemNameTextStyle
import com.pi12a082.hf21.ui.theme.itemPriceTextStyle
import com.pi12a082.hf21.ui.theme.seeAllTextStyle


@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun CategoryComponent(
    category: String,
    products: List<NetworkProduct>,
    addItem: (id: String) -> Unit,
    navigateToDetail: (ProductDetail) -> Unit,
) {
    val sortedProducts = products.filter {
        it.category.name == category
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = category, style = itemCategoryTextStyle)
            Text(
                text = "See all",
                style = seeAllTextStyle,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sortedProducts) { product ->
                FoodItemComposable(product,
                    addingToCart = { id -> addItem(id) },
                    viewProduct = { navigateToDetail(it) })
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun FoodItemComposable(
    product: NetworkProduct,
    addingToCart: (id: String) -> Unit,
    viewProduct: (ProductDetail) -> Unit,
) {
    val productDetail = ProductDetail(id = product.id,
        name = product.name,
        description = product.description,
        imageUrl = product.imageUrl, price = product.price)
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        border = BorderStroke(1.dp, Color(0xffe2e2e2)),
        modifier = Modifier
            .height(170.dp)
            .width(134.dp),
        onClick = { viewProduct(productDetail) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = rememberImagePainter(product.imageUrl), contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.9f)
            )
            Text(
                text = product.name, style = itemNameTextStyle,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 2.dp)
            )
            Text(
                text = "Kshs, price", style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text(text = product.price.toString(), style = itemPriceTextStyle)
                Button(
                    modifier = Modifier
                        .height(36.dp)
                        .width(36.dp),
                    onClick = {
                        addingToCart(product.id)
                    },
                    shape = RoundedCornerShape(33),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}