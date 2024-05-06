package com.arnavigation.app.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnavigation.app.R
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
@SuppressLint("SuspiciousIndentation")
fun ProfileScreen(navController: NavHostController) {
    val db = Firebase.firestore

    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }

    var email by remember { mutableStateOf(preferencesManager.getData("userEmail","")) }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    LaunchedEffect(email) {
        val docRef = db.collection("users").document(email)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                scope.launch {

                    email = document.data?.get("email")?.toString() ?: ""
                    gender = document.data?.get("gender")?.toString() ?: ""
                    dob = document.data?.get("dob")?.toString() ?: ""
                    name = document.data?.get("name")?.toString() ?: ""
                    phone = document.data?.get("phone")?.toString() ?: ""

                }
            }
        }
    }
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
                Text(
                    text = "HI,$name",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .padding(top = 190.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "+91 $phone",
                    fontSize = 15.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .padding(top =250.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "UserName", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
            )
            Row(
            modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp )
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
                )
                Text(
                    text = "$email",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(6.dp)
                )

            }
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "Gender", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gender),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp )
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
                )
                Text(
                    text = "$gender",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "Date Of Birth", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp )
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
                )
                Text(
                    text = "$dob",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.support),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 10.dp )
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
                )
                Text(
                    text = "Support",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(6.dp)
                )


            }


        }
    }
}

