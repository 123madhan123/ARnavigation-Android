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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
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
fun ChangePassword(navController: NavHostController) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
//            modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp )
                        .clickable {
                            navController.navigate("SettingsScreen")
                        }
//                    .padding(8.dp)
                )

                Text(
                    text = "Change Password",
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
                text = "Old Password:", fontSize = 16.sp,
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
                    value = oldPassword,

                    onValueChange = { oldPassword = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    singleLine = true
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "New Password:", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
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
                    value = newPassword,

                    onValueChange = { newPassword = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Confirm Password:", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
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
                    value = confirmPassword,

                    onValueChange = { confirmPassword = it },
                    textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { /* Handle login click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 38.dp, end = 38.dp)
                    .height(50.dp),

                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    // Set text color
                    containerColor = Color(0xFF7D48EA),

                    ),


                ) {
                Text(
                    "Submit",
                    fontSize = 16.sp,
                    letterSpacing = 2.sp,
                )
            }


        }
    }

}