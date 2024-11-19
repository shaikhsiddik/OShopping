package com.example.oshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.domain.model.Product
import com.example.oshopping.model.UiProductModel
import com.example.oshopping.navigation.CartScreen
import com.example.oshopping.navigation.HomeScreen
import com.example.oshopping.navigation.ProductDetailScreen
import com.example.oshopping.navigation.ProfileScreen
import com.example.oshopping.navigation.productNavType
import com.example.oshopping.ui.feature.home.HomeScreen
import com.example.oshopping.ui.feature.product_details.ProductDetailScreen
import com.example.oshopping.ui.theme.OShoppingTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OShoppingTheme {

                val shouldShowBottomBar = remember {

                    mutableStateOf(true)

                }

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                        AnimatedVisibility(visible = shouldShowBottomBar.value, enter = fadeIn()) {

                            BottomNavigationBar(navController)

                        }

                    }) {

                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(it)) {

                        NavHost(navController = navController, startDestination = HomeScreen){

                            composable<HomeScreen>{

                                HomeScreen(navController)

                                shouldShowBottomBar.value = true

                            }

                            composable<CartScreen> {

                                Box(modifier = Modifier.fillMaxSize()){
                                    Text(text = "Cart")
                                }

                                shouldShowBottomBar.value = true

                            }

                            composable<ProfileScreen> {

                                Box(modifier = Modifier.fillMaxSize()){
                                    Text(text = "Cart")
                                }

                                shouldShowBottomBar.value = true

                            }

                            composable<ProductDetailScreen>(
                                typeMap = mapOf(typeOf<UiProductModel>() to productNavType)
                            ){

                                shouldShowBottomBar.value = false

                                val productRoute = it.toRoute<ProductDetailScreen>()

                                ProductDetailScreen(navController, productRoute.product)

                            }

                        }

                    }

                }

            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController){

    NavigationBar {

        val currentRoute = navController.currentBackStackEntry?.destination?.route

        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )

        items.forEach { item ->

            val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route)
                },
                label = {

                    Text(text = item.label)

                },
                icon = {

                    Image(painter = painterResource(item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray))

                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground
                )
            )

        }

    }

}


sealed class BottomNavItems(val route: Any, val icon: Int, val label: String){

    object Home : BottomNavItems(HomeScreen, R.drawable.home, "Home")

    object Cart : BottomNavItems(CartScreen, R.drawable.cart, "Cart")

    object Profile : BottomNavItems(ProfileScreen, R.drawable.profile, "Profile")

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OShoppingTheme {

    }
}