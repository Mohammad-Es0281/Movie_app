package com.sample.movie.presentation.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object ScreenAnimationSpecs {
    private const val FADING_ANIM_DURATION = 1000
    private const val SLIDING_ANIM_DURATION = 200

    val fadeIn = fadeIn(animationSpec = tween(FADING_ANIM_DURATION))

    val fadeOut = fadeOut(animationSpec = tween(FADING_ANIM_DURATION))

    val enterFromRight =
        slideInHorizontally(
            animationSpec = tween(durationMillis = SLIDING_ANIM_DURATION),
            initialOffsetX = { it }
        )

    val enterFromLeft =
        slideInHorizontally(
            animationSpec = tween(durationMillis = SLIDING_ANIM_DURATION),
            initialOffsetX = { -it }
        )

    val exitToLeft =
        slideOutHorizontally(
            animationSpec = tween(durationMillis = SLIDING_ANIM_DURATION),
            targetOffsetX = { -it }
        )

    val exitToRight =
        slideOutHorizontally(
            animationSpec = tween(durationMillis = SLIDING_ANIM_DURATION),
            targetOffsetX = { it }
        )
}

object ScreenTransitions : DestinationStyle.Animated {
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition {
        return ScreenAnimationSpecs.enterFromRight
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return ScreenAnimationSpecs.exitToLeft
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition {
        return ScreenAnimationSpecs.enterFromLeft
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition {
        return ScreenAnimationSpecs.exitToRight
    }
}