package com.example.oshopping.navigation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.oshopping.model.UiProductModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): UiProductModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key,UiProductModel::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): UiProductModel {
        val item = Json.decodeFromString<UiProductModel>(value)
        return item.copy(
            image = URLDecoder.decode(item.image, "UTF-8"),
            description = String(Base64.decode(item.description.toByteArray())).replace("/","_"),
            title = String(Base64.decode(item.title.toByteArray())).replace("/","_")
        )
    }

    override fun put(bundle: Bundle, key: String, value: UiProductModel) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: UiProductModel): String {

        val encodeValue = value.copy(
            image = URLEncoder.encode(value.image, "UTF-8"),
            description = String(Base64.encodeToByteArray(value.description.toByteArray())).replace("/","_"),
            title = String(Base64.encodeToByteArray(value.title.toByteArray())).replace("/","_")
        )

        return Json.encodeToString(encodeValue)
    }

}