package com.example.oshopping.model

import android.os.Parcelable
import com.example.domain.model.Product
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UiProductModel(
    val categoryId: Int,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
) : Parcelable{

    companion object{

        fun fromProduct(product: Product) = UiProductModel(
            categoryId = product.categoryId,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            title = product.title
        )

    }

}
