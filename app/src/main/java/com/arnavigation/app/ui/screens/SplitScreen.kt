
package com.arnavigation.app.ui.screens
//
//import android.Manifest
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.arnavigation.app.R
//import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
//import com.arnavigation.app.ui.viewmodels.PolyLineViewModel
//import com.arnavigation.app.ui.viewmodels.PreferencesManager
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.ar.core.Config
//import com.google.ar.core.Session
//import com.google.ar.core.exceptions.UnavailableException
//import com.google.ar.sceneform.AnchorNode
//import com.google.ar.sceneform.Node
//import com.google.ar.sceneform.math.Quaternion
//import com.google.ar.sceneform.math.Vector3
//import com.google.ar.sceneform.rendering.MaterialFactory
//import com.google.ar.sceneform.rendering.ModelRenderable
//import com.google.ar.sceneform.rendering.ShapeFactory
//import com.google.ar.sceneform.ux.ArFragment
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.Polyline
//import com.google.maps.android.compose.rememberCameraPositionState
//import kotlinx.coroutines.launch
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                ARNavigationScreen()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ARNavigationScreen() {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
//
//    DisposableEffect(Unit) {
//        coroutineScope.launch {
//            permissionState.launchPermissionRequest()
//        }
//        onDispose { }
//    }
//
//    if (permissionState.hasPermission) {
//        ARNavigationContent(context)
//    } else {
//        // Handle permission denied
//        Surface(
//            color = Color.Red,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // You can replace this with any UI component or message you want to show
//            Text(
//                text = "Camera permission is required to use this feature",
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun ARNavigationContent(context: android.content.Context) {
//    val arFragment = remember { ArFragment() }
//    var session: Session? by remember { mutableStateOf(null) }
//
//    // Start AR session
//    LaunchedEffect(Unit) {
//        try {
//            session = Session(context)
//            val config = Config(session).apply {
//                updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
//            }
//            session?.configure(config)
//        } catch (e: UnavailableException) {
//            // Handle ARCore not available on this device
//            Log.e("ARNavigation", "ARCore is not available on this device", e)
//        }
//    }
//
//    // Create an AnchorNode for rendering waypoints and routes
//    val anchorNode = remember { AnchorNode() }
//
//    // Render AR scene
//    Box(modifier = Modifier.fillMaxSize()) {
//        ArView(arFragment)
//        val currentLocation = LatLng(0.0, 0.0) // Placeholder
//        val destinationLocation = LatLng(0.0, 0.0) // Placeholder
//        val route = listOf<LatLng>() // Placeholder
//        val waypoints = listOf<LatLng>() // Placeholder
//
//        // Render waypoints
//        for (waypoint in waypoints) {
//            ArWaypoint(anchorNode = anchorNode, waypointLocation = waypoint)
//        }
//
//        // Render navigation route
//        ArRoute(anchorNode = anchorNode, route = route)
//
//        // Include MapContent composable here
//        MapContent()
//    }
//}
//
//@Composable
//fun ArView(arFragment: ArFragment) {
//    AndroidView(
//        factory = { arFragment.requireView() },
//        modifier = Modifier.fillMaxSize()
//    )
//}
//
//@Composable
//fun ArWaypoint(anchorNode: AnchorNode, waypointLocation: LatLng) {
//    val context = LocalContext.current
//
//    // Get the resource ID of the sphere model in the raw directory
//    val resourceId =
//        R.raw.sphere
//
//    ModelRenderable.builder()
//        .setSource(context, resourceId)
//        .build()
//        .thenAccept { modelRenderable ->
//            anchorNode.apply {
//                renderable = modelRenderable
//                worldPosition = Vector3(waypointLocation.latitude.toFloat(), 0f, waypointLocation.longitude.toFloat())
//            }
//        }
//        .exceptionally { throwable ->
//            // Handle the exception
//            Log.e("ArWaypoint", "Error loading model: $throwable")
//            null
//        }
//}
//
//@Composable
//fun ArRoute(anchorNode: AnchorNode, route: List<LatLng>) {
//    val context = LocalContext.current
//
//    // ARSceneform color
//    val arColor = com.google.ar.sceneform.rendering.Color(0.0f, 0.0f, 1.0f, 0.8f)
//
//    // Render route segments
//    for (i in 0 until route.size - 1) {
//        val startPoint = route[i]
//        val endPoint = route[i + 1]
//
//        val startVector = Vector3(startPoint.latitude.toFloat(), 0f, startPoint.longitude.toFloat())
//        val endVector = Vector3(endPoint.latitude.toFloat(), 0f, endPoint.longitude.toFloat())
//
//        MaterialFactory.makeOpaqueWithColor(context, arColor)
//            .thenAccept { material ->
//                val segmentNode = createCylinderBetweenPoints(startVector, endVector, material)
//                anchorNode.addChild(segmentNode)
//            }
//    }
//}
//
//fun createCylinderBetweenPoints(startVector: Vector3, endVector: Vector3, material: com.google.ar.sceneform.rendering.Material): Node {
//    val segmentNode = Node()
//
//    // Calculate the length and direction of the cylinder
//    val direction = Vector3.subtract(endVector, startVector)
//    val length = direction.length()
//
//    // Calculate the center position of the cylinder
//    val centerPosition = Vector3.add(startVector, direction.scaled(0.5f))
//
//    // Create the cylinder shape and attach it to the segment node
//    val cylinder = ShapeFactory.makeCylinder(0.01f, length, Vector3.zero(), material)
//    segmentNode.renderable = cylinder
//
//    // Calculate the rotation quaternion to orient the cylinder along the direction vector
//    val rotationQuaternion = Quaternion.lookRotation(direction, Vector3.up())
//
//    // Set the position and orientation of the segment node
//    segmentNode.worldPosition = centerPosition
//    segmentNode.worldRotation = rotationQuaternion
//
//    return segmentNode
//}
//
//@Composable
//fun MapContent() {
//    var ctx = LocalContext.current
//    val preferencesManager = remember { PreferencesManager(ctx) }
//    var markerPosition by remember {
//        mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble()))
//    }
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
//    }
//    val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())
//    val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//    val polyLineViewModel: PolyLineViewModel = viewModel()
//    var destinationLocationLatitude by remember {
//        mutableStateOf(preferencesManager.getData("destinationLocationLatitude", "0.0"))
//    }
//    var destinationLocationLongitude by remember {
//        mutableStateOf(preferencesManager.getData("destinationLocationLongitude", "0.0"))
//    }
//    var directionPath by remember {
//        mutableStateOf(listOf<PolyfillPoint>())
//    }
//    var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }
//    GoogleMap(modifier = Modifier
//        .fillMaxSize()
//        .padding(bottom = 40.dp),
//        cameraPositionState = cameraPositionState){
//        Marker(
//            state = MarkerState(position = currentLocation),
//            title = "Me",
//            snippet = "Marker in Big Ben"
//        )
//
//        if(destinationLocationLatitude.toDouble()!=0.0 && destinationLocationLongitude.toDouble()!=0.0){
//            Marker(
//                state = MarkerState(position = LatLng(destinationLocationLatitude.toDouble(),destinationLocationLongitude.toDouble())),
//                title = "Destination",
//                snippet = "Destination Position"
//            )
//            collectDirections(
//                "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
//                "${destinationLocationLatitude.toString()},${destinationLocationLongitude.toString()}"
//            ) { result ->
//                directionPath = result
//                var pointArray = arrayListOf<LatLng>()
//                for (i in 0 until result.lastIndex) {
//                    pointArray.add(
//                        LatLng(
//                            result[i].startLocation.latitude,
//                            result[i].startLocation.longitude
//                        )
//                    )
//                }
//                pointsList = pointArray
//            }
//
//            Polyline(points = pointsList, color = Color.Black)
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreviewARNavigationScreen() {
//    MaterialTheme {
//        ARNavigationScreen()
//    }
//}
// try this one


//package com.arnavigation.app.ui.screens

////running this code
//
//
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
import com.arnavigation.app.ui.viewmodels.PolyLineViewModel
import com.arnavigation.app.ui.viewmodels.PreferencesManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SplashScreen()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SplashScreen() {
    Surface(color = Color.White) {
        Column(modifier = Modifier.fillMaxSize()) {
            CameraPreviewScreen()
            MapContent()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreviewScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    DisposableEffect(Unit) {
        coroutineScope.launch {
            permissionState.launchPermissionRequest()
        }
        onDispose { }
    }

    if (permissionState.hasPermission) {
        CameraPreview(context)
    } else {
        // Handle permission denied
        Surface(
            color = Color.Red,
            modifier = Modifier.fillMaxSize()
        ) {
            // You can replace this with any UI component or message you want to show
            Text(
                text = "Camera permission is required to use this feature",
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun CameraPreview(context: android.content.Context) {
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp) // Adjust height as needed
            .background(Color.Black)
    ) {
        CameraPreviewView(context, cameraExecutor)
    }
}

@Composable
fun CameraPreviewView(context: android.content.Context, cameraExecutor: ExecutorService) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(factory = { context ->
        androidx.camera.view.PreviewView(context).apply {
            // Make sure the PreviewView fills the entire screen
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                // Create preview use case
                val preview = androidx.camera.core.Preview.Builder().build()

                try {
                    // Unbind any existing use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind preview to lifecycle
                    preview.setSurfaceProvider(surfaceProvider)

                    // Bind camera use cases to lifecycle
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview
                    )
                } catch (exc: Exception) {
                    // Handle exceptions
                }
            }, ContextCompat.getMainExecutor(context))
        }
    })
}

@Composable
fun MapContent() {

    var ctx = LocalContext.current
    val preferencesManager = remember { PreferencesManager(ctx) }
    var markerPosition by remember { mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
    }
    val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
    val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
    val polyLineViewModel: PolyLineViewModel = viewModel()
//    var destinationMarkerPosition by remember {
//        mutableStateOf(LatLng(destinationLocationViewModel.getLatitude(),destinationLocationViewModel.getLongitude()))
//    }
    var destinationLocationLatitude by remember {
        mutableStateOf(preferencesManager.getData("destinationLocationLatitude","0.0"))
    }
    var destinationLocationLongitude by remember {
        mutableStateOf(preferencesManager.getData("destinationLocationLongitude","0.0"))
    }
    var directionPath by remember {
        mutableStateOf(listOf<PolyfillPoint>())
    }
    var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }
    GoogleMap(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 40.dp),
        cameraPositionState = cameraPositionState){
        Marker(
            state = MarkerState(position = currentLocation),
            title = "Me",
            snippet = "Marker in Big Ben"
        )

        if(destinationLocationLatitude.toDouble()!=0.0 && destinationLocationLongitude.toDouble()!=0.0){
            Marker(
                state = MarkerState(position = LatLng(destinationLocationLatitude.toDouble(),destinationLocationLongitude.toDouble())),
                title = "Destination",
                snippet = "Destination Position"
            )
            collectDirections(
                "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
                "${destinationLocationLatitude.toString()},${destinationLocationLongitude.toString()}"
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
                Log.d("---------cool",pointsList.lastIndex.toString())
            }

            Polyline(points = pointsList, color = Color.Black)
        }

    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    MaterialTheme {
        SplashScreen()
    }
}
//
//import android.Manifest
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.arnavigation.app.R
//import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
//import com.arnavigation.app.ui.viewmodels.PreferencesManager
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.android.gms.maps.model.LatLng
//import com.google.ar.core.Config
//import com.google.ar.core.Session
//import com.google.ar.core.exceptions.UnavailableException
//import com.google.ar.sceneform.AnchorNode
//import com.google.ar.sceneform.Node
//import com.google.ar.sceneform.math.Quaternion
//import com.google.ar.sceneform.math.Vector3
//import com.google.ar.sceneform.rendering.MaterialFactory
//import com.google.ar.sceneform.rendering.ModelRenderable
//import com.google.ar.sceneform.rendering.ShapeFactory
//import com.google.ar.sceneform.ux.ArFragment
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalPermissionsApi::class)
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                ARNavigationScreen()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ARNavigationScreen() {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
//
//    DisposableEffect(Unit) {
//        coroutineScope.launch {
//            permissionState.launchPermissionRequest()
//        }
//        onDispose { }
//    }
//
//    if (permissionState.hasPermission) {
//        ARNavigationContent(context)
//    } else {
//        // Handle permission denied
//        Surface(
//            color = Color.Red,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // You can replace this with any UI component or message you want to show
//            Text(
//                text = "Camera permission is required to use this feature",
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun ARNavigationContent(context: android.content.Context) {
//    val arFragment = remember { ArFragment() }
//    var session: Session? by remember { mutableStateOf(null) }
//
//    // Start AR session
//    LaunchedEffect(Unit) {
//        try {
//            session = Session(context)
//            val config = Config(session).apply {
//                updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
//            }
//            session?.configure(config)
//        } catch (e: UnavailableException) {
//            // Handle ARCore not available on this device
//            Log.e("ARNavigation", "ARCore is not available on this device", e)
//        }
//    }
//
//    // Create an AnchorNode for rendering waypoints and routes
//    val anchorNode = remember { AnchorNode() }
//
//    // Render AR scene
//    Box(modifier = Modifier.fillMaxSize()) {
//        ArView(arFragment)
//        var ctx = LocalContext.current
//        val preferencesManager = remember { PreferencesManager(ctx) }
//        val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//        val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble()) // Placeholder
//        val destinationLocation =LatLng(preferencesManager.getData("destinationLocationLatitude","0.0"),preferencesManager.getData("destinationLocationLongitude","0.0"))
//        val route = listOf<LatLng>(pointsList) // Placeholder
//        val waypoints = listOf<LatLng>(pointsList) // Placeholder
//
//        val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
//        val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//        val polyLineViewModel: PolyLineViewModel = viewModel()
//
//        var destinationLocationLatitude by remember {
//            mutableStateOf(preferencesManager.getData("destinationLocationLatitude","0.0"))
//        }
//        var destinationLocationLongitude by remember {
//            mutableStateOf(preferencesManager.getData("destinationLocationLongitude","0.0"))
//        }
//        var directionPath by remember {
//            mutableStateOf(listOf<PolyfillPoint>())
//        }
//        var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }
//        GoogleMap(modifier = Modifier
//            .fillMaxSize()
//            .padding(bottom = 40.dp),
//            cameraPositionState = cameraPositionState){
//            Marker(
//                state = MarkerState(position = currentLocation),
//                title = "Me",
//                snippet = "Marker in Big Ben"
//            )
//
//            if(destinationLocationLatitude.toDouble()!=0.0 && destinationLocationLongitude.toDouble()!=0.0){
//                Marker(
//                    state = MarkerState(position = LatLng(destinationLocationLatitude.toDouble(),destinationLocationLongitude.toDouble())),
//                    title = "Destination",
//                    snippet = "Destination Position"
//                )
//                collectDirections(
//                    "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
//                    "${destinationLocationLatitude.toString()},${destinationLocationLongitude.toString()}"
//                ) { result ->
//                    directionPath = result
//                    var pointArray = arrayListOf<LatLng>()
////                                          Log.d("---------cool",result.lastIndex.toString())
//                    for (i in 0 until result.lastIndex) {
//                        pointArray.add(
//                            LatLng(
//                                result[i].startLocation.latitude,
//                                result[i].startLocation.longitude
//                            )
//                        )
//                    }
//                    pointsList = pointArray
//                    Log.d("---------cool",pointsList.lastIndex.toString())
//                }
//
//                Polyline(points = pointsList, color = Color.Black)
//            }
//
//        // Render waypoints
//        for (waypoint in waypoints) {
//            ArWaypoint(anchorNode = anchorNode, waypointLocation = waypoint)
//        }
//
//        // Render navigation route
//        ArRoute(anchorNode = anchorNode, route = route)
//
//        // Include CameraPreviewScreen composable here
//        CameraPreviewScreen()
//    }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun CameraPreviewScreen() {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
//
//    DisposableEffect(Unit) {
//        coroutineScope.launch {
//            permissionState.launchPermissionRequest()
//        }
//        onDispose { }
//    }
//
//    }
//
//
//
//@Composable
//fun ArView(arFragment: ArFragment) {
//    AndroidView(
//        factory = { arFragment.requireView() },
//        modifier = Modifier.fillMaxSize()
//    )
//}
//
//@Composable
//fun ArWaypoint(anchorNode: AnchorNode, waypointLocation: LatLng) {
//    val context = LocalContext.current
//
//    // Get the resource ID of the sphere model in the raw directory
//    val resourceId = R.raw.sphere
//
//    ModelRenderable.builder()
//        .setSource(context, resourceId)
//        .build()
//        .thenAccept { modelRenderable ->
//            anchorNode.apply {
//                renderable = modelRenderable
//                worldPosition = Vector3(waypointLocation.latitude.toFloat(), 0f, waypointLocation.longitude.toFloat())
//            }
//        }
//        .exceptionally { throwable ->
//            // Handle the exception
//            Log.e("ArWaypoint", "Error loading model: $throwable")
//            null
//        }
//}
//
//@Composable
//fun ArRoute(anchorNode: AnchorNode, route: List<LatLng>) {
//    val context = LocalContext.current
//
//    // ARSceneform color
//    val arColor = com.google.ar.sceneform.rendering.Color(0.0f, 0.0f, 1.0f, 0.8f)
//
//    // Render route segments
//    for (i in 0 until route.size - 1) {
//        val startPoint = route[i]
//        val endPoint = route[i + 1]
//
//        val startVector = Vector3(startPoint.latitude.toFloat(), 0f, startPoint.longitude.toFloat())
//        val endVector = Vector3(endPoint.latitude.toFloat(), 0f, endPoint.longitude.toFloat())
//
//        MaterialFactory.makeOpaqueWithColor(context, arColor)
//            .thenAccept { material ->
//                val segmentNode = createCylinderBetweenPoints(startVector, endVector, material)
//                anchorNode.addChild(segmentNode)
//            }
//    }
//}
//
//fun createCylinderBetweenPoints(startVector: Vector3, endVector: Vector3, material: com.google.ar.sceneform.rendering.Material): Node {
//    val segmentNode = Node()
//
//    // Calculate the length and direction of the cylinder
//    val direction = Vector3.subtract(endVector, startVector)
//    val length = direction.length()
//
//    // Calculate the center position of the cylinder
//    val centerPosition = Vector3.add(startVector, direction.scaled(0.5f))
//
//    // Create the cylinder shape and attach it to the segment node
//    val cylinder = ShapeFactory.makeCylinder(0.01f, length, Vector3.zero(), material)
//    segmentNode.renderable = cylinder
//
//    // Calculate the rotation quaternion to orient the cylinder along the direction vector
//    val rotationQuaternion = Quaternion.lookRotation(direction, Vector3.up())
//
//    // Set the position and orientation of the segment node
//    segmentNode.worldPosition = centerPosition
//    segmentNode.worldRotation = rotationQuaternion
//
//    return segmentNode
//}
//@Composable
//fun MapContent() {
//
//    var ctx = LocalContext.current
//    val preferencesManager = remember { PreferencesManager(ctx) }
//    var markerPosition by remember { mutableStateOf(LatLng(preferencesManager.getData("latitude", "0.0").toDouble(), preferencesManager.getData("longitude", "0.0").toDouble())) }
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
//    }
//    val currentLocation = LatLng(preferencesManager.getData("latitude", "0.0").toDouble(),preferencesManager.getData("longitude", "0.0").toDouble())
//    val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//    val polyLineViewModel: PolyLineViewModel = viewModel()
//
//    var destinationLocationLatitude by remember {
//        mutableStateOf(preferencesManager.getData("destinationLocationLatitude","0.0"))
//    }
//    var destinationLocationLongitude by remember {
//        mutableStateOf(preferencesManager.getData("destinationLocationLongitude","0.0"))
//    }
//    var directionPath by remember {
//        mutableStateOf(listOf<PolyfillPoint>())
//    }
//    var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }
//    GoogleMap(modifier = Modifier
//        .fillMaxSize()
//        .padding(bottom = 40.dp),
//        cameraPositionState = cameraPositionState){
//        Marker(
//            state = MarkerState(position = currentLocation),
//            title = "Me",
//            snippet = "Marker in Big Ben"
//        )
//
//        if(destinationLocationLatitude.toDouble()!=0.0 && destinationLocationLongitude.toDouble()!=0.0){
//            Marker(
//                state = MarkerState(position = LatLng(destinationLocationLatitude.toDouble(),destinationLocationLongitude.toDouble())),
//                title = "Destination",
//                snippet = "Destination Position"
//            )
//            collectDirections(
//                "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
//                "${destinationLocationLatitude.toString()},${destinationLocationLongitude.toString()}"
//            ) { result ->
//                directionPath = result
//                var pointArray = arrayListOf<LatLng>()
////                                          Log.d("---------cool",result.lastIndex.toString())
//                for (i in 0 until result.lastIndex) {
//                    pointArray.add(
//                        LatLng(
//                            result[i].startLocation.latitude,
//                            result[i].startLocation.longitude
//                        )
//                    )
//                }
//                pointsList = pointArray
//                Log.d("---------cool",pointsList.lastIndex.toString())
//            }
//
//            Polyline(points = pointsList, color = Color.Black)
//        }
//
//    }
//}

//@Preview
//@Composable
//fun SplashScreen() {
//    Surface(color = Color.White) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            ARNavigationScreen()
//            MapContent()
//        }
//    }
//}
//
//import android.Manifest
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.arnavigation.app.R
//import com.arnavigation.app.ui.viewmodels.DestinationLocationViewModel
//import com.arnavigation.app.ui.viewmodels.PolyLineViewModel
//import com.arnavigation.app.ui.viewmodels.PreferencesManager
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.ar.core.Config
//import com.google.ar.core.Session
//import com.google.ar.core.exceptions.UnavailableException
//import com.google.ar.sceneform.AnchorNode
//import com.google.ar.sceneform.Node
//import com.google.ar.sceneform.math.Quaternion
//import com.google.ar.sceneform.math.Vector3
//import com.google.ar.sceneform.rendering.MaterialFactory
//import com.google.ar.sceneform.rendering.ModelRenderable
//import com.google.ar.sceneform.rendering.ShapeFactory
//import com.google.ar.sceneform.ux.ArFragment
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.Polyline
//import com.google.maps.android.compose.rememberCameraPositionState
//import io.grpc.Context
//import kotlinx.coroutines.launch
//
//
//
//
//@OptIn(ExperimentalPermissionsApi::class)
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                ARNavigationScreen()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ARNavigationScreen() {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
//
//    DisposableEffect(Unit) {
//        coroutineScope.launch {
//            permissionState.launchPermissionRequest()
//        }
//        onDispose { }
//    }
//
//    if (permissionState.hasPermission) {
//        ARNavigationContent(context)
//    } else {
//        // Handle permission denied
//        Surface(
//            color = Color.Red,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            // You can replace this with any UI component or message you want to show
//            Text(
//                text = "Camera permission is required to use this feature",
//                color = Color.White,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun ARNavigationContent(context: android.content.Context) {
//    val arFragment = remember { ArFragment() }
//    var session: Session? by remember { mutableStateOf(null) }
//
//    // Start AR session
//    LaunchedEffect(Unit) {
//        try {
//            session = Session(context)
//            val config = Config(session).apply {
//                updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
//            }
//            session?.configure(config)
//        } catch (e: UnavailableException) {
//            // Handle ARCore not available on this device
//            Log.e("ARNavigation", "ARCore is not available on this device", e)
//        }
//    }
//
//    // Create an AnchorNode for rendering waypoints and routes
//    val anchorNode = remember { AnchorNode() }
//
//    // Render AR scene
//    Box(modifier = Modifier.fillMaxSize()) {
//        ArView(arFragment)
//        var ctx = LocalContext.current
//        val preferencesManager = remember { PreferencesManager(ctx) }
//        val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//        val currentLocation = LatLng(
//            preferencesManager.getData("latitude", "0.0").toDouble(),
//            preferencesManager.getData("longitude", "0.0").toDouble()
//        ) // Placeholder
//        val destinationLocation = LatLng(
//            preferencesManager.getData("destinationLocationLatitude", "0.0"),
//            preferencesManager.getData("destinationLocationLongitude", "0.0")
//        )
//        val route = pointsList // Placeholder
//        val waypoints = pointsList // Placeholder
//
//
//
//
//        var directionPath by remember {
//            mutableStateOf(listOf<PolyfillPoint>())
//        }
//        var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }
//
//                collectDirections(
//                    "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
//                    "${destinationLocationLatitude.toString()},${destinationLocationLongitude.toString()}"
//                ) { result ->
//                    directionPath = result
//                    val pointArray = arrayListOf<LatLng>()
//                    for (i in 0 until result.lastIndex) {
//                        pointArray.add(
//                            LatLng(
//                                result[i].startLocation.latitude,
//                                result[i].startLocation.longitude
//                            )
//                        )
//                    }
//                    pointsList = pointArray
//                    Log.d("---------cool", pointsList.lastIndex.toString())
//                }
//
//                Polyline(points = pointsList, color = Color.Black)
//            }
//
//            // Render waypoints
//            for (waypoint in waypoints) {
//                ArWaypoint(anchorNode = anchorNode, waypointLocation = waypoint)
//            }
//
//            // Render navigation route
//            ArRoute(anchorNode = anchorNode, route = route)
//
//            // Include CameraPreviewScreen composable here
//            CameraPreviewScreen()
//        }
//    }
//}
//
//@Composable
//fun ArView(arFragment: ArFragment) {
//    // AR View setup
//    // Placeholder content:
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text(text = "AR View", modifier = Modifier.padding(16.dp))
//    }
//}
//
//
//@Composable
//fun ArWaypoint(anchorNode: AnchorNode, waypointLocation: LatLng, context: Context) {
//    // Load 3D model for waypoint
//    val resourceId = R.raw.sphere
//    ModelRenderable.builder()
//        .setSource(context, resourceId)
//        .build()
//        .thenAccept { modelRenderable ->
//            // Create a node for the 3D model and attach it to the anchor node
//            val node = AnchorNode().apply {
//                setParent(anchorNode)
//                localPosition = Vector3(
//                    waypointLocation.latitude.toFloat(),
//                    0f,
//                    waypointLocation.longitude.toFloat()
//                )
//                renderable = modelRenderable
//            }
//            anchorNode.addChild(node)
//        }
//        .exceptionally { throwable ->
//            Log.e("ArWaypoint", "Error loading model: $throwable")
//            null
//        }
//}
//
//    @Composable
//    fun MapContent() {
//        val ctx = LocalContext.current
//        val preferencesManager = remember { PreferencesManager(ctx) }
//        var markerPosition by remember {
//            mutableStateOf(
//                LatLng(
//                    preferencesManager.getData("latitude", "0.0").toDouble(),
//                    preferencesManager.getData("longitude", "0.0").toDouble()
//                )
//            )
//        }
//        val cameraPositionState = rememberCameraPositionState {
//            position = CameraPosition.fromLatLngZoom(markerPosition, 10f)
//        }
//        val currentLocation = LatLng(
//            preferencesManager.getData("latitude", "0.0").toDouble(),
//            preferencesManager.getData("longitude", "0.0").toDouble()
//        )
//        val destinationLocationViewModel: DestinationLocationViewModel = viewModel()
//        val polyLineViewModel: PolyLineViewModel = viewModel()
//
//        var destinationLocationLatitude by remember {
//            mutableStateOf(preferencesManager.getData("destinationLocationLatitude", "0.0"))
//        }
//        var destinationLocationLongitude by remember {
//            mutableStateOf(preferencesManager.getData("destinationLocationLongitude", "0.0"))
//        }
//        var directionPath by remember {
//            mutableStateOf(listOf<PolyfillPoint>())
//        }
//        var pointsList by remember { mutableStateOf(arrayListOf<LatLng>()) }
//        GoogleMap(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 40.dp),
//            cameraPositionState = cameraPositionState
//        ) {
//            Marker(
//                state = MarkerState(position = currentLocation),
//                title = "Me",
//                snippet = "Marker in Big Ben"
//            )
//
//            if (destinationLocationLatitude.toDouble() != 0.0 && destinationLocationLongitude.toDouble() != 0.0) {
//                Marker(
//                    state = MarkerState(
//                        position = LatLng(
//                            destinationLocationLatitude.toDouble(),
//                            destinationLocationLongitude.toDouble()
//                        )
//                    ),
//                    title = "Destination",
//                    snippet = "Destination Position"
//                )
//                collectDirections(
//                    "${currentLocation.latitude.toString()},${currentLocation.longitude.toString()}",
//                    "${destinationLocationLatitude.toString()},${destinationLocationLongitude.toString()}"
//                ) { result ->
//                    directionPath = result
//                    val pointArray = arrayListOf<LatLng>()
//                    for (i in 0 until result.lastIndex) {
//                        pointArray.add(
//                            LatLng(
//                                result[i].startLocation.latitude,
//                                result[i].startLocation.longitude
//                            )
//                        )
//                    }
//                    pointsList = pointArray
//                    Log.d("---------cool", pointsList.lastIndex.toString())
//                }
//
//                Polyline(points = pointsList, color = Color.Black)
//            }
//        }
//    }
//
//    @Preview
//@Composable
//fun SplashScreen() {
//    Surface(color = Color.White) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            ARNavigationScreen()
//            MapContent()
//        }
//    }
//}
