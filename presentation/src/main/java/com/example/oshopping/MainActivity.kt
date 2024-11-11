package com.example.oshopping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.oshopping.ui.feature.home.HomeScreen
import com.example.oshopping.ui.theme.OShoppingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OShoppingTheme {

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {

                        BottomNavigationBar(navController)

                    }) {

                    Surface(modifier = Modifier
                        .fillMaxSize()
                        .padding(it)) {

                        NavHost(navController = navController, startDestination = "home"){

                            composable("home") {

                                HomeScreen(navController)

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

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                label = {

                    Text(text = item.label)

                },
                icon = {

                    Image(painter = painterResource(item.icon), contentDescription = null)

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


sealed class BottomNavItems(val route: String, val icon: Int, val label: String){

    object Home : BottomNavItems("home", R.drawable.home, "Home")

    object Cart : BottomNavItems("cart", R.drawable.cart, "Cart")

    object Profile : BottomNavItems("profile", R.drawable.profile, "Profile")

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OShoppingTheme {

    }
}