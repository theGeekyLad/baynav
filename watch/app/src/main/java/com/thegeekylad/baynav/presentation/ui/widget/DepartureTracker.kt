package com.thegeekylad.baynav.presentation.ui.widget

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.google.android.horologist.compose.layout.fillMaxRectangle
import com.thegeekylad.baynav.presentation.util.Services
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import kotlin.math.absoluteValue

@Composable
fun DepartureTracker(
    countdown: Int,
    globalStopId: String,
    globalRouteId: String,
    stopName: String,
    routeName: String,
) {
    val coroutineScope = rememberCoroutineScope()
    val seconds = remember { mutableStateOf(countdown) }

    LaunchedEffect(key1 = 0) {
        coroutineScope.launch(Dispatchers.IO) {
            while (seconds.value > 0) {
                delay(1000)
                seconds.value = seconds.value - 1
            }
        }
        coroutineScope.launch(Dispatchers.IO) {
            while (true) {
                delay(30000)
                Log.d("VTA", "Polled for fresh time.")
                val res = Services.Helper.getStopDepartures(globalStopId, globalRouteId)
                if (res != null) {
                    val currDeparture = res[0].departureInterval.toInt()
                    if (currDeparture == seconds.value / 60)
                        Log.d("VTA", "Synced but M is the same. Not setting ...")
                    else {
                        Log.d("VTA", "Synced. New M! Updating countdown ...")
                        seconds.value = currDeparture * 60
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
//            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        TimeText()

        CircularProgressIndicator(
            progress = seconds.value / (countdown * 1f),
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            startAngle = -60f,
            endAngle = 240f,
            strokeWidth = 4.dp
        )

        Column(
            modifier = Modifier.fillMaxRectangle()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .weight(1f, true),
                text = routeName,
                style = MaterialTheme.typography.title3,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.weight(1f, true))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .weight(1f, true),
                text = stopName,
                style = MaterialTheme.typography.caption2,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (seconds.value / 60).toString().padStart(2, '0'),
                style = MaterialTheme.typography.display1,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = ":",
                style = MaterialTheme.typography.display1,
                textAlign = TextAlign.Center
            )
            Text(
                text = (seconds.value % 60).toString().padStart(2, '0'),
                style = MaterialTheme.typography.display1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )
        }
    }
}