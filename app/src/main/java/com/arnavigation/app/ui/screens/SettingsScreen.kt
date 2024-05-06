package com.arnavigation.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnavigation.app.ui.viewmodels.PreferencesManager


data class SettingsAction(val name:String, val route:String)
var accountSettings = listOf<SettingsAction>(
    SettingsAction("Edit Profile","EditProfile"),
    SettingsAction("Change Password","ChangePassword"),
    SettingsAction("Profile","profileScreen")
)
var moreDetailsSettings = listOf<SettingsAction>(
    SettingsAction("About us","About"),
    SettingsAction("Privacy Policy","PrivacyPolicy"),
    SettingsAction("Terms and conditions","Termsandcon"),
    SettingsAction("Logout","Login")
)

@Composable
fun SettingsScreen(navController: NavHostController){
    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }
    Column(modifier = Modifier
        .padding(horizontal = 24.dp)
        .fillMaxWidth()) {
        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "", modifier = Modifier.align(
                Alignment.CenterStart))
            Text(text = "Settings", fontSize = 24.sp, fontWeight= FontWeight.Bold, modifier = Modifier.align(
                Alignment.Center))
        }
        Spacer(modifier = Modifier.height(60.dp))
        Text(text = "Account Settings", modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)

        accountSettings.forEach {item ->
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .clickable { navController.navigate(item.route) }
                .fillMaxWidth()
                .padding(vertical = 10.dp)) {
                Text(text = item.name)
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "")
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        Text(text = "More", modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
        moreDetailsSettings.forEach {item ->
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .clickable { navController.navigate(item.route) }
                .fillMaxWidth()
                .padding(vertical = 10.dp)
            ) {
                Text(text = item.name)
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "")
            }

        }
    }
}