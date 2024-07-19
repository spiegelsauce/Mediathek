package com.example.orfdownloader.ui.station

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orfdownloader.data.RadioStations
import com.example.orfdownloader.ui.compose.StringResources
import com.example.orfdownloader.ui.compose.headerStyle

@Composable
fun StationList(stationList: List<RadioStations>, onClick: (RadioStations) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = StringResources.STATION_CHOOSER_TITLE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            style = headerStyle
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            items(stationList) { station ->
                StationView(station, modifier = Modifier.clickable(onClick = {
                    onClick(station)
                }))
            }
        }
    }
}


@Composable
fun StationView(station: RadioStations, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = station.icon),
                contentDescription = "Station Icon",
                modifier = Modifier
                    .size(100.dp)
                    .padding(12.dp)
            )
            Text(
                text = station.humanFriendlyName,
                modifier = Modifier
                    .padding(12.dp),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StationViewPreview() {
    StationView(RadioStations.FM4)
}
