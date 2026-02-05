package dev.me.mysmov.feature.home

//import androidx.compose.material.icons.filled.Bell
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.me.mysmov.data.model.Movie
import dev.me.mysmov.ui.component.MovieBannerCard
import dev.me.mysmov.ui.component.MovieItem
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onAction(HomeAction.InitPage)
    }

    val searchQuery = remember { mutableStateOf("") }
    val popular = remember {
        listOf(
            Movie(4, "The Batman", "2022 • Action", "https://image.tmdb.org/t/p/original/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg", 8.1),
            Movie(5, "John Wick 4", "2023 • Action", "https://image.tmdb.org/t/p/original/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg", 7.8),
            Movie(6, "Interstellar", "2014 • Sci-Fi", "https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg", 8.6),
        )
    }
    val topRated = remember {
        listOf(
            Movie(7, "The Godfather", "Crime • 1972", "https://image.tmdb.org/t/p/original/3bhkrj58Vtu7enYsRolD1fZdja1.jpg", 9.2),
            Movie(8, "Shawshank Redemption", "Drama • 1994", "https://image.tmdb.org/t/p/original/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg", 9.3),
            Movie(9, "Dune: Part Two", "2024 • Sci-Fi", "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg", 8.8),
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                placeholder = { Text("Movies, actors, or genres...") }
            ) {}
        }
        item { SectionNowPlaying(state.nowPlayingMovies) }
        item { SectionHeader(title = "Popular", actionText = "") }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(popular) { movie ->
                    MovieItem(
                        imageUrl = movie.posterPath,
                        title = movie.title,
                        subtitle = movie.overview,
                        rating = movie.rating
                    )
                }
            }
        }
        item { SectionHeader(title = "Top Rated", actionText = "See More") }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(topRated) { movie ->
                    MovieItem(
                        imageUrl = movie.posterPath,
                        title = movie.title,
                        subtitle = movie.overview,
                        rating = movie.rating
                    )
                }
            }
        }
        item { Spacer(Modifier.height(12.dp)) }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🎬", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.width(10.dp))
        Text("Discover", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold))
        Spacer(Modifier.weight(1f))
//        Icon(imageVector = Icons.Default.Bell, contentDescription = null)
        Spacer(Modifier.width(14.dp))
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
        }
    }
}

@Composable
private fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
        Spacer(Modifier.weight(1f))
        if (actionText.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(actionText, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun SectionNowPlaying(cards: List<Movie>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Now Playing", actionText = "View All")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(end = 16.dp)
        ) {
            items(cards) { card ->
                MovieBannerCard(
                    modifier = Modifier.width(320.dp),
                    imageUrl = card.posterPath,
                    title = card.title,
                    rating = card.rating
                )
            }
        }
    }
}
