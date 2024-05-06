package com.arnavigation.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arnavigation.app.R
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.arnavigation.app.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch


@Composable
fun Login(navController: NavHostController) {
    val viewModel: SignInViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.signInState.collectAsState(initial = null)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Username:",
            fontSize = 16.sp,
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 10.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .height(50.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(30.dp))
                .clipToBounds()
        ) {
            BasicTextField(
                value = username,

                onValueChange = { username = it },
                textStyle = TextStyle(color = Color.Black,fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Password:",
            fontSize = 16.sp,
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 10.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .height(50.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(30.dp))
                .clipToBounds()
        ) {
            BasicTextField(
                value = password,

                onValueChange = { password = it },
                textStyle = TextStyle(color = Color.Black,fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Don’t have an account? ",
                fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 80.dp),
            )
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                style = TextStyle(color = Color(0xFF7D48EA), fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .clickable(onClick = { navController.navigate("SignUp") })
                    .padding(vertical = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                viewModel.loginUser(username,password)
                preferencesManager.saveData("userEmail",username)
                preferencesManager.saveData("isLogin","true")
                navController.navigate("homeScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 38.dp, end = 38.dp)
                .height(50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF7D48EA), // Set button background color
            )
        ) {
            Text(
                "Login",
                fontSize = 16.sp,
                letterSpacing = 2.sp,
            )
        }
    }
//    LaunchedEffect(key1 = state.value?.isSuccess) {
//        scope.launch {
//            if (state.value?.isSuccess?.isNotEmpty() == true) {
//                val success = state.value?.isSuccess
//            }
//        }
//    }
}

