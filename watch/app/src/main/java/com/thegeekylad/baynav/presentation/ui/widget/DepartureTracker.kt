package com.thegeekylad.baynav.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.google.android.horologist.compose.layout.fillMaxRectangle

@Composable
fun DepartureTracker() {
    Box(
        modifier = Modifier
//            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        TimeText()

        CircularProgressIndicator(
            progress = 0.5f,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            startAngle = -60f,
            endAngle = 240f
        )

        Column(
            modifier = Modifier
                .fillMaxRectangle()
                .padding(top = 12.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "22 - Palo Alto",
                style = MaterialTheme.typography.title3,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = "Santa Clara / 5th",
                style = MaterialTheme.typography.caption2,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "20",
                    style = MaterialTheme.typography.display1,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.padding(top = 22.dp),
                    text = "min",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}