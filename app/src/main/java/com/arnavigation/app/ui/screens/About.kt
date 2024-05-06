package com.arnavigation.app.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnavigation.app.R


@Composable
fun About(navController: NavHostController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            navController.navigate("SettingsScreen")
                        }
                )

                Text(
                    text = "About Us",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .height(190.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Scan-It is an augmented reality navigation app that provides users with a unique and immersive way to explore their surroundings. With this app, you can access live place, food, and product details in 3D. The app uses visual positioning systems (VPS) to analyze your surroundings and determine your location. This technology allows the app to provide you with on-screen directions overlaid on top of real environments seen through the camera of your device. While I couldn’t find any specific details about the app called Scan-It, I hope this information helps you understand what augmented reality navigation apps are capable of doing. This technology allows the app to provide you with on-screen directions overlaid on top of real environments seen through the camera of your device.",fontSize=16.sp,
                style = TextStyle(color = Color.Black,lineHeight = 28.sp),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(start = 10.dp),


                )


        }
    }
}
