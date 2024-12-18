package com.example.oshopping.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.domain.model.Product
import com.example.oshopping.R
import com.example.oshopping.model.UiProductModel
import com.example.oshopping.navigation.ProductDetailScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = koinViewModel()) {

    val uiState = viewModel.uiState.collectAsState()

    val loading = remember {

        mutableStateOf(false)

    }

    val error = remember {

        mutableStateOf<String?>("")

    }

    val feature = remember {

        mutableStateOf(emptyList<Product>())

    }

    val popular = remember {

        mutableStateOf(emptyList<Product>())

    }

    val categories = remember {

        mutableStateOf(emptyList<String>())

    }



    Scaffold {

        Surface(modifier = Modifier.fillMaxSize()
            .padding(it)) {

            when(uiState.value){

                is HomeScreenUiEvents.Error -> {

                    Text(text = (uiState.value as HomeScreenUiEvents.Error).message ?: "Error")

                    loading.value = false

                    error.value = (uiState.value as HomeScreenUiEvents.Error).message ?: "Error"

                }

                HomeScreenUiEvents.Loading -> {

                    loading.value = true

                    error.value = null

                }

                is HomeScreenUiEvents.Success -> {

                    val data = (uiState.value as HomeScreenUiEvents.Success)

                    feature.value = data.featureProducts

                    popular.value = data.popularProducts

                    categories.value = data.category

                    loading.value = false

                    error.value = null

                }

            }

            HomeContent(
                feature.value,
                popular.value,
                categories.value,
                loading.value,
                error.value,
                onClick = {
                    navController.navigate(ProductDetailScreen(UiProductModel.fromProduct(it)))
                })

        }

    }

}

@Composable
fun HomeContent(
    feature: List<Product>,
    popular: List<Product>,
    categories: List<String>,
    isLoading: Boolean = false,
    errorMsg: String? = null,
    onClick: (Product) -> Unit
){

    LazyColumn {

        item {

            ProfileHeader()

            Spacer(modifier = Modifier.size(16.dp))

            SearchBar(values = "", onTextChange = {})

            Spacer(modifier = Modifier.size(16.dp))


        }

        item {

            errorMsg?.let {

                Text(text = it, style = MaterialTheme.typography.bodyMedium)

            }

            if (isLoading){
                Box(modifier = Modifier.fillMaxWidth()
                    .height(300.dp),
                    contentAlignment = Alignment.Center,){

                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                    Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)

                }
            }

            if (categories.isNotEmpty()){

                Spacer(modifier = Modifier.size(16.dp))

                LazyRow {

                  items(categories,
                      key = {it})
                  { categories ->

                      val isVisible = remember {

                          mutableStateOf(true)

                      }

                      LaunchedEffect(true) {

                          isVisible.value = true

                      }

                      AnimatedVisibility(isVisible.value, enter = fadeIn() + expandVertically()) {

                          Text(
                              text = categories.replaceFirstChar { it.uppercase() },
                              fontWeight = FontWeight.SemiBold,
                              style = MaterialTheme.typography.bodyMedium,
                              modifier = Modifier.padding(horizontal = 8.dp)
                                  .clip(RoundedCornerShape(8.dp))
                                  .background(MaterialTheme.colorScheme.primary)
                                  .padding(8.dp),
                              color = MaterialTheme.colorScheme.onPrimary,
                          )

                      }

                  }

              }

            }

            if (feature.isNotEmpty()){

                HomeProductRow(products = feature, title = "Feature", onClick = onClick)

                Spacer(modifier = Modifier.size(16.dp))

            }

            if (popular.isNotEmpty()){

                HomeProductRow(products = popular, title = "Popular", onClick = onClick)

            }

        }

    }

}

@Composable
fun HomeProductRow(products: List<Product>, title: String, onClick: (Product) -> Unit) {
    Column {
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth()){
            Text(text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "See all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterEnd))
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow{
            items(products,
                key = {it.id}) { product ->

                val isVisible = remember {

                    mutableStateOf(true)

                }

                LaunchedEffect(true) {

                    isVisible.value = true

                }

                AnimatedVisibility(isVisible.value, enter = fadeIn() + expandVertically()) {

                    ProductItems(
                        item = product,
                        onClick = onClick
                    )

                }

            }
        }
    }

}

@Composable
fun ProductItems(item: Product, onClick: (Product) -> Unit) {

    Card(modifier = Modifier.padding(horizontal = 8.dp)
        .size(width = 126.dp, height = 144.dp)
        .clickable { onClick(item) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(contentColor = Color.LightGray.copy(alpha = 0.3f))
    ) {

       Column(modifier = Modifier.fillMaxSize()) {

            AsyncImage(model = item.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .height(96.dp))

           Spacer(modifier = Modifier.size(8.dp))

           Text(text = item.title,
               style = MaterialTheme.typography.bodyMedium,
               modifier = Modifier.padding(horizontal = 8.dp),
               fontWeight = FontWeight.SemiBold,
               color = Color.Black,
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
           )

           Text(text = "$${item.price}",
               style = MaterialTheme.typography.bodyMedium,
               modifier = Modifier.padding(horizontal = 8.dp),
               color = MaterialTheme.colorScheme.primary,
               fontWeight = FontWeight.SemiBold)

       }

    }

}

@Composable
fun ProfileHeader(){

    Box(
        modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)) {

            Image(painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.size(48.dp))

            Spacer(modifier = Modifier.width(8.dp))

            Column {

                Text(
                    text = "Hello, Good Morning",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "Shaikh Siddik",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

            }

        }

        Image(
            painter = painterResource(id = R.drawable.circle_notifications),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f)),
            contentScale = ContentScale.Inside
        )

    }

}

@Composable
fun SearchBar(values:String, onTextChange: (String) -> Unit){

    TextField(
        value = values,
        onValueChange = onTextChange,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            cursorColor = Color.Black
        ),
        placeholder = {
            Text(text = "Search for products, brands and more", style = MaterialTheme.typography.bodyMedium)
        }
    )

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){


}