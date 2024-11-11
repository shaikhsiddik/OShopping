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

        getProduct()

    }

    fun getProduct(){

        viewModelScope.launch {

            getProductUseCase.getProductExecute().let { result ->

                when (result) {

                    is ResultWrapper.Success -> {

                        val data = result.value

                        _uiState.value = HomeScreenUiEvents.Success(data)

                    }

                    is ResultWrapper.Failure -> {

                        val data = result.error

                        _uiState.value = HomeScreenUiEvents.Error(data.message ?: "Error occurred")

                    }

                    else -> {

                        _uiState.value = HomeScreenUiEvents.Error("An unknown error occurred")

                    }

                }

            }


        }


    }

}

sealed class HomeScreenUiEvents{

    data object Loading: HomeScreenUiEvents()

    data class Success(val data: List<Product>): HomeScreenUiEvents()

    data class Error(val message: String): HomeScreenUiEvents()


}