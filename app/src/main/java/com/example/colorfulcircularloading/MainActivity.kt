package com.example.colorfulcircularloading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.colorfulcircularloading.ui.theme.ColorfulCircularLoadingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorfulCircularLoadingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LoadingAnimation()
                    }
                }
            }
        }
    }
}


@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color(0xFFF4B400),
        Color(0xFF0F9D58),
        Color(0xFFDB4437),
        Color(0xFF4285F4)
    ),
    strokeWidth: Dp = 4.dp
) {
    val expansionDuration by remember { mutableStateOf(700) }
    val infiniteTransition = rememberInfiniteTransition()


    val currentColorIndex by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = colors.size,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Restart,
            animation = tween(
                durationMillis = 2*expansionDuration*colors.size,
                easing = LinearEasing
            )
        )
    )

    val progress by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Reverse,
            animation = tween(
                durationMillis = expansionDuration,
                easing = LinearEasing
            )
        )
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Restart,
            animation = tween(
                durationMillis = expansionDuration,
                easing = LinearEasing
            )
        )
    )

    CircularProgressIndicator(
        modifier = modifier
            .rotate(rotation),
        progress = progress,
        color = colors[currentColorIndex],
        strokeWidth = strokeWidth
    )
}