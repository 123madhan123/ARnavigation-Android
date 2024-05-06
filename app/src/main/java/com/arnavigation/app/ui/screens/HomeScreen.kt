/*
package com.arnavigation.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
import com.arnavigation.app.ui.viewmodels.LocationViewModel
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun HomeScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(emptyList<LocationSuggestion>()) }
    val locationViewModel: LocationViewModel = viewModel()
    val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }
    var markerPosition by remember { mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())) }
    var destinationMarkerPosition by remember {
        mutableStateOf(LatLng(0.0,0.0))
    }
    var directionPath by remember {
        mutableStateOf(listOf<PolyfillPoint>())
    }
    var suggestionSelected by remember { mutableStateOf(false) }
    var directions by remember { mutableStateOf(emptyList<String>()) }
    val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
    var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = searchText,
                onValueChange = { newSearchText ->
                    searchText = newSearchText

                    if (newSearchText.isNotEmpty()) {
                        // Launch coroutine to perform search
                        searchLocation(newSearchText) { result ->
                            suggestions = result
                        }
                    } else {
                        suggestions = emptyList()
                    }
                },
                label = { Text(text = "Search") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = {
                            searchText = ""
                            suggestions = emptyList() // Clear suggestions when clearing search text
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )


            if (suggestions.isNotEmpty() && !suggestionSelected) { // Show suggestions only if none is selected
                Column(modifier = Modifier.fillMaxWidth()) {
                    for (suggestion in suggestions) {
                        Text(
                            text = suggestion.displayName,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .clickable {
                                    // Handle suggestion selection
                                    destinationMarkerPosition =
                                        LatLng(suggestion.latitude, suggestion.longitude)
                                    destinationLocationViewModel.setLocation(
                                        suggestion.latitude,
                                        suggestion.longitude
                                    )
                                    suggestionSelected = true // Set suggestion selected
                                    suggestions = emptyList() // Clear suggestions after selection
                                    preferencesManager.saveData("destinationLocationLatitude", destinationMarkerPosition.latitude.toString())
                                    preferencesManager.saveData("destinationLocationLongitude", destinationMarkerPosition.longitude.toString())
                                    collectDirections(
                                        "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
                                        "${destinationMarkerPosition.latitude.toString()},${destinationMarkerPosition.longitude.toString()}"
                                    ) { result ->
                                        directionPath = result
                                        var pointArray = arrayListOf<LatLng>()
//                                          Log.d("---------cool",result.lastIndex.toString())
                                        for (i in 0 until result.lastIndex) {
                                            pointArray.add(
                                                LatLng(
                                                    result[i].startLocation.latitude,
                                                    result[i].startLocation.longitude
                                                )
                                            )
                                        }
                                        pointsList = pointArray
//                                        Log.d("---------cool",pointsList.lastIndex.toString())
                                    }


                                },
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
        }


        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
        }

        GoogleMap(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
            cameraPositionState = cameraPositionState){
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Me",
                snippet = "Marker in Big Ben"
            )

            if(destinationMarkerPosition.latitude!=0.0 && destinationMarkerPosition.longitude!=0.0){
                Marker(
                    state = MarkerState(position = destinationMarkerPosition),
                    title = "Destination",
                    snippet = "Destination Position"
                )

                Polyline(points = pointsList, color = Color.Black)
//                Log.d("---------cool",pointsList.lastIndex.toString())
            }

        }

        // Display directions
        Column(modifier = Modifier.fillMaxWidth()) {
            for (step in directions) {
                Text(
                    text = step,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
    if(destinationMarkerPosition.latitude!=0.0 && destinationMarkerPosition.longitude!=0.0){
        Box(modifier = Modifier.padding(top = 60.dp, start = 280.dp)) {
            Button(onClick = { navController.navigate("splitScreen") }) {
                Text(text = "Start")
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
            }
        }
    }
}

data class LocationSuggestion(
    val displayName: String,
    val latitude: Double,
    val longitude: Double
)

fun searchLocation(query: String, onComplete: (List<LocationSuggestion>) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val urlString = "https://nominatim.openstreetmap.org/search?q=${query}&format=json"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val inputStream = connection.inputStream
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val results = mutableListOf<LocationSuggestion>()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val displayName = jsonObj.getString("display_name")
                val latitude = jsonObj.getDouble("lat")
                val longitude = jsonObj.getDouble("lon")
                results.add(LocationSuggestion(displayName, latitude, longitude))
            }

            // Update suggestions list on the main thread
            withContext(Dispatchers.Main) {
                onComplete(results)
            }
        } catch (e: IOException) {
            // Handle error
            e.printStackTrace()
        }
    }
}
data class LocationCoord(val latitude:Double, val longitude:Double)
data class PolyfillPoint(val startLocation:LocationCoord, val endLocation:LocationCoord)

fun collectDirections(startLocation: String, endLocation: String, onComplete: (List<PolyfillPoint>) -> Unit) {
    var result:List<PolyfillPoint> = listOf<PolyfillPoint>();
    val directionPairs = mutableListOf<PolyfillPoint>()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Fetching directions using Google Directions API
            val directionsUrlString = "https://maps.googleapis.com/maps/api/directions/json?origin=${startLocation}&destination=${endLocation}&key=AIzaSyDHNDMGczp96oVLFyYu6pFMhZjoK0-DpvE"
            val directionsUrl = URL(directionsUrlString)
            val directionsConnection = directionsUrl.openConnection() as HttpURLConnection
            directionsConnection.requestMethod = "GET"
            val directionsInputStream = directionsConnection.inputStream
            val directionsJsonString = directionsInputStream.bufferedReader().use { it.readText() }
            val directionsJsonObject = JSONObject(directionsJsonString)
            val routesArray = directionsJsonObject.getJSONArray("routes")

            for (i in 0 until routesArray.length()) {
                val routeObject = routesArray.getJSONObject(i)
                val legsArray = routeObject.getJSONArray("legs")

                for (j in 0 until legsArray.length()) {
                    val legObject = legsArray.getJSONObject(j)
                    val stepsArray = legObject.getJSONArray("steps")

                    for (k in 0 until stepsArray.length()) {
                        val stepObject = stepsArray.getJSONObject(k)
                        val htmlInstructions = stepObject.getString("html_instructions")
                        val startLocationObj = stepObject.getJSONObject("start_location")
                        val endLocationObj = stepObject.getJSONObject("end_location")
                        val startLat = startLocationObj.getDouble("lat")
                        val startLng = startLocationObj.getDouble("lng")
                        val endLat = endLocationObj.getDouble("lat")
                        val endLng = endLocationObj.getDouble("lng")
                        val startPair =LocationCoord(startLat, startLng)
                        val endPair = LocationCoord(endLat, endLng)
                        directionPairs.add(PolyfillPoint(startPair, endPair))
                    }
                }
            }

            // Update start and end destinations on the main thread
            withContext(Dispatchers.Main) {
                result = directionPairs
                onComplete(directionPairs)

            }
        } catch (e: IOException) {
            // Handle error
            e.printStackTrace()
        }
    }

}

*/











