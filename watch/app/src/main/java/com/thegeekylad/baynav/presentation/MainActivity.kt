/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.thegeekylad.baynav.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgument
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListAnchorType
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.android.gms.location.LocationServices
import com.thegeekylad.baynav.R
import com.thegeekylad.baynav.presentation.model.ChipPayload
import com.thegeekylad.baynav.presentation.model.Departure
import com.thegeekylad.baynav.presentation.model.Stop
import com.thegeekylad.baynav.presentation.nav.DeparturesDestination
import com.thegeekylad.baynav.presentation.nav.StopsDestination
import com.thegeekylad.baynav.presentation.nav.TrackerDestination
import com.thegeekylad.baynav.presentation.theme.BaynavTheme
import com.thegeekylad.baynav.presentation.ui.widget.ChipsList
import com.thegeekylad.baynav.presentation.ui.widget.DepartureTracker
import com.thegeekylad.baynav.presentation.util.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

@ExperimentalWearFoundationApi
class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaynavTheme {
                val navController = rememberSwipeDismissableNavController()
                val coroutineScope = rememberCoroutineScope()
                val stopsList: MutableState<List<Stop>> = remember { mutableStateOf(listOf()) }
                val departuresList: MutableState<List<Departure>> =
                    remember { mutableStateOf(listOf()) }
                val isApiCall = remember { mutableStateOf(false) }
                val keyOnce = 0

                // sync stops / location
                LaunchedEffect(key1 = keyOnce) {
                    isApiCall.value = true
                    val fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(applicationContext)
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                        coroutineScope.launch(Dispatchers.IO) {
//                        val res = Services.Helper.getNearbyStops(
//                            "37.330640868974236",
//                            "-121.90519826561415"
//                        )
                            val res = Services.Helper.getNearbyStops(
                                location.latitude.toString(),
                                location.longitude.toString(),
                            )
                            if (res == null) {
                                withContext(Dispatchers.Main) {
                                    toastThrottledStatus()
                                    finish()
                                }
                            } else {
                                stopsList.value = res
                            }
                            isApiCall.value = false
                        }
                    }
                }

                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = StopsDestination().path
                ) {
                    composable(StopsDestination().path) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                        ChipsList(
                            title = "Nearby Stops",
                            data = stopsList.value,
                            onClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    isApiCall.value = true
                                    val res = Services.Helper.getStopDepartures(it.globalStopId, null)
                                    if (res == null) {
                                        withContext(Dispatchers.Main) {
                                            toastThrottledStatus()
//                                            navController.popBackStack()
                                        }
                                    } else {
                                        departuresList.value = res
                                        withContext(Dispatchers.Main) {
                                            navController.navigate("${DeparturesDestination().path}?global_stop_id=${it.globalStopId}&stop_name=${it.stopName}")
                                        }
                                    }
                                    isApiCall.value = false
                                }
                            }
                        )
                    }

                    composable(
                        "${DeparturesDestination().path}?global_stop_id={global_stop_id}&stop_name={stop_name}",
                        arguments = listOf(
                            navArgument("global_stop_id") { },
                            navArgument("stop_name") { }
                        )
                    ) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                        ChipsList(
                            title = it.arguments?.getString("stop_name") ?: "Departures",
                            data = departuresList.value,
                            onClick = { departure ->
                                navController.navigate("${TrackerDestination().path}?" +
                                        "countdown=${departure.departureInterval}&" +
                                        "global_stop_id=${it.arguments!!.getString("global_stop_id")}&" +
                                        "route_name=${departure.routeShortName} - ${departure.directionHeadsign}&" +
                                        "stop_name=${it.arguments?.getString("stop_name")}&" +
                                        "global_route_id=${departure.globalRouteId}")
                            }
                        )
                    }

                    composable(
                        "${TrackerDestination().path}?countdown={countdown}&global_stop_id={global_stop_id}&global_route_id={global_route_id}&route_name={route_name}&stop_name={stop_name}",
                        arguments = listOf(
                            navArgument("countdown") { },
                            navArgument("global_stop_id") { },
                            navArgument("global_route_id") { },
                            navArgument("route_name") { },
                            navArgument("stop_name") { },
                        )
                    ) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                        DepartureTracker(
                            countdown = it.arguments!!.getString("countdown")!!.toInt() * 60,
                            globalStopId = it.arguments!!.getString("global_stop_id")!!,
                            globalRouteId = it.arguments!!.getString("global_route_id")!!,
                            stopName = it.arguments!!.getString("stop_name")!!,
                            routeName = it.arguments!!.getString("route_name")!!,
                        )
                    }
                }

                // spin on api calls
                if (isApiCall.value) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize(),
                            indicatorColor = MaterialTheme.colors.primary,
                            strokeWidth = 4.dp
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            text = "Syncing...",
                            style = MaterialTheme.typography.caption2
                        )
                    }
                }
            }
        }
    }

    private fun toastThrottledStatus() {
        Toast.makeText(applicationContext, "You've been throttled!", Toast.LENGTH_SHORT).show()
    }
}
