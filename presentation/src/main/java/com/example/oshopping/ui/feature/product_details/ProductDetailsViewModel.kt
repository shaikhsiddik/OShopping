package com.example.oshopping.ui.feature.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.request.AddCartRequestModel
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.AddToCartUseCase
import com.example.oshopping.model.UiProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val useCase: AddToCartUseCase) : ViewModel() {

    private val _productDetailEventState =
        MutableStateFlow<ProductDetailEvent>(ProductDetailEvent.Nothing)

    val eventState = _productDetailEventState.asStateFlow()

    fun addProductToCart(product: UiProductModel) {

        viewModelScope.launch {

            _productDetailEventState.value = ProductDetailEvent.Loading

            val result = useCase.execute(
                AddCartRequestModel(
                    productId = product.id,
                    quantity = 1,
                    userId = 1,
                    productName = product.title,
                    price = product.price
                )
            )

            when (result) {

                is ResultWrapper.Success -> {

                    _productDetailEventState.value =
                        ProductDetailEvent.Success("Product added to cart successfully")
                }

                is ResultWrapper.Failure -> {

                    _productDetailEventState.value =
                        ProductDetailEvent.Error(result.error.message ?: "Something went wrong")
                }

            }

        }

    }
}

sealed class ProductDetailEvent{

    data object Loading: ProductDetailEvent()

    data object Nothing: ProductDetailEvent()

    data class Success(val message: String): ProductDetailEvent()

    data class Error(val message: String): ProductDetailEvent()

}