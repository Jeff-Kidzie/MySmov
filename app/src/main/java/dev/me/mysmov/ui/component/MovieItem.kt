package dev.me.mysmov.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MovieItem(
    imageUrl: String,
    title: String = "",
    description: String = "",
    rating: Double = 4.0,
    genre: String = "",
    releasedYear: String = ""
) {

    Column(modifier = Modifier.width(120.dp)) {
        AsyncImage(
            modifier = Modifier
                .height(180.dp)
                .width(120.dp),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(
        imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg"
    )
}