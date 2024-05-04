package com.sample.movie.app.extension

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

inline fun Modifier.runIf(
    value: Boolean,
    block: Modifier.() -> Modifier
): Modifier =
    if (value) this.block() else this

@Composable
fun Modifier.shimmer(offsetValue: Float = 1000f, durationMillis: Int = 1000): Modifier =
    this.background(shimmerBrush(offsetValue, durationMillis))

@Composable
private fun shimmerBrush(offsetValue: Float = 1000f, durationMillis: Int = 1000): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.5f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.5f),
    )

    val transition = rememberInfiniteTransition(label = "Shimmer Effect Transition")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = offsetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis), repeatMode = RepeatMode.Restart
        ),
        label = "Shimmer Effect Translation"
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
}