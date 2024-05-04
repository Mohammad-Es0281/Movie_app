package com.sample.movie.app.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sample.movie.R
import com.sample.movie.app.exception.UiException
import com.sample.movie.app.theme.AppTypography
import com.sample.movie.app.theme.MovieTheme

@Composable
fun ErrorUi(
    modifier: Modifier = Modifier,
    exception: UiException,
    onRetry: (() -> Unit)? = null,
) {
    val context = LocalContext.current

    when (exception) {
        is UiException.ClientOffline -> {
            BaseErrorUi(
                modifier = modifier,
                errorAnim = R.raw.anim_error_disconnected_wifi_circle,
                message = exception.message
                    ?: context.getString(R.string.gl_error_network_unavailable),
                onRetry = onRetry
            )
        }

        is UiException.ServerConnectionFailed -> {
            BaseErrorUi(
                modifier = modifier,
                errorAnim = R.raw.anim_error_connection_lost,
                message = exception.message ?: context.getString(R.string.gl_error_server_connection_failed),
                onRetry = onRetry,
                iterations = 1
            )
        }

        is UiException.LowStorage -> { /* TODO */ }

        is UiException.Unknown -> {
            BaseErrorUi(
                modifier = modifier,
                errorAnim = R.raw.anim_error_caution_circle,
                message = exception.message ?: context.getString(R.string.gl_error_unknown),
                onRetry = onRetry
            )
        }
    }
}

@Composable
private fun BaseErrorUi(
    message: String,
    @RawRes errorAnim: Int,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    iterations: Int = LottieConstants.IterateForever
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(errorAnim))

        LottieAnimation(
            modifier = Modifier
                .fillMaxSize(0.2F)
                .aspectRatio(1.0F, matchHeightConstraintsFirst = true),
            composition = composition,
            iterations = iterations,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = AppTypography.titleLarge,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        onRetry?.let { onRetry ->
            Button(onClick = { onRetry.invoke() }) {
                Text(
                    text = stringResource(id = R.string.gl_try_again),
                    style = AppTypography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UnknownErrorUiPreview() {
    MovieTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorUi(
                modifier = Modifier.fillMaxSize(),
                exception = UiException.Unknown()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ClientOfflineErrorUiPreview() {
    MovieTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorUi(
                modifier = Modifier.fillMaxSize(),
                exception = UiException.ClientOffline(),
                onRetry = { }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerConnectionFailedErrorUiPreview() {
    MovieTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorUi(
                modifier = Modifier.fillMaxSize(),
                exception = UiException.ServerConnectionFailed(),
                onRetry = { }
            )
        }
    }
}