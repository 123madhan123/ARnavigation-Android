package com.arnavigation.app

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.arnavigation.app.ui.composables.NavBottomBar
import com.arnavigation.app.ui.composables.currentRoute
import com.arnavigation.app.ui.navigation.App
import com.arnavigation.app.ui.navigation.BottomNavigationScreens
import com.arnavigation.app.ui.theme.ARNavigationTheme
import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
import com.arnavigation.app.ui.viewmodels.LocationViewModel
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latI:Double = 0.0
    var longI:Double = 0.0
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ARNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val bottomNavigationItems = listOf(
                        BottomNavigationScreens.Home,
                        BottomNavigationScreens.Profile,
                        BottomNavigationScreens.Settings
                    )
                    val navController = rememberNavController()
                    Scaffold(
                        floatingActionButton = {
                            var destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//                           if(currentRoute(navController = navController)=="homeScreen"){
//                               FloatingActionButton(onClick = {
//                                   if(destinationLocationViewModel.getLatitude()==0.0&&destinationLocationViewModel.getLongitude()==0.0){
//                                       Toast.makeText(applicationContext,"Choose Destination Location",Toast.LENGTH_SHORT ).show()
//                                   }
//                               }) {
//                                   Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
//                               }
//                           }
                        },
                        bottomBar = {
                            if((currentRoute(navController = navController)=="Login")){

                            }else if((currentRoute(navController = navController)=="signup")){
                            }
                            else{
                                NavBottomBar(navController=navController, items = bottomNavigationItems)
                            }
                        }
                    ) { it->
                        val locationViewModel: LocationViewModel = viewModel()
                        App(navController = navController, modifier = Modifier.padding(it))
                        val preferencesManager = remember { PreferencesManager(this) }
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                        val task = fusedLocationProviderClient.lastLocation
                        if(ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            !=PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
                        }
                        task.addOnSuccessListener(this) { it ->
                            if (it != null) {
                                preferencesManager.saveData("latitude", it.latitude.toString())
                                preferencesManager.saveData("longitude", it.longitude.toString())

                            }
                        }
                        locationViewModel.setLocation(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
//                        Toast.makeText(applicationContext,"${locationViewModel.getLatitude()}",Toast.LENGTH_LONG ).show()
                    }
                }

            }
        }
    }
}
