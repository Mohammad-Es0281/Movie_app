package com.sample.movie.presentation.main.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.sample.movie.presentation.util.ScreenTransitions
import com.sample.movie.provider.NavigationProvider
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.sample.movie.R
import com.sample.movie.app.architecture.mvi.UiState
import com.sample.movie.app.component.BaseScreen
import com.sample.movie.app.component.ErrorUi
import com.sample.movie.app.component.LoadingUi
import com.sample.movie.app.exception.asUiException
import com.sample.movie.app.extension.cast
import com.sample.movie.app.extension.shimmer
import com.sample.movie.app.theme.AppColors
import com.sample.movie.app.theme.AppShapes
import com.sample.movie.app.theme.AppTypography
import com.sample.movie.presentation.home.HomeEvent
import com.sample.movie.presentation.home.HomeUiState
import com.sample.movie.presentation.model.MovieItemModel
import com.sample.movie.presentation.util.GOLDEN_RATIO

@RootNavGraph(start = true)
@Destination(style = ScreenTransitions::class)
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: NavigationProvider
) {
    HomeScreen(
        uiState = viewModel.uiState.cast(),
        onMovieItemClicked = {
            navigator.navigateToMovieDetail(it.id)
        },
        onFailure = {
            viewModel.onTriggerEvent(HomeEvent.GetInformation)
        }
    )
}

@Composable
private fun HomeScreen(
    uiState: UiState<HomeUiState>,
    onMovieItemClicked: (MovieItemModel) -> Unit,
    onFailure: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopAppBar(scrollBehavior)
        }
    ) { paddingValues ->
        BaseScreen(
            state = uiState,
            content = { successState ->
                Content(
                    modifier = Modifier.padding(paddingValues),
                    state = successState.data,
                    onMovieItemClicked = onMovieItemClicked,
                    onFailure = onFailure
                )
            },
            onRetry = onFailure
        )
    }
}

@Composable
private fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColors.primary,
            scrolledContainerColor = AppColors.primary
        ),
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground_witout_padding),
                    contentDescription = stringResource(id = R.string.app_name),
                    tint = AppColors.onPrimary
                )

                Text(
                    text = stringResource(id = R.string.app_name),
                    color = AppColors.onPrimary,
                    style = AppTypography.headlineMedium
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun Content(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onMovieItemClicked: (MovieItemModel) -> Unit,
    onFailure: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagingMovieItems = state.movieItems.collectAsLazyPagingItems()

        if (pagingMovieItems.loadState.refresh is LoadState.Loading)
            LoadingUi(modifier = Modifier.fillMaxSize(0.5F))
        else if (pagingMovieItems.loadState.refresh is LoadState.Error && pagingMovieItems.itemCount == 0)
            ErrorUi(
                modifier = Modifier.fillMaxSize(),
                exception = pagingMovieItems.loadState.refresh.cast<LoadState.Error>().error.asUiException(),
                onRetry = onFailure
            )
        else
            MovieList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0F),
                movieItems = pagingMovieItems,
                onMovieItemClicked = onMovieItemClicked,
            )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieList(
    movieItems: LazyPagingItems<MovieItemModel>,
    modifier: Modifier = Modifier,
    onMovieItemClicked: (MovieItemModel) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 128.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(24.dp),
    ) {
        items(
            count = movieItems.itemCount,
            key = movieItems.itemKey { item -> item.order }
        ) { index ->
            movieItems[index]?.let { movieItem ->
                MovieItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateItemPlacement(),
                    movieItem = movieItem,
                    onMovieItemClicked = onMovieItemClicked
                )
            }
        }

        item {
            if (movieItems.loadState.append is LoadState.Loading) {
                MovieItemLoadingPlaceholder(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateItemPlacement()
                )
            } else if (
                movieItems.loadState.append is LoadState.Error ||
                movieItems.loadState.refresh is LoadState.Error && movieItems.itemCount != 0
            ) {
                MovieItemErrorPlaceholder(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateItemPlacement(),
                    onRetry = { movieItems.retry() }
                )
            }
        }
    }
}

@Composable
private fun MovieItem(
    movieItem: MovieItemModel,
    modifier: Modifier = Modifier,
    onMovieItemClicked: (MovieItemModel) -> Unit
) {
    Column(
        modifier = modifier
            .clip(AppShapes.small)
            .clickable { onMovieItemClicked(movieItem) }
            .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.movieItemImageStyle(),
            model = movieItem.imageUrl,
            contentDescription = movieItem.title,
            contentScale = ContentScale.FillBounds,
        )

        Text(
            modifier = Modifier.fillMaxWidth(0.8F),
            style = AppTypography.titleMedium,
            text = movieItem.title,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
private fun Modifier.movieItemImageStyle() = this
    .aspectRatio(1 / GOLDEN_RATIO)
    .clip(AppShapes.small)
    .border(2.dp, AppColors.primary, AppShapes.small)


@Composable
private fun MovieItemLoadingPlaceholder(
    modifier: Modifier = Modifier,
) {
    var columnHeightPx by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .movieItemImageStyle()
            .shimmer(offsetValue = columnHeightPx * 5, 2000)
            .onGloballyPositioned { coordinates ->
                columnHeightPx = coordinates.size.height.toFloat()
            },
    )
}

@Composable
private fun MovieItemErrorPlaceholder(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .movieItemImageStyle()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.sc_home_screen_error_in_append),
            style = AppTypography.titleMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onRetry) {
            Text(
                text = stringResource(id = R.string.gl_try_again),
                style = AppTypography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}