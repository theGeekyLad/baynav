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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListAnchorType
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.thegeekylad.baynav.R
import com.thegeekylad.baynav.presentation.theme.BaynavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("Android")
        }
    }
}

@Composable
fun WearApp(greetingName: String) {
    BaynavTheme {
        val listState = rememberScalingLazyListState()

        Scaffold(
            positionIndicator = {
                PositionIndicator(scalingLazyListState = listState)
            }
        ) {
            ScalingLazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Nearby Stops",
                        style = MaterialTheme.typography.button
                    )
                }

                item {
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        colors = ChipDefaults.secondaryChipColors(),
                        label = {
                            Text(text = "Santa Clara / 6th")
                        },
                        secondaryLabel = {
                            Text(text = "520m")
                        })
                }

                item {
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        colors = ChipDefaults.secondaryChipColors(),
                        label = {
                            Text(text = "Santa Clara / 6th")
                        },
                        secondaryLabel = {
                            Text(text = "520m")
                        })
                }

                item {
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        colors = ChipDefaults.secondaryChipColors(),
                        label = {
                            Text(text = "Santa Clara / 6th")
                        },
                        secondaryLabel = {
                            Text(text = "520m")
                        })
                }

                item {
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        colors = ChipDefaults.secondaryChipColors(),
                        label = {
                            Text(text = "Santa Clara / 6th")
                        },
                        secondaryLabel = {
                            Text(text = "520m")
                        })
                }

                item {
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /*TODO*/ },
                        colors = ChipDefaults.secondaryChipColors(),
                        label = {
                            Text(text = "Santa Clara / 6th")
                        },
                        secondaryLabel = {
                            Text(text = "520m")
                        })
                }
            }
        }
    }
}
