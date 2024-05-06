package com.arnavigation.app.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.arnavigation.app.ui.navigation.BottomNavigationScreens

@Composable
fun NavBottomBar(navController: NavController, items:List<BottomNavigationScreens>){
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Blue,
        indicatorColor = Color.White,
        selectedTextColor = Color.Blue
    )
    NavigationBar(containerColor = Color.White){
        val currentRoute = currentRoute(navController)
        items.forEach {screen ->
            NavigationBarItem(selected = currentRoute==screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                },
                colors=colors,
                label = { Text(text = screen.name) },
                icon = { Icon(screen.icon, contentDescription = "")})
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}