package com.arnavigation.app.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
import com.arnavigation.app.ui.viewmodels.LocationViewModel
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

// @Composable
// fun HomeScreen(navController: NavController) {
//     var searchText by remember { mutableStateOf("") }
//     var suggestions by remember { mutableStateOf(emptyList<LocationSuggestion>()) }
//     val locationViewModel: LocationViewModel = viewModel()
//     val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//     var ctx = LocalContext.current
//     val preferencesManager = remember { PreferencesManager(ctx) }
//     var markerPosition by remember { mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())) }
//     var destinationMarkerPosition by remember {
//         mutableStateOf(LatLng(0.0,0.0))
//     }
//     var directionPath by remember {
//         mutableStateOf(listOf<PolyfillPoint>())
//     }
//     var suggestionSelected by remember { mutableStateOf(false) }
//     var directions by remember { mutableStateOf(emptyList<String>()) }
//     val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
//     var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }

//     Column(modifier = Modifier.fillMaxWidth()) {
//         Column(
//             modifier = Modifier.fillMaxWidth()
//         ) {
//             TextField(
//                 value = searchText,
//                 onValueChange = { newSearchText ->
//                     searchText = newSearchText

//                     if (newSearchText.isNotEmpty()) {
//                         // Launch coroutine to perform search
//                         searchLocation(newSearchText) { result ->
//                             suggestions = result
//                         }
//                     } else {
//                         suggestions = emptyList()
//                     }
//                 },
//                 label = { Text(text = "Search") },
//                 leadingIcon = {
//                     Icon(Icons.Filled.Search, contentDescription = "Search")
//                 },
//                 trailingIcon = {
//                     if (searchText.isNotEmpty()) {
//                         IconButton(onClick = {
//                             searchText = ""
//                             suggestions = emptyList() // Clear suggestions when clearing search text
//                         }) {
//                             Icon(Icons.Filled.Clear, contentDescription = "Clear")
//                         }
//                     }
//                 },
//                 modifier = Modifier.fillMaxWidth()
//             )


