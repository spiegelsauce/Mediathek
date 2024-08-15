package com.example.orfdownloader.ui.player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.orfdownloader.R

@Composable
fun RadioWithWaves() {
    val infiniteTransition = rememberInfiniteTransition(label = "transition")

    val wave1Radius by infiniteTransition.animateFloat(
        initialValue = 100f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "wave1"
    )

    val waveScale by infiniteTransition.animateFloat(
        initialValue = 2.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "waveScale"
    )

    val wave2Scale by infiniteTransition.animateFloat(
        initialValue = 2.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(200)
        ), label = "waveScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.radio1), // Your radio image resource
            contentDescription = "Radio",
            modifier = Modifier.size(100.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 25.dp, y = (-25).dp)
                .fillMaxSize()
                .scale(waveScale)
                .border(2.dp, Color.Red, CircleShape)
        )

        Box (
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 25.dp, y = (-25).dp)
                .fillMaxSize()
                .scale(wave2Scale)
                .border(2.dp, Color.Red, CircleShape)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RadioWithWavesPreview() {
    RadioWithWaves()
}
