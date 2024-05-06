@file:OptIn(ExperimentalMaterial3Api::class)

package com.arnavigation.app.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arnavigation.app.ui.screens.About
import com.arnavigation.app.ui.screens.ChangePassword
import com.arnavigation.app.ui.screens.EditProfile
import com.arnavigation.app.ui.screens.HomeScreen
import com.arnavigation.app.ui.screens.Login
import com.arnavigation.app.ui.screens.PrivacyPolicy
import com.arnavigation.app.ui.screens.ProfileScreen
import com.arnavigation.app.ui.screens.SettingsScreen
import com.arnavigation.app.ui.screens.Signup
import com.arnavigation.app.ui.screens.SplashScreen
import com.arnavigation.app.ui.screens.Termsandcon
import com.arnavigation.app.ui.viewmodels.PreferencesManager

sealed class BottomNavigationScreens(val name: String, val route:String, val icon: ImageVector){
    object Home : BottomNavigationScreens("Home","homeScreen",Icons.Filled.Home)
    object Profile : BottomNavigationScreens("Profile","profileScreen",Icons.Filled.Face)
    object Settings : BottomNavigationScreens("Settings","settingsScreen",Icons.Filled.Settings)

}

@Composable
fun App(navController: NavHostController, modifier: Modifier){
    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }
    var startDest = "Login"
    if(preferencesManager.getData("isLogin","false")=="true"){
        startDest = "homeScreen"
    }
    NavHost(navController = navController, startDestination = startDest){
        composable("Login") {
            Login(navController = navController)
        }
        composable("About") {
            About(navController = navController)
        }
        composable("ChangePassword") {
            ChangePassword(navController = navController)
        }
        composable("EditProfile") {
            EditProfile(navController = navController)
        }
        composable("PrivacyPolicy") {
            PrivacyPolicy(navController = navController)
        }
        composable("Termsandcon") {
            Termsandcon(navController = navController)
        }
        composable("signup"){
            Signup(navController = navController)
        }
        composable("splitScreen"){
            SplashScreen()
        }

        composable(BottomNavigationScreens.Home.route){
            HomeScreen(navController = navController)
        }
        composable(BottomNavigationScreens.Profile.route){
            ProfileScreen(navController = navController)
        }
        composable(BottomNavigationScreens.Settings.route){
            SettingsScreen(navController = navController)
        }
    }
}

@Composable
fun ARNavigationScreen() {
    TODO("Not yet implemented")
}