//             if (suggestions.isNotEmpty() && !suggestionSelected) { // Show suggestions only if none is selected
//                 Column(modifier = Modifier.fillMaxWidth()) {
//                     for (suggestion in suggestions) {
//                         Text(
//                             text = suggestion.displayName,
//                             fontSize = 15.sp,
//                             letterSpacing = 1.sp,
//                             modifier = Modifier
//                                 .padding(start = 15.dp, bottom = 10.dp)
//                                 .fillMaxWidth()
//                                 .clickable {
//                                     // Handle suggestion selection
//                                     destinationMarkerPosition =
//                                         LatLng(suggestion.latitude, suggestion.longitude)
//                                     destinationLocationViewModel.setLocation(
//                                         suggestion.latitude,
//                                         suggestion.longitude
//                                     )
//                                     suggestionSelected = true // Set suggestion selected
//                                     suggestions = emptyList() // Clear suggestions after selection
//                                     preferencesManager.saveData("destinationLocationLatitude", destinationMarkerPosition.latitude.toString())
//                                     preferencesManager.saveData("destinationLocationLongitude", destinationMarkerPosition.longitude.toString())
//                                     collectDirections(
//                                         "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
//                                         "${destinationMarkerPosition.latitude.toString()},${destinationMarkerPosition.longitude.toString()}"
//                                     ) { result ->
//                                         directionPath = result
//                                         var pointArray = arrayListOf<LatLng>()
// //                                          Log.d("---------cool",result.lastIndex.toString())
//                                         for (i in 0 until result.lastIndex) {
//                                             pointArray.add(
//                                                 LatLng(
//                                                     result[i].startLocation.latitude,
//                                                     result[i].startLocation.longitude
//                                                 )
//                                             )
//                                         }
//                                         pointsList = pointArray
// //                                        Log.d("---------cool",pointsList.lastIndex.toString())
//                                     }


//                                 },
//                             textAlign = TextAlign.Left
//                         )
//                     }
//                 }
//             }
//         }


//         val cameraPositionState = rememberCameraPositionState {
//             position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
//         }

//         GoogleMap(modifier = Modifier
//             .fillMaxSize()
//             .padding(bottom = 40.dp),
//             cameraPositionState = cameraPositionState){
//             Marker(
//                 state = MarkerState(position = currentLocation),
//                 title = "Me",
//                 snippet = "Marker in Big Ben"
//             )

//             if(destinationMarkerPosition.latitude!=0.0 && destinationMarkerPosition.longitude!=0.0){
//                 Marker(
//                     state = MarkerState(position = destinationMarkerPosition),
//                     title = "Destination",
//                     snippet = "Destination Position"
//                 )

//                 Polyline(points = pointsList, color = Color.Black)
// //                Log.d("---------cool",pointsList.lastIndex.toString())
//             }

//         }

//         // Display directions
//         Column(modifier = Modifier.fillMaxWidth()) {
//             for (step in directions) {
//                 Text(
//                     text = step,
//                     modifier = Modifier.padding(8.dp)
//                 )
//             }
//         }

//     }
//     if(destinationMarkerPosition.latitude!=0.0 && destinationMarkerPosition.longitude!=0.0){
//         Box(modifier = Modifier.padding(top = 60.dp, start = 280.dp)) {
//             Button(onClick = { navController.navigate("splitScreen") }) {
//                 Text(text = "Start")
//                 Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
//             }
//         }
//     }
// }

