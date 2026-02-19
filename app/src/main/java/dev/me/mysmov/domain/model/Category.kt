package dev.me.mysmov.domain.model

sealed class MovieCategory(val name : String) {
    object Popular : MovieCategory("popular")
    object NowPlaying : MovieCategory("now_playing")
    object Upcoming : MovieCategory("upcoming")
    object TopRated : MovieCategory("top_rated")
}

sealed class TvCategory(val name : String) {
    object Popular : TvCategory("popular")
    object AiringToday : TvCategory("airing_today")
    object OnTheAir : TvCategory("on_the_air")
    object TopRated : TvCategory("top_rated")
}