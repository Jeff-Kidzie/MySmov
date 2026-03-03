package dev.me.mysmov.domain.model


interface MediaCategory {
    val name : String
}
sealed class MovieCategory : MediaCategory {
    object Popular : MovieCategory() {
        override val name: String
            get() = "popular"
    }
    object NowPlaying : MovieCategory() {
        override val name: String
            get() = "now_playing"
    }
    object Upcoming : MovieCategory(){
        override val name: String
            get() = "upcoming"
    }
    object TopRated : MovieCategory() {
        override val name: String
            get() = "top_rated"
    }
}

sealed class TvCategory(val name : String) {
    object Popular : TvCategory("popular")
    object AiringToday : TvCategory("airing_today")
    object OnTheAir : TvCategory("on_the_air")
    object TopRated : TvCategory("top_rated")
}

fun MovieCategory.label(): String = when (this) {
    MovieCategory.Popular -> "Popular"
    MovieCategory.NowPlaying -> "Now Playing"
    MovieCategory.Upcoming -> "Upcoming"
    MovieCategory.TopRated -> "Top Rated"
}

fun TvCategory.label(): String = when (this) {
    TvCategory.Popular -> "Popular"
    TvCategory.AiringToday -> "Airing Today"
    TvCategory.OnTheAir -> "On The Air"
    TvCategory.TopRated -> "Top Rated"
}