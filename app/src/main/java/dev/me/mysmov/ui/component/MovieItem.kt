package dev.me.mysmov.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MovieItem(
    imageUrl: String,
    title: String = "",
    subtitle: String = "", // e.g. "2022 • Action"
    rating: Double? = null,
) {

    Column(modifier = Modifier.width(140.dp)) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .width(140.dp),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            rating?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .background(color = Color(0xCC000000), shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD166),
                        modifier = Modifier.height(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(text = String.format("%.1f", it), style = MaterialTheme.typography.labelMedium, color = Color.White)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(2.dp))
        if (subtitle.isNotBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(
        imageUrl = "https://image.tmdb.org/t/p/original/2VK4d3mqqTc7LVZLnLPeRiPaJ71.jpg",
        title = "John Wick 4",
        subtitle = "2023 • Action",
        rating = 7.8
    )
}