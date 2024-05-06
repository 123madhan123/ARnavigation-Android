package com.arnavigation.app.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arnavigation.app.R
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
@Composable
@SuppressLint("SuspiciousIndentation")

fun EditProfile(navController: NavHostController) {
    val db = Firebase.firestore

    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }

    var email by remember { mutableStateOf(preferencesManager.getData("userEmail","")) }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Fetch user data from Firestore
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
                .padding(0.dp)
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
                        .padding(top = 10.dp)
                        .clickable {
                            navController.navigate("SettingsScreen")
                        }
//                    .padding(8.dp)
                )

                Text(
                    text = "Edit Profile",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }


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
                    text = "AR NAVIGATION",
                    fontSize = 25.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .padding(top = 190.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Name", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.name),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
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
                        value = name,

                        onValueChange = { name = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        singleLine = true
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "phone", fontSize = 16.sp,
                style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
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
                        value = phone,
                        onValueChange = { phone = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        singleLine = true
                    )
                }

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
                        .size(50.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
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
                        value = email,

                        onValueChange = { email = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        singleLine = true
                    )
                }

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
                        .size(50.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
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
                        value = gender,

                        onValueChange = { gender = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        singleLine = true
                    )
                }
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
                        .size(50.dp)
                        .padding(top = 10.dp)
                        .clickable {
                            // Add functionality for back button click
                        }
//                    .padding(8.dp)
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
                        value = dob,

                        onValueChange = { dob = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        singleLine = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    var submitData = hashMapOf(
                        "email" to email ,
                        "gender" to gender,
                        "dob" to dob,
                        "name" to name,
                        "phone" to phone
                    )
                    db.collection("users").document(email).set(submitData).addOnSuccessListener {
                        Toast.makeText(ctx,"Your Details Updated",Toast.LENGTH_LONG ).show()
                    }.addOnFailureListener { e -> Toast.makeText(ctx,"Failed ${e.toString()}",Toast.LENGTH_LONG ).show()
                    Log.d("error","${e.toString()}")}

                },
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
                    "Update",
                    fontSize = 16.sp,
                    letterSpacing = 2.sp,
                )
            }
            Spacer(modifier = Modifier.height(60.dp))


        }
    }
}
