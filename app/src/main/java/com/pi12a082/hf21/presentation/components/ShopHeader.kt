package com.pi12a082.hf21.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pi12a082.hf21.R

@Composable
fun ShopHeaderComponent() {
    Image(
        painter = painterResource(id = R.drawable.ic_origami_bird),
        contentDescription = null,
        modifier = Modifier
            .width(27.dp)
            .height(31.dp)
    )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.94f)
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = null
        )
        Text(text = " Orumobi, Origami Mobility")
    }
    Spacer(modifier = Modifier.height(5.dp))
}