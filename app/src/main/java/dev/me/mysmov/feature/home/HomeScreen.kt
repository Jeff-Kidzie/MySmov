package dev.me.mysmov.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.me.mysmov.ui.component.MovieItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val state = viewModel.viewState.collectAsState().value
    Column(modifier = Modifier.fillMaxSize()) {
        SearchSection(state)
        Spacer(Modifier.height(8.dp))
        NowPlayingSection(state)
        Spacer(Modifier.height(8.dp))
        PopularSection(state)
        Spacer(Modifier.height(8.dp))
        TvShowSection(state)
    }

}

@Composable
private fun SearchSection(state: HomeViewState) {
    MovieItem(
        imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"
    )
}

@Composable
private fun NowPlayingSection(state: HomeViewState) {
    MovieItem(
        imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"
    )
}

@Composable
private fun PopularSection(state: HomeViewState) {
    MovieItem(
        imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"
    )
}

@Composable
private fun TvShowSection(state: HomeViewState) {
    MovieItem(
        imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"
    )
}
