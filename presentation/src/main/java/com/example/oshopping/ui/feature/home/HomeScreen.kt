package com.example.oshopping.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = koinViewModel()) {

    val uiState = viewModel.uiState.collectAsState()

    when(uiState.value){

        is HomeScreenUiEvents.Error -> Text(text = (uiState.value as HomeScreenUiEvents.Error).message ?: "Error")

        HomeScreenUiEvents.Loading -> CircularProgressIndicator()

        is HomeScreenUiEvents.Success -> {

            val data = (uiState.value as HomeScreenUiEvents.Success)

            LazyColumn {

                item {



                }

            }

        }

    }

}

@Composable
fun HomeProductRow(products: List<Product>, title: String) {
    Column {
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth()){
            Text(text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart))
            Text(text = "See all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterEnd))
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow{
            items(products) { product ->
                ProductItems(item = product)
            }
        }
    }

}

@Composable
fun ProductItems(item: Product) {

    Card(modifier = Modifier.padding(horizontal = 8.dp)
        .size(width = 126.dp, height = 144.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(contentColor = Color.LightGray.copy(alpha = 0.3f))
    ) {

       Column(modifier = Modifier.fillMaxSize()) {



       }

    }

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){


}