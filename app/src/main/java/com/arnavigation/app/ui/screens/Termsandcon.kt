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
fun Termsandcon(navController: NavHostController) {
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
                .padding(horizontal = 16.dp)
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
                    text = "Privacy and Policy",
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
                text = "Our Terms and Conditions govern the use of our services and outline the rights and responsibilities of both users and the service provider. By accessing or using our services, users agree to abide by these terms. The terms cover aspects such as user eligibility, account creation, content usage, intellectual property rights, user conduct, and prohibited activities. They also address service modifications, termination, liability limitations, dispute resolution, and governing law. Users are encouraged to read the terms carefully and understand their implications before using our services. Continued use of the services implies acceptance of these terms.",
                fontSize = 16.sp,
                style = TextStyle(color = Color.Black, lineHeight = 28.sp),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            )
        }
    }
}
