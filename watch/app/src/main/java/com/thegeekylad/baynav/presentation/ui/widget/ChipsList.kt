package com.thegeekylad.baynav.presentation.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListAnchorType
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.android.horologist.compose.layout.fillMaxRectangle
import com.thegeekylad.baynav.presentation.model.ChipPayload
import com.thegeekylad.baynav.presentation.model.Departure
import com.thegeekylad.baynav.presentation.model.Stop
import com.thegeekylad.baynav.presentation.theme.BaynavTheme
import kotlinx.coroutines.launch
import java.time.Instant

@ExperimentalWearFoundationApi
@Composable
fun <T> ChipsList(
    title: String,
    data: List<T>,
    onClick: (payload: T) -> Unit,
) {
    val listState = rememberScalingLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        positionIndicator = {
            PositionIndicator(scalingLazyListState = listState)
        }
    ) {
        ScalingLazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .onRotaryScrollEvent {
                    coroutineScope.launch {
                        listState.scrollBy(it.verticalScrollPixels)
                        listState.animateScrollBy(0f)
                    }
                    true
                }
                .focusRequester(focusRequester)
                .focusable(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            anchorType = ScalingLazyListAnchorType.ItemStart
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxRectangle(),
                    text = title,
                    style = MaterialTheme.typography.button,
                    textAlign = TextAlign.Center,
                )
            }

            data.forEach {
                item {
                    Chip(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onClick(it) },
                        colors = ChipDefaults.secondaryChipColors(),
                        label = {
                            Text(
                                text = when (it) {
                                    is Stop -> it.stopName
                                    is Departure -> "${it.routeShortName} - ${it.directionHeadsign}"
                                    else -> ""
                                },
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        secondaryLabel = {
                            Text(
                                text = when (it) {
                                    is Stop -> "${it.distance}m"
                                    is Departure -> "${it.departureInterval}min"
                                    else -> ""
                                },
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    )
                }
            }
        }
    }
}