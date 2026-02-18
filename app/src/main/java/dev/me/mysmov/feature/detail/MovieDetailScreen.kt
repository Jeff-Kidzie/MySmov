package dev.me.mysmov.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.me.mysmov.data.model.ui.VideoTrailerUi
import dev.me.mysmov.ui.component.AppButton
import dev.me.mysmov.ui.component.ButtonType
import dev.me.mysmov.ui.component.shimmer
import dev.me.mysmov.ui.component.CastItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel = koinViewModel(),
    id: Int,
    onBackClick: () -> Unit = {}
) {

    val movieDetailState = movieDetailViewModel.viewState.collectAsState().value
    LaunchedEffect(Unit) {
        movieDetailViewModel.onAction(DetailMovieAction.OnRequestDetail(id))
        movieDetailViewModel.effect.collect { effect ->
            when (effect) {
                DetailMovieEffect.NavigateToWatchList -> TODO()
                is DetailMovieEffect.ShowToast -> TODO()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSection(movieDetailState, onBackClick)
        ContentSection(movieDetailState = movieDetailState)
    }
}

@Composable
private fun HeaderSection(movieDetailState: DetailMovieViewState, onBackClick: () -> Unit) {
    val isLoading = movieDetailState.isLoading
    Box(modifier = Modifier.fillMaxWidth()) {
        if (isLoading) {
            // Shimmer placeholder for backdrop image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .shimmer(true, cornerRadius = 0.dp)
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
                model = movieDetailState.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000)),
                        startY = 100f,
                        endY = 900f
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick, modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0x33000000))
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IconButton(
                    onClick = { /* TODO share */ }, modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0x33000000))
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null, tint = Color.White)
                }
                IconButton(
                    onClick = { /* TODO fav */ }, modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0x33000000))
                ) {
                    Icon(Icons.Filled.FavoriteBorder, contentDescription = null, tint = Color.White)
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                // Shimmer placeholder for poster
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(160.dp)
                        .shimmer(true, cornerRadius = 14.dp)
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .width(110.dp)
                        .height(160.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    model = movieDetailState.posterPath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                if (isLoading) {
                    // Shimmer placeholder for title
                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(28.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                    // Shimmer placeholder for details
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(18.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(18.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                    // Shimmer placeholder for rating
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(18.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                } else {
                    Text(
                        text = movieDetailState.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "${movieDetailState.yearRelease}  •  ${movieDetailState.duration}  \n ${movieDetailState.listGenres.joinToString()}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.85f))
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            tint = Color(0xFFFFD166)
                        )
                        Text(
                            text = "%.1f".format(movieDetailState.rating),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "(${movieDetailState.countReviews} reviews)",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.White.copy(
                                    alpha = 0.75f
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentSection(movieDetailState: DetailMovieViewState) {
    val isLoading = movieDetailState.isLoading
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            type = ButtonType.Primary,
            prefixIcon = {
                Icon(
                    imageVector = Icons.Filled.BookmarkBorder,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            text = "Add to Watchlist",
            onClickButton = { }
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "Synopsis",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
            if (isLoading) {
                // Shimmer placeholder for synopsis
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(16.dp)
                            .shimmer(true, cornerRadius = 4.dp)
                    )
                }
            } else {
                Text(
                    text = movieDetailState.overview,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.85f
                        )
                    )
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Top Cast",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "See All",
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
                )
            }
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                if (isLoading && movieDetailState.listCast.isEmpty()) {
                    // Shimmer placeholders for cast
                    items(5) {
                        CastItemShimmer()
                    }
                } else {
                    items(movieDetailState.listCast) { cast ->
                        CastItem(
                            name = cast.name,
                            role = cast.role,
                            imageUrl = cast.imgUrl
                        )
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Trailers & Clips",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (isLoading && movieDetailState.listTrailers.isEmpty()) {
                    // Shimmer placeholders for trailers
                    items(3) {
                        TrailerCardShimmer()
                    }
                } else {
                    items(movieDetailState.listTrailers) { trailer ->
                        TrailerCard(trailer)
                    }
                }
            }
        }
    }
}

@Composable
private fun CastItemShimmer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .shimmer(true, cornerRadius = 35.dp)
        )
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(14.dp)
                .shimmer(true, cornerRadius = 4.dp)
        )
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(12.dp)
                .shimmer(true, cornerRadius = 4.dp)
        )
    }
}

@Composable
private fun TrailerCardShimmer() {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp)
            .shimmer(true, cornerRadius = 12.dp)
    )
}

@Composable
private fun TrailerCard(trailer: VideoTrailerUi) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = trailer.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.45f)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color(0xCC000000)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                text = trailer.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class CastUiModel(val name: String, val role: String, val imageUrl: String)
