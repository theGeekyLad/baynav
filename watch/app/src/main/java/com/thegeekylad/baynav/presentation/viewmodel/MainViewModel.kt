package com.thegeekylad.baynav.presentation.viewmodel

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.thegeekylad.baynav.presentation.model.Departure
import com.thegeekylad.baynav.presentation.model.Stop
import com.thegeekylad.baynav.presentation.nav.DeparturesDestination
import com.thegeekylad.baynav.presentation.util.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    val stopsList: MutableState<List<Stop>> = mutableStateOf(listOf())
    val departuresList: MutableState<List<Departure>> = mutableStateOf(listOf())
    val isApiCall = mutableStateOf(false)

    fun syncNearbyStops(
        onToast: (msg: String) -> Unit,
        onFinish: () -> Unit,
    ) {
        isApiCall.value = true
//        val fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(applicationContext)
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            viewModelScope.launch(Dispatchers.IO) {
                val res = Services.Helper.getNearbyStops(
                    "37.330640868974236",
                    "-121.90519826561415"
                )
//                val res = Services.Helper.getNearbyStops(
//                    location.latitude.toString(),
//                    location.longitude.toString(),
//                )
                if (res == null) {
                    withContext(Dispatchers.Main) {
                        onToast("You've been throttled!")
                        onFinish()
                    }
                } else {
                    stopsList.value = res
                }
                isApiCall.value = false
            }
//        }
    }

    fun syncDepartures(
        globalStopId: String,
        globalRouteId: String? = null,
        stopName: String,
        navController: NavController,
        onToast: (msg: String) -> Unit,
        onFinish: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            isApiCall.value = true
            val res = Services.Helper.getStopDepartures(globalStopId, globalRouteId)
            if (res == null) {
                withContext(Dispatchers.Main) {
                    onToast("You've been throttled!")
                    onFinish()
                }
            } else {
                departuresList.value = res
                withContext(Dispatchers.Main) {
                    navController.navigate(
                        "${DeparturesDestination().path}?" +
                                "global_stop_id=${globalStopId}&" +
                                "stop_name=${stopName}"
                    )
                }
            }
            isApiCall.value = false
        }
    }
}