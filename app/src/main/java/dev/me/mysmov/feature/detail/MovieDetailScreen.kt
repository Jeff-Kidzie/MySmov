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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import dev.me.mysmov.ui.component.AppButton
import dev.me.mysmov.ui.component.ButtonType
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


    val castMembers = listOf(
        CastUiModel(
            "Marcus Thorne",
            "Elena Vance",
            "https://randomuser.me/api/portraits/men/64.jpg"
        ),
        CastUiModel(
            "Sia Jenkins",
            "Chief Kael",
            "https://randomuser.me/api/portraits/women/65.jpg"
        ),
        CastUiModel(
            "David Lowery",
            "The Admiral",
            "https://randomuser.me/api/portraits/men/66.jpg"
        ),
        CastUiModel("Liam West", "Pilot Jax", "https://randomuser.me/api/portraits/men/67.jpg")
    )

    val trailers = listOf(
        TrailerUiModel(
            title = "Official Teaser: Journey to the Edge",
            thumbnail = "https://images.unsplash.com/photo-1523966211575-eb4a01e7dd51?auto=format&fit=crop&w=800&q=80",
            duration = "2:14"
        ),
        TrailerUiModel(
            title = "Behind the Scenes",
            thumbnail = "https://images.unsplash.com/photo-1478720568477-152d9b164e26?auto=format&fit=crop&w=800&q=80",
            duration = "1:32"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderSection(movieDetailState, onBackClick)
        ContentSection(movieDetailState = movieDetailState,castMembers = castMembers, trailers = trailers)
    }
}

@Composable
private fun HeaderSection(movieDetailState: DetailMovieViewState, onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp),
            model = movieDetailState.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
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
            AsyncImage(
                modifier = Modifier
                    .width(110.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(14.dp)),
                model = movieDetailState.posterPath,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
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

@Composable
private fun ContentSection(movieDetailState: DetailMovieViewState,castMembers: List<CastUiModel>, trailers: List<TrailerUiModel>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp),
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
            Text(
                text = movieDetailState.overview,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.85f
                    )
                )
            )
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
                items(castMembers) { cast ->
                    CastItem(
                        name = cast.name,
                        role = cast.role,
                        imageUrl = cast.imageUrl
                    )
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
                items(trailers) { trailer ->
                    TrailerCard(trailer)
                }
            }
        }
    }
}

@Composable
private fun TrailerCard(trailer: TrailerUiModel) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier.matchParentSize(),
                model = trailer.thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
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
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = trailer.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = trailer.duration,
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.8f))
                )
            }
        }
    }
}

data class CastUiModel(val name: String, val role: String, val imageUrl: String)
data class TrailerUiModel(val title: String, val thumbnail: String, val duration: String)