@Composable
fun HomeScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(emptyList<LocationSuggestion>()) }
    val locationViewModel: LocationViewModel = viewModel()
    val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }
    var markerPosition by remember { mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())) }
    var destinationMarkerPosition by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }
    var directionPath by remember {
        mutableStateOf(listOf<PolyfillPoint>())
    }
    var suggestionSelected by remember { mutableStateOf(false) }
    var directions by remember { mutableStateOf(emptyList<String>()) }
    val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())
    var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }

    @Composable
    // Function to display Polyline on the Google Map
    fun DisplayPolylineOnMap() {
        if (pointsList.isNotEmpty()) {
            Polyline(
                points = pointsList,
                color = Color.Black,
                width = 5f // Set Polyline width
            )
        } else {
            Log.d("HomeScreen", "pointsList is empty or not initialized.")
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = searchText,
                onValueChange = { newSearchText ->
                    searchText = newSearchText

                    if (newSearchText.isNotEmpty()) {
                        // Launch coroutine to perform search
                        searchLocation(newSearchText) { result ->
                            suggestions = result
                        }
                    } else {
                        suggestions = emptyList()
                    }
                },
                label = { Text(text = "Search") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = {
                            searchText = ""
                            suggestions = emptyList() // Clear suggestions when clearing search text
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (suggestions.isNotEmpty() && !suggestionSelected) { // Show suggestions only if none is selected
                Column(modifier = Modifier.fillMaxWidth()) {
                    for (suggestion in suggestions) {
                        Text(
                            text = suggestion.displayName,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .clickable {
                                    // Handle suggestion selection
                                    destinationMarkerPosition =
                                        LatLng(suggestion.latitude, suggestion.longitude)
                                    destinationLocationViewModel.setLocation(
                                        suggestion.latitude,
                                        suggestion.longitude
                                    )
                                    suggestionSelected = true // Set suggestion selected
                                    suggestions = emptyList() // Clear suggestions after selection
                                    preferencesManager.saveData("destinationLocationLatitude", destinationMarkerPosition.latitude.toString())
                                    preferencesManager.saveData("destinationLocationLongitude", destinationMarkerPosition.longitude.toString())
                                    collectDirections(
                                        "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
                                        "${destinationMarkerPosition.latitude.toString()},${destinationMarkerPosition.longitude.toString()}"
                                    ) { result ->
                                        directionPath = result
                                        var pointArray = arrayListOf<LatLng>()
                                        for (i in 0 until result.lastIndex) {
                                            pointArray.add(
                                                LatLng(
                                                    result[i].startLocation.latitude,
                                                    result[i].startLocation.longitude
                                                )
                                            )
                                        }
                                        pointsList = pointArray
                                    }
                                },
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
        }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
        }

        GoogleMap(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
            cameraPositionState = cameraPositionState) {
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Me",
                snippet = "Marker in Big Ben"
            )

            if (destinationMarkerPosition.latitude != 0.0 && destinationMarkerPosition.longitude != 0.0) {
                Marker(
                    state = MarkerState(position = destinationMarkerPosition),
                    title = "Destination",
                    snippet = "Destination Position"
                )

                DisplayPolylineOnMap()
            }
        }

        // Display directions
        Column(modifier = Modifier.fillMaxWidth()) {
            for (step in directions) {
                Text(
                    text = step,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    // Start navigation button
    if (destinationMarkerPosition.latitude != 0.0 && destinationMarkerPosition.longitude != 0.0) {
        Box(modifier = Modifier.padding(top = 60.dp, start = 280.dp)) {
            Button(onClick = { navController.navigate("splitScreen") }) {
                Text(text = "Start")
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
            }
        }
    }

    // Debugging statements
    Log.d("HomeScreen", "Current Location: $currentLocation")
    Log.d("HomeScreen", "Destination Location: $destinationMarkerPosition")
    Log.d("HomeScreen", "pointsList: $pointsList")
}





data class LocationSuggestion(
    val displayName: String,
    val latitude: Double,
    val longitude: Double
)

fun searchLocation(query: String, onComplete: (List<LocationSuggestion>) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val urlString = "https://nominatim.openstreetmap.org/search?q=${query}&format=json"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val inputStream = connection.inputStream
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val results = mutableListOf<LocationSuggestion>()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val displayName = jsonObj.getString("display_name")
                val latitude = jsonObj.getDouble("lat")
                val longitude = jsonObj.getDouble("lon")
                results.add(LocationSuggestion(displayName, latitude, longitude))
            }

            // Update suggestions list on the main thread
            withContext(Dispatchers.Main) {
                onComplete(results)
            }
        } catch (e: IOException) {
            // Handle error
            e.printStackTrace()
        }
    }
}
data class LocationCoord(val latitude:Double, val longitude:Double)
data class PolyfillPoint(val startLocation:LocationCoord, val endLocation:LocationCoord)

fun collectDirections(startLocation: String, endLocation: String, onComplete: (List<PolyfillPoint>) -> Unit) {
    var result:List<PolyfillPoint> = listOf<PolyfillPoint>();
    val directionPairs = mutableListOf<PolyfillPoint>()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Fetching directions using Google Directions API
            val directionsUrlString = "https://maps.googleapis.com/maps/api/directions/json?origin=${startLocation}&destination=${endLocation}&key=AIzaSyDHNDMGczp96oVLFyYu6pFMhZjoK0-DpvE"
            val directionsUrl = URL(directionsUrlString)
            val directionsConnection = directionsUrl.openConnection() as HttpURLConnection
            directionsConnection.requestMethod = "GET"
            val directionsInputStream = directionsConnection.inputStream
            val directionsJsonString = directionsInputStream.bufferedReader().use { it.readText() }
            val directionsJsonObject = JSONObject(directionsJsonString)
            val routesArray = directionsJsonObject.getJSONArray("routes")

            for (i in 0 until routesArray.length()) {
                val routeObject = routesArray.getJSONObject(i)
                val legsArray = routeObject.getJSONArray("legs")

                for (j in 0 until legsArray.length()) {
                    val legObject = legsArray.getJSONObject(j)
                    val stepsArray = legObject.getJSONArray("steps")

                    for (k in 0 until stepsArray.length()) {
                        val stepObject = stepsArray.getJSONObject(k)
                        val htmlInstructions = stepObject.getString("html_instructions")
                        val startLocationObj = stepObject.getJSONObject("start_location")
                        val endLocationObj = stepObject.getJSONObject("end_location")
                        val startLat = startLocationObj.getDouble("lat")
                        val startLng = startLocationObj.getDouble("lng")
                        val endLat = endLocationObj.getDouble("lat")
                        val endLng = endLocationObj.getDouble("lng")
                        val startPair =LocationCoord(startLat, startLng)
                        val endPair = LocationCoord(endLat, endLng)
                        directionPairs.add(PolyfillPoint(startPair, endPair))
                    }
                }
            }

            // Update start and end destinations on the main thread
            withContext(Dispatchers.Main) {
                result = directionPairs
                onComplete(directionPairs)

            }
        } catch (e: IOException) {
            // Handle error
            e.printStackTrace()
        }
    }

}














/*
package com.arnavigation.app.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
import com.arnavigation.app.ui.viewmodels.LocationViewModel
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun HomeScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(emptyList<LocationSuggestion>()) }
    val locationViewModel: LocationViewModel = viewModel()
    val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }
    var markerPosition by remember { mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())) }
    var destinationMarkerPosition by remember {
        mutableStateOf(LatLng(0.0,0.0))
    }
    var directionPath by remember {
        mutableStateOf(listOf<PolyfillPoint>())
    }
    var suggestionSelected by remember { mutableStateOf(false) }
    var directions by remember { mutableStateOf(emptyList<String>()) }
    val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
    var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = searchText,
                onValueChange = { newSearchText ->
                    searchText = newSearchText

                    if (newSearchText.isNotEmpty()) {
                        // Launch coroutine to perform search
                        searchLocation(newSearchText) { result ->
                            suggestions = result
                        }
                    } else {
                        suggestions = emptyList()
                    }
                },
                label = { Text(text = "Search") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = {
                            searchText = ""
                            suggestions = emptyList() // Clear suggestions when clearing search text
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )


            if (suggestions.isNotEmpty() && !suggestionSelected) { // Show suggestions only if none is selected
                Column(modifier = Modifier.fillMaxWidth()) {
                    for (suggestion in suggestions) {
                        Text(
                            text = suggestion.displayName,
                            fontSize = 15.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .clickable {
                                    // Handle suggestion selection
                                    destinationMarkerPosition =
                                        LatLng(suggestion.latitude, suggestion.longitude)
                                    destinationLocationViewModel.setLocation(
                                        suggestion.latitude,
                                        suggestion.longitude
                                    )
                                    suggestionSelected = true // Set suggestion selected
                                    suggestions = emptyList() // Clear suggestions after selection
                                    preferencesManager.saveData("destinationLocationLatitude", destinationMarkerPosition.latitude.toString())
                                    preferencesManager.saveData("destinationLocationLongitude", destinationMarkerPosition.longitude.toString())
                                    collectDirections(
                                        "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
                                        "${destinationMarkerPosition.latitude.toString()},${destinationMarkerPosition.longitude.toString()}"
                                    ) { result ->
                                        directionPath = result
                                        var pointArray = arrayListOf<LatLng>()
//                                          Log.d("---------cool",result.lastIndex.toString())
                                        for (i in 0 until result.lastIndex) {
                                            pointArray.add(
                                                LatLng(
                                                    result[i].startLocation.latitude,
                                                    result[i].startLocation.longitude
                                                )
                                            )
                                        }
                                        pointsList = pointArray
//                                        Log.d("---------cool",pointsList.lastIndex.toString())
                                    }


                                },
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
        }


        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
        }

        GoogleMap(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
            cameraPositionState = cameraPositionState){
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Me",
                snippet = "Marker in Big Ben"
            )

            if(destinationMarkerPosition.latitude!=0.0 && destinationMarkerPosition.longitude!=0.0){
                Marker(
                    state = MarkerState(position = destinationMarkerPosition),
                    title = "Destination",
                    snippet = "Destination Position"
                )

                Polyline(points = pointsList, color = Color.Black)
//                Log.d("---------cool",pointsList.lastIndex.toString())
            }

        }

        // Display directions
        Column(modifier = Modifier.fillMaxWidth()) {
            for (step in directions) {
                Text(
                    text = step,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
    if(destinationMarkerPosition.latitude!=0.0 && destinationMarkerPosition.longitude!=0.0){
        Box(modifier = Modifier.padding(top = 60.dp, start = 280.dp)) {
            Button(onClick = { navController.navigate("splitScreen") }) {
                Text(text = "Start")
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
            }
        }
    }
}

data class LocationSuggestion(
    val displayName: String,
    val latitude: Double,
    val longitude: Double
)

fun searchLocation(query: String, onComplete: (List<LocationSuggestion>) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val urlString = "https://nominatim.openstreetmap.org/search?q=${query}&format=json"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val inputStream = connection.inputStream
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val results = mutableListOf<LocationSuggestion>()
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val displayName = jsonObj.getString("display_name")
                val latitude = jsonObj.getDouble("lat")
                val longitude = jsonObj.getDouble("lon")
                results.add(LocationSuggestion(displayName, latitude, longitude))
            }

            // Update suggestions list on the main thread
            withContext(Dispatchers.Main) {
                onComplete(results)
            }
        } catch (e: IOException) {
            // Handle error
            e.printStackTrace()
        }
    }
}
data class LocationCoord(val latitude:Double, val longitude:Double)
data class PolyfillPoint(val startLocation:LocationCoord, val endLocation:LocationCoord)

fun collectDirections(startLocation: String, endLocation: String, onComplete: (List<PolyfillPoint>) -> Unit) {
    var result:List<PolyfillPoint> = listOf<PolyfillPoint>();
    val directionPairs = mutableListOf<PolyfillPoint>()

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Fetching directions using Google Directions API
            val directionsUrlString = "https://maps.googleapis.com/maps/api/directions/json?origin=${startLocation}&destination=${endLocation}&key=AIzaSyDHNDMGczp96oVLFyYu6pFMhZjoK0-DpvE"
            val directionsUrl = URL(directionsUrlString)
            val directionsConnection = directionsUrl.openConnection() as HttpURLConnection
            directionsConnection.requestMethod = "GET"
            val directionsInputStream = directionsConnection.inputStream
            val directionsJsonString = directionsInputStream.bufferedReader().use { it.readText() }
            val directionsJsonObject = JSONObject(directionsJsonString)
            val routesArray = directionsJsonObject.getJSONArray("routes")

            for (i in 0 until routesArray.length()) {
                val routeObject = routesArray.getJSONObject(i)
                val legsArray = routeObject.getJSONArray("legs")

                for (j in 0 until legsArray.length()) {
                    val legObject = legsArray.getJSONObject(j)
                    val stepsArray = legObject.getJSONArray("steps")

                    for (k in 0 until stepsArray.length()) {
                        val stepObject = stepsArray.getJSONObject(k)
                        val htmlInstructions = stepObject.getString("html_instructions")
                        val startLocationObj = stepObject.getJSONObject("start_location")
                        val endLocationObj = stepObject.getJSONObject("end_location")
                        val startLat = startLocationObj.getDouble("lat")
                        val startLng = startLocationObj.getDouble("lng")
                        val endLat = endLocationObj.getDouble("lat")
                        val endLng = endLocationObj.getDouble("lng")
                        val startPair =LocationCoord(startLat, startLng)
                        val endPair = LocationCoord(endLat, endLng)
                        directionPairs.add(PolyfillPoint(startPair, endPair))
                    }
                }
            }

            // Update start and end destinations on the main thread
            withContext(Dispatchers.Main) {
                result = directionPairs
                onComplete(directionPairs)

            }
        } catch (e: IOException) {
            // Handle error
            e.printStackTrace()
        }
    }

}








//@Preview
//@Composable
//fun PreviewHomeScreen() {
//    HomeScreen()
//}
*/
