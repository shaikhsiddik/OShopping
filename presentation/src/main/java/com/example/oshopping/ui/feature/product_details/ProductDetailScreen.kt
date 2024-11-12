package com.example.oshopping.ui.feature.product_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.oshopping.R
import com.example.oshopping.model.UiProductModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetailScreen(navController: NavController,
                        product: UiProductModel,
                        viewModel: ProductViewModel = koinViewModel()){



    Column(modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        Box(modifier = Modifier.weight(1f)){

            AsyncImage(model = product.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Image(painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
                    .clickable {

                        navController.popBackStack()

                    }
                    .size(48.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .padding(8.dp)
                    .background(Color.LightGray.copy(alpha = 0.4f))
            )

            Image(painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
                    .clickable {
                        // Click operation handle
                    }
                    .size(48.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .padding(8.dp)
                    .background(Color.LightGray.copy(alpha = 0.4f))
            )

        }

        Column(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = product.title,
                    modifier = Modifier.padding(16.dp).weight(1f),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                Text(
                    text = "$ ${product.price}",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

            }

            Row(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Image(painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null)

                Spacer(modifier = Modifier.size(4.dp))

                Text(text = "4.5",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))

                Spacer(modifier = Modifier.size(16.dp))

                Text(text = "(100 Reviews)",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Gray)


            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = "Description",
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = product.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp),
                minLines = 3,
                maxLines = 5,
                color = Color.Gray)

            Spacer(modifier = Modifier.size(16.dp))

            Text(text = "Size",
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))

            Spacer(modifier = Modifier.size(8.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {

                repeat(4){

                    SizeItem(size = it.toString(), isSelected = it == 0, onClick = {})

                }

            }

            Spacer(modifier = Modifier.size(16.dp))

            Row(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)){

                Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {

                    Text(text = "Add to Cart")

                }

                Spacer(modifier = Modifier.size(8.dp))

                IconButton(onClick = { /*TODO*/ },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.LightGray.copy(alpha = 0.4f))
                ) {

                    Image(painter = painterResource(id = R.drawable.ic_bag),contentDescription = null)

                }

            }

        }

    }


}

@Composable
fun SizeItem(size: String, isSelected: Boolean, onClick: () -> Unit){
    Box(modifier = Modifier
        .padding(horizontal = 4.dp)
        .size(48.dp)
        .clip(RoundedCornerShape(8.dp))
        .border(
            width = 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        )
        .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
        .clickable {
            onClick()
        }){

        Text(
            text = size,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = if (isSelected) Color.White else Color.Black
        )

    }
}