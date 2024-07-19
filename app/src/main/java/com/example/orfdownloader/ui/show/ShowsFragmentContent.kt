package com.example.orfdownloader.ui.show

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orfdownloader.data.ShowDetails
import com.example.orfdownloader.util.DateUtil
import kotlinx.coroutines.delay

/*
ShowsFragmentContent is a composable function that takes a ShowsViewModel as a parameter.
 */
@Composable
fun ShowsFragmentContent(showsViewModel: ShowsViewModel, playShow: (ShowDetails) -> Unit) {

    val shows = showsViewModel.showDetails.observeAsState()

    Column {
        val days = shows.value?.keys?.toList() ?: emptyList()
        var selectedDay by remember { mutableStateOf(-1) }

        var counter by remember { mutableStateOf(0) }

        val showsList = shows.value?.get(selectedDay) ?: emptyList()

        // Increments counter for animation
        LaunchedEffect(showsList) {
            counter = 0
            while(counter < showsList.size) {
                delay(50)
                counter++
            }
        }

        LaunchedEffect(days) {
            selectedDay = days.firstOrNull() ?: -1
        }
        LazyRow {
            items(days) { item ->
                DayCard(dayInt = item, modifier = Modifier.clickable { selectedDay = item })
            }
        }
        LazyColumn {
            itemsIndexed(showsList) { index, show ->
                AnimatedVisibility(visible = index < counter, exit = ExitTransition.None) {
                    ShowCard(
                        showDetails = show,
                        modifier = Modifier.clickable { playShow(show) })
                }

            }
        }
    }
}

@Composable
private fun DayCard(dayInt: Int, modifier: Modifier = Modifier) {
    val date =
        DateUtil.getDate(dayInt.toString(), DateUtil.DateFormat.RAWSHOWDATE)

    Card(modifier.padding(8.dp)) {
        Text(
            text = DateUtil.convertDate(date, DateUtil.DateFormat.NICESHOWDATE),
            modifier = Modifier.padding(8.dp),
            textAlign = Center
        )
    }
}

@Composable
private fun ShowCard(showDetails: ShowDetails, modifier: Modifier = Modifier) {
    val displayString = "%1s \t %2s"
    val timeString = DateUtil.convertDate(showDetails.startTime, DateUtil.DateFormat.NICETIME)
    val displayText = displayString.format(timeString, showDetails.showTitle)
    Card(
        backgroundColor = androidx.compose.ui.graphics.Color.LightGray,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = displayText,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
@Preview
fun DayCardPreview() {
    DayCard(20210101)
}