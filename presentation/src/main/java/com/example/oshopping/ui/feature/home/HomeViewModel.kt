package com.example.oshopping.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.network.ResultWrapper
import com.example.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val getProductUseCase: GetProductUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiEvents>(HomeScreenUiEvents.Loading)

    val uiState: StateFlow<HomeScreenUiEvents> = _uiState.asStateFlow()

    init {

        getAllProduct()

    }

    private fun getAllProduct(){
        viewModelScope.launch {
            _uiState.value = HomeScreenUiEvents.Loading
            val featureProducts = getProduct("electronics")
            val popularProducts = getProduct("jewelery")
            if (featureProducts.isEmpty() || popularProducts.isEmpty()){
                _uiState.value = HomeScreenUiEvents.Error("Something went wrong")
                return@launch
            }
            _uiState.value = HomeScreenUiEvents.Success(featureProducts, popularProducts)
        }
    }

    private suspend fun getProduct(category: String?): List<Product>{

            getProductUseCase.getProductExecute(category).let { result ->

                when (result) {

                    is ResultWrapper.Success -> {

                        return result.value

                    }

                    is ResultWrapper.Failure -> {

                       return emptyList()

                    }

                }

        }


    }

}

sealed class HomeScreenUiEvents{

    data object Loading: HomeScreenUiEvents()

    data class Success(val featureProducts: List<Product>, val popularProducts: List<Product>): HomeScreenUiEvents()

    data class Error(val message: String): HomeScreenUiEvents()


}