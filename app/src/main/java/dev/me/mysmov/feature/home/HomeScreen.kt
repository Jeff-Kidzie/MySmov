package dev.me.mysmov.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieCategory
import dev.me.mysmov.domain.model.TvCategory
import dev.me.mysmov.domain.model.label
import dev.me.mysmov.ui.component.MovieBannerCard
import dev.me.mysmov.ui.component.MovieItem
import org.koin.androidx.compose.koinViewModel

private val movieCategories = listOf(
    MovieCategory.Popular,
    MovieCategory.NowPlaying,
    MovieCategory.Upcoming,
    MovieCategory.TopRated,
)

private val tvCategories = listOf(
    TvCategory.Popular,
    TvCategory.OnTheAir,
    TvCategory.TopRated,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetail: (Int) -> Unit = {},
) {
    val state by viewModel.viewState.collectAsState()
    val onClickItem = { id: Int -> viewModel.onAction(HomeAction.OnClickMovie(id)) }

    LaunchedEffect(Unit) {
        viewModel.onAction(HomeAction.InitPage)
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowDetailMovie -> onNavigateToDetail(effect.id)
            }
        }
    }

    val searchQuery = remember { mutableStateOf("") }

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.onAction(HomeAction.RefreshPage) },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { Header() }

            item {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = searchQuery.value,
                    onQueryChange = { searchQuery.value = it },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text("Movies, actors, or genres...") },
                ) {}
            }

            // Media type chips
            item {
                MediaTypeChips(
                    selected = state.selectedMediaType,
                    onSelect = { viewModel.onAction(HomeAction.SwitchMediaType(it)) },
                )
            }

            // Featured banner (Now Playing / Airing Today)
            item {
                val featuredLabel = if (state.selectedMediaType == MediaType.Movie) "Now Playing" else "Airing Today"
                SectionFeatured(
                    label = featuredLabel,
                    items = state.featuredItems,
                    isLoading = state.isLoadingFeatured,
                    onClickItem = onClickItem,
                )
            }

            // Category chips
            item {
                if (state.selectedMediaType == MediaType.Movie) {
                    MovieCategoryChips(
                        selected = state.selectedMovieCategory,
                        onSelect = { viewModel.onAction(HomeAction.SelectMovieCategory(it)) },
                    )
                } else {
                    TvCategoryChips(
                        selected = state.selectedTvCategory,
                        onSelect = { viewModel.onAction(HomeAction.SelectTvCategory(it)) },
                    )
                }
            }

            // Category items with lazy loading
            item {
                CategoryItemsRow(
                    items = state.categoryItems,
                    isLoading = state.isLoadingCategory,
                    isLoadingMore = state.isLoadingMore,
                    onClickItem = onClickItem,
                    onReachedEnd = {
                        if (!state.isLoadingMore && !state.isLoadingCategory && state.canLoadMore) {
                            viewModel.onAction(HomeAction.LoadMoreCategoryItems)
                        }
                    },
                )
            }

            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun CategoryItemsRow(
    items: List<MediaItem>,
    isLoading: Boolean,
    isLoadingMore: Boolean,
    onClickItem: (Int) -> Unit,
    onReachedEnd: () -> Unit,
) {
    val lazyRowState = rememberLazyListState()

    val reachedEnd by remember(lazyRowState) {
        derivedStateOf {
            val lastVisible = lazyRowState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
            lastVisible >= lazyRowState.layoutInfo.totalItemsCount - 3
        }
    }

    LaunchedEffect(reachedEnd) {
        if (reachedEnd && items.isNotEmpty()) onReachedEnd()
    }

    LazyRow(
        state = lazyRowState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 16.dp),
    ) {
        if (isLoading && items.isEmpty()) {
            items(5) {
                MovieItem(
                    id = 0,
                    imageUrl = "",
                    title = "",
                    subtitle = "",
                    rating = null,
                    isLoading = true,
                    onClick = {},
                )
            }
        } else {
            items(items) { item ->
                MovieItem(
                    id = item.id,
                    imageUrl = item.posterPath,
                    title = item.title,
                    subtitle = item.releaseDate,
                    rating = item.rating,
                    isLoading = false,
                    onClick = onClickItem,
                )
            }
            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(56.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MediaTypeChips(
    selected: MediaType,
    onSelect: (MediaType) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = selected == MediaType.Movie,
            onClick = { onSelect(MediaType.Movie) },
            label = { Text("Movies") },
        )
        FilterChip(
            selected = selected == MediaType.TvShow,
            onClick = { onSelect(MediaType.TvShow) },
            label = { Text("TV Shows") },
        )
    }
}

@Composable
private fun MovieCategoryChips(
    selected: MovieCategory,
    onSelect: (MovieCategory) -> Unit,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movieCategories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text(category.label()) },
            )
        }
    }
}

@Composable
private fun TvCategoryChips(
    selected: TvCategory,
    onSelect: (TvCategory) -> Unit,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(tvCategories) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text(category.label()) },
            )
        }
    }
}

@Composable
private fun SectionFeatured(
    label: String,
    items: List<MediaItem>,
    isLoading: Boolean,
    onClickItem: (Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp),
        ) {
            if (isLoading && items.isEmpty()) {
                items(3) {
                    MovieBannerCard(
                        modifier = Modifier.width(320.dp),
                        id = 0,
                        imageUrl = "",
                        title = "",
                        rating = 0.0,
                        isLoading = true,
                        onWatchNowClick = {},
                    )
                }
            } else {
                items(items) { item ->
                    MovieBannerCard(
                        modifier = Modifier.width(320.dp),
                        id = item.id,
                        imageUrl = item.backdropPath,
                        title = item.title,
                        rating = item.rating,
                        isLoading = false,
                        onWatchNowClick = onClickItem,
                    )
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "🎬", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.width(10.dp))
        Text(
            "Discover",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
        )
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.width(14.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        }
    }
}
