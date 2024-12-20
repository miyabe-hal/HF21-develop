package com.pi12a082.hf21.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductDetail(
    val id :String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val description: String,
) : Parcelable
