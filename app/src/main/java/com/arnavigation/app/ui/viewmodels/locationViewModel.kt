package com.arnavigation.app.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationViewModel(private val savedStateHandle: SavedStateHandle):ViewModel(){
    private var _latitude = mutableStateOf(0.0)
    private var _longitude = mutableStateOf(0.0)

    fun setLocation(lat:Double, long:Double){
        _latitude.value = lat
        _longitude.value = long
    }

    fun getLatitude():Double{
        return _latitude.value
    }
    fun getLongitude():Double{
        return _longitude.value
    }

}



class DestinationLocationViewModel(private val savedStateHandle: SavedStateHandle):ViewModel(){
    private var _latitude = mutableStateOf(0.0)
    private var _longitude = mutableStateOf(0.0)

    fun setLocation(lat:Double, long:Double){
        _latitude.value = lat
        _longitude.value = long
    }

    fun getLatitude():Double{
        return _latitude.value
    }
    fun getLongitude():Double{
        return _longitude.value
    }

}

class PolyLineViewModel():ViewModel(){
    private var _pointsList = mutableStateOf(arrayListOf<LatLng>())
    fun setPoints(points:ArrayList<LatLng>){
        _pointsList.value = points
    }
    fun getPoints():ArrayList<LatLng>{
        return _pointsList.value
    }
}