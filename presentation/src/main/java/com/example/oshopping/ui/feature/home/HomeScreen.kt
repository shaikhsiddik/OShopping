package com.example.oshopping.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

            val data = (uiState.value as HomeScreenUiEvents.Success).data

            LazyColumn {

                items(data) { item: Product ->

                    ProductItems(item)

                }

            }

        }

    }

}

@Composable
fun ProductItems(item: Product) {

    Card(modifier = Modifier.padding(8.dp)) {

        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(text = item.title, style = MaterialTheme.typography.titleMedium)

                Text(text = item.description, style = MaterialTheme.typography.bodyMedium)

            }

        }

    }

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){


}