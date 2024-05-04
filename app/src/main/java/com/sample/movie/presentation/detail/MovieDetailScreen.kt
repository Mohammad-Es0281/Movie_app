package com.sample.movie.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.sample.movie.R
import com.sample.movie.app.architecture.mvi.UiState
import com.sample.movie.app.component.BaseScreen
import com.sample.movie.app.extension.cast
import com.sample.movie.app.theme.AppColors
import com.sample.movie.app.theme.AppShapes
import com.sample.movie.app.theme.AppTypography
import com.sample.movie.app.theme.MovieTheme
import com.sample.movie.app.theme.onBackgroundLowOpacity
import com.sample.movie.data.local.model.AppTheme
import com.sample.movie.data.local.model.DarkMode
import com.sample.movie.presentation.model.MovieLengthModel
import com.sample.movie.presentation.util.GOLDEN_RATIO
import com.sample.movie.presentation.util.ScreenTransitions
import com.sample.movie.presentation.model.MovieDetailModel
import com.sample.movie.presentation.model.MovieGenreModel

@Destination(style = ScreenTransitions::class)
@Composable
fun MovieDetailRoute(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    movieId: Long
) {
    MovieDetailScreen(
        uiState = viewModel.uiState.cast(),
        onRetry = {
            viewModel.onTriggerEvent(MovieDetailEvent.GetInformation(movieId))
        }
    )
}

@Composable
private fun MovieDetailScreen(
    uiState: UiState<MovieDetailUiState>,
    onRetry: () -> Unit
) {
    Scaffold { paddingValues ->
        BaseScreen(
            modifier = Modifier.padding(paddingValues),
            state = uiState,
            onRetry = onRetry,
            content = { successState ->
                Content(uiState = successState.data)
            }
        )
    }
}

@Composable
private fun Content(
    uiState: MovieDetailUiState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ImageSection(movie = uiState.movie, scrollState = scrollState)

        InfoSection(movie = uiState.movie)
    }
}

@Composable
private fun ImageSection(
    movie: MovieDetailModel,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    val imagePainter: Painter = rememberAsyncImagePainter(model = movie.image)

    val color = AppColors.scrim
    val linearShadowColor = remember(color) {
        Brush.verticalGradient(
            listOf(
                color.copy(alpha = 0.65F),
                color.copy(alpha = 0.75F),
                color.copy(alpha = 0.95F),
            )
        )
    }

    Column(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .paint(
                painter = imagePainter,
                sizeToIntrinsics = false,
                contentScale = ContentScale.FillWidth
            )
            .background(linearShadowColor)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .offset { IntOffset(y = 4 * scrollState.value / 3, x = 0) }
                .height(168.dp)
                .aspectRatio(1 / GOLDEN_RATIO),
            painter = imagePainter,
            contentDescription = movie.name,
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        Genres(
            modifier = Modifier.offset { IntOffset(y = 2 * scrollState.value, x = 0) },
            genres = movie.genres
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun Genres(modifier: Modifier = Modifier, genres: List<MovieGenreModel>) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(genres.size) { index ->
            Text(
                modifier = Modifier
                    .clip(AppShapes.extraLarge)
                    .background(
                        color = AppColors.primary.copy(alpha = 0.75F),
                        shape = AppShapes.extraLarge
                    )
                    .padding(vertical = 4.dp, horizontal = 16.dp),
                text = genres[index].name,
                style = AppTypography.labelMedium,
                color = AppColors.onPrimary
            )
        }
    }
}

@Composable
private fun InfoSection(
    movie: MovieDetailModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(AppColors.scrim)
            .background(
                color = AppColors.background,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = modifier,
            text = movie.name,
            style = AppTypography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Rating(rate = movie.voteAverage)

        Spacer(modifier = Modifier.height(16.dp))

        MainInfoRow(
            modifier = Modifier.fillMaxWidth(),
            length = movie.length,
            language = movie.language
        )

        Spacer(modifier = Modifier.height(24.dp))

        Description(description = movie.description)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Rating(rate: Float, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.ic_star),
            tint = Color.Unspecified,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = stringResource(R.string.sc_movie_detail_imdb_rating, rate.toString()),
            style = AppTypography.labelMedium,
            color = AppColors.onBackgroundLowOpacity
        )
    }
}

@Composable
private fun Description(description: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.sc_movie_detail_description_title),
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Justify,
            color = AppColors.onBackgroundLowOpacity
        )
    }
}

@Composable
private fun MainInfoRow(
    length: MovieLengthModel,
    language: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainInfoItem(
            title = stringResource(R.string.sc_movie_detail_length_title),
            value = stringResource(
                R.string.sc_movie_detail_length_formatted,
                length.hour.toString(),
                length.minute.toString()
            )
        )

        Spacer(modifier = Modifier.width(32.dp))

        MainInfoItem(
            title = stringResource(R.string.sc_movie_detail_length_language),
            value = language
        )
    }
}

@Composable
private fun MainInfoItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = AppTypography.labelMedium,
            color = AppColors.onBackgroundLowOpacity
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = AppTypography.labelMedium,
            color = AppColors.onBackground,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showSystemUi = true)
@PreviewScreenSizes
@PreviewLightDark
@PreviewFontScale
@Composable
private fun MovieDetailPreview() {
    MovieTheme(
        appTheme = AppTheme.Default,
        darkMode = DarkMode.Off
    ) {
        MovieDetailScreen(
            uiState = UiState.Success(
                MovieDetailUiState(
                    MovieDetailModel(
                        0,
                        "Kung Fu Panda Collection",
                        voteAverage = 9.1F,
                        description = "Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.",
                        length = MovieLengthModel(hour = 1, minute = 28),
                        language = "English",
                        genres = listOf(
                            MovieGenreModel(1, "ACTION"),
                            MovieGenreModel(1, "ACTION"),
                            MovieGenreModel(1, "ACTION"),
                            MovieGenreModel(1, "ACTION"),
                            MovieGenreModel(1, "ACTION"),
                        ),
                        image = ""
                    ),
                )
            ),
            onRetry = {}
        )
    }
}