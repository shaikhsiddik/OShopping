package com.example.data.model.response

import com.example.data.model.CategoryDataModel
import com.example.domain.model.CategoryListModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesListResponse(
    val `data`: List<CategoryDataModel>,
    val msg: String
){

    fun toCategoriesList() = CategoryListModel(
        categories = data.map { it.toCategory() },
        msg = msg
    )

}