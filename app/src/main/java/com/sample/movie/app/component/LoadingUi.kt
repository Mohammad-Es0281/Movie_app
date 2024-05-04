package com.sample.movie.app.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.sample.movie.R
import com.sample.movie.app.theme.AppColors

@Composable
fun LoadingUi(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading_four_square))
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = AppColors.primary.toArgb(),
            keyPath = arrayOf(
                "Rectangle_1",
                "Rectangle 1",
                "Fill 1",
            ),
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = AppColors.secondary.toArgb(),
            keyPath = arrayOf(
                "Rectangle_2",
                "Rectangle 1",
                "Fill 1",
            ),
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = AppColors.primary.toArgb(),
            keyPath = arrayOf(
                "Rectangle_3",
                "Rectangle 1",
                "Fill 1",
            ),
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = AppColors.secondary.toArgb(),
            keyPath = arrayOf(
                "Rectangle_4",
                "Rectangle 1",
                "Fill 1",
            ),
        ),
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        dynamicProperties = dynamicProperties,
    )
}