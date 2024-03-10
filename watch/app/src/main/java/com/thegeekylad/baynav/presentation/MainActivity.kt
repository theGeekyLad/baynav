/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.thegeekylad.baynav.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgument
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
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
import com.thegeekylad.baynav.R
import com.thegeekylad.baynav.presentation.model.ChipPayload
import com.thegeekylad.baynav.presentation.model.Stop
import com.thegeekylad.baynav.presentation.nav.DeparturesDestination
import com.thegeekylad.baynav.presentation.nav.StopsDestination
import com.thegeekylad.baynav.presentation.theme.BaynavTheme
import com.thegeekylad.baynav.presentation.ui.widget.ChipsList
import com.thegeekylad.baynav.presentation.util.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaynavTheme {
                val navController = rememberSwipeDismissableNavController()
                val coroutineScope = rememberCoroutineScope()
                val stopsList: MutableState<List<Stop>> = mutableStateOf(listOf())

                // sync stops / location
                LaunchedEffect(key1 = navController) {
                    coroutineScope.launch(Dispatchers.IO) {
                        stopsList.value = Services.Helper.getNearbyStops("37.330640868974236", "-121.90519826561415")
                    }
                }

                SwipeDismissableNavHost(
                    navController = navController,
                    startDestination = StopsDestination().path
                ) {
                    composable(StopsDestination().path) {
                        ChipsList(
                            title = "Nearby Stops",
                            data = stopsList.value,
                            onClick = {
                                navController.navigate("${DeparturesDestination().path}?stop_name=${it.stopName}")
                            }
                        )
                    }

                    composable(
                        "${DeparturesDestination().path}?stop_name={stop_name}",
                        arguments = listOf(navArgument("stop_name") { })
                    ) {
                        ChipsList(
                            title = it.arguments?.getString("stop_name") ?: "Departures",
                            data = mutableListOf(
                                ChipPayload("The Alameda / Bush", "500", ""),
                                ChipPayload("The Alameda / J...", "326", ""),
                            ),
                            onClick = {
                                // TODO implement
                            }
                        )
                    }
                }
            }
        }
    }
}
