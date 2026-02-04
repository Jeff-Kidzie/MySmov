package dev.me.mysmov.feature.home

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
//import androidx.compose.material.icons.filled.Bell
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.me.mysmov.ui.component.MovieItem
import org.koin.androidx.compose.koinViewModel

private data class MovieCard(val title: String, val subtitle: String, val rating: Double, val imageUrl: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.viewState.collectAsState()
    val searchQuery = remember { mutableStateOf("") }

    val nowPlaying = remember {
        listOf(
            MovieCard(
                title = "Dune: Part Two",
                subtitle = "Watch Now",
                rating = 8.8,
                imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"
            )
        )
    }
    val popular = remember {
        listOf(
            MovieCard("The Batman", "2022 • Action", 8.1, "https://image.tmdb.org/t/p/original/6DrHO1jr3qVrViUO6s6kFiAGM7.jpg"),
            MovieCard("John Wick 4", "2023 • Action", 7.8, "https://image.tmdb.org/t/p/original/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"),
            MovieCard("Interstellar", "2014 • Sci-Fi", 8.6, "https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg"),
        )
    }
    val topRated = remember {
        listOf(
            MovieCard("The Godfather", "Crime • 1972", 9.2, "https://image.tmdb.org/t/p/original/3bhkrj58Vtu7enYsRolD1fZdja1.jpg"),
            MovieCard("Shawshank Redemption", "Drama • 1994", 9.3, "https://image.tmdb.org/t/p/original/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg"),
            MovieCard("Dune: Part Two", "2024 • Sci-Fi", 8.8, "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"),
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
        item { SectionNowPlaying(nowPlaying.first()) }
        item { SectionHeader(title = "Popular", actionText = "") }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(popular) { movie ->
                    MovieItem(
                        imageUrl = movie.imageUrl,
                        title = movie.title,
                        subtitle = movie.subtitle,
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
                        imageUrl = movie.imageUrl,
                        title = movie.title,
                        subtitle = movie.subtitle,
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
private fun SectionNowPlaying(card: MovieCard) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        SectionHeader(title = "Now Playing", actionText = "View All")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = card.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFEE5253), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("TRENDING", color = Color.White, style = MaterialTheme.typography.labelMedium)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xCC000000), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD166),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(String.format("%.1f", card.rating), color = Color.White, style = MaterialTheme.typography.labelMedium)
                    }
                }
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Watch Now", color = Color.White)
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("+", color = Color.White)
                    }
                }
            }
        }
    }
}
