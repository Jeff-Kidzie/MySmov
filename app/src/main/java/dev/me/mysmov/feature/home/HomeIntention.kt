package dev.me.mysmov.feature.home

import dev.me.mysmov.core.base.Action
import dev.me.mysmov.core.base.Effect
import dev.me.mysmov.core.base.Event
import dev.me.mysmov.core.base.ViewState
import dev.me.mysmov.domain.model.MediaItem
import dev.me.mysmov.domain.model.MovieCategory
import dev.me.mysmov.domain.model.TvCategory

enum class MediaType { Movie, TvShow }

sealed class HomeAction : Action {
    object InitPage : HomeAction()
    object RefreshPage : HomeAction()
    object LoadMoreCategoryItems : HomeAction()
    data class OnClickMovie(val id: Int) : HomeAction()
    data class SwitchMediaType(val type: MediaType) : HomeAction()
    data class SelectMovieCategory(val category: MovieCategory) : HomeAction()
    data class SelectTvCategory(val category: TvCategory) : HomeAction()
}

data class HomeViewState(
    val selectedMediaType: MediaType = MediaType.Movie,
    val selectedMovieCategory: MovieCategory = MovieCategory.Popular,
    val selectedTvCategory: TvCategory = TvCategory.Popular,
    val featuredItems: List<MediaItem> = emptyList(),
    val isLoadingFeatured: Boolean = true,
    val categoryItems: List<MediaItem> = emptyList(),
    val isLoadingCategory: Boolean = true,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String = "",
) : ViewState

sealed class HomeEvent : Event {
    data class ShowFeaturedItems(val items: List<MediaItem>) : HomeEvent()
    object ShowLoadingFeatured : HomeEvent()
    object DismissLoadingFeatured : HomeEvent()

    data class ShowCategoryItems(val items: List<MediaItem>) : HomeEvent()
    data class AppendCategoryItems(val items: List<MediaItem>) : HomeEvent()
    object ShowLoadingCategory : HomeEvent()
    object DismissLoadingCategory : HomeEvent()
    object ShowLoadingMore : HomeEvent()
    object DismissLoadingMore : HomeEvent()
    data class SetCanLoadMore(val canLoadMore: Boolean) : HomeEvent()

    object ShowRefreshing : HomeEvent()
    object DismissRefreshing : HomeEvent()

    data class UpdateMediaType(val type: MediaType) : HomeEvent()
    data class UpdateMovieCategory(val category: MovieCategory) : HomeEvent()
    data class UpdateTvCategory(val category: TvCategory) : HomeEvent()

    data class ShowError(val message: String) : HomeEvent()
}

sealed class HomeEffect : Effect {
    data class ShowDetailMovie(val id: Int) : HomeEffect()
}
