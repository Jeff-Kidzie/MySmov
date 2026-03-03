package dev.me.mysmov.feature.home

import androidx.lifecycle.viewModelScope
import dev.me.mysmov.core.base.BaseViewModel
import dev.me.mysmov.domain.model.MovieCategory
import dev.me.mysmov.domain.model.TvCategory
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCase
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCaseParam
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCaseResult
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCase
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCaseParam
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCaseResult
import dev.me.mysmov.domain.usecase.GetTvByCategoryUseCase
import dev.me.mysmov.domain.usecase.GetTvByCategoryUseCaseParam
import dev.me.mysmov.domain.usecase.GetTvByCategoryUseCaseResult
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getMovieByCategoryUseCase: GetMovieByCategoryUseCase,
    private val nowPlayingMovieUseCase: NowPlayingMovieUseCase,
    private val getTvByCategoryUseCase: GetTvByCategoryUseCase,
) : BaseViewModel<HomeAction, HomeEvent, HomeEffect, HomeViewState>() {

    private var currentCategoryPage = 1

    override fun initialState(): HomeViewState = HomeViewState()

    override fun handleOnAction(action: HomeAction) {
        viewModelScope.launch {
            val state = viewState.value
            when (action) {
                HomeAction.InitPage -> {
                    loadFeaturedMovies()
                    loadMovieCategory(MovieCategory.Popular, page = 1, append = false)
                }

                HomeAction.RefreshPage -> {
                    sendEvent(HomeEvent.ShowRefreshing)
                    currentCategoryPage = 1
                    if (state.selectedMediaType == MediaType.Movie) {
                        loadFeaturedMovies()
                        loadMovieCategory(state.selectedMovieCategory, page = 1, append = false)
                    } else {
                        loadFeaturedTv()
                        loadTvCategory(state.selectedTvCategory, page = 1, append = false)
                    }
                    sendEvent(HomeEvent.DismissRefreshing)
                }

                HomeAction.LoadMoreCategoryItems -> {
                    if (state.isLoadingMore || state.isLoadingCategory || !state.canLoadMore) return@launch
                    currentCategoryPage++
                    if (state.selectedMediaType == MediaType.Movie) {
                        loadMovieCategory(state.selectedMovieCategory, currentCategoryPage, append = true)
                    } else {
                        loadTvCategory(state.selectedTvCategory, currentCategoryPage, append = true)
                    }
                }

                is HomeAction.SwitchMediaType -> {
                    currentCategoryPage = 1
                    sendEvent(HomeEvent.UpdateMediaType(action.type))
                    if (action.type == MediaType.Movie) {
                        sendEvent(HomeEvent.UpdateMovieCategory(MovieCategory.Popular))
                        loadFeaturedMovies()
                        loadMovieCategory(MovieCategory.Popular, page = 1, append = false)
                    } else {
                        sendEvent(HomeEvent.UpdateTvCategory(TvCategory.Popular))
                        loadFeaturedTv()
                        loadTvCategory(TvCategory.Popular, page = 1, append = false)
                    }
                }

                is HomeAction.SelectMovieCategory -> {
                    currentCategoryPage = 1
                    sendEvent(HomeEvent.UpdateMovieCategory(action.category))
                    loadMovieCategory(action.category, page = 1, append = false)
                }

                is HomeAction.SelectTvCategory -> {
                    currentCategoryPage = 1
                    sendEvent(HomeEvent.UpdateTvCategory(action.category))
                    loadTvCategory(action.category, page = 1, append = false)
                }

                is HomeAction.OnClickMovie -> sendEffect(HomeEffect.ShowDetailMovie(action.id))
            }
        }
    }

    private suspend fun loadFeaturedMovies() {
        sendEvent(HomeEvent.ShowLoadingFeatured)
        val result = nowPlayingMovieUseCase.execute(NowPlayingMovieUseCaseParam)
        when (result) {
            is NowPlayingMovieUseCaseResult.Success ->
                sendEvent(HomeEvent.ShowFeaturedItems(result.mediaItems))
            is NowPlayingMovieUseCaseResult.Error ->
                sendEvent(HomeEvent.ShowError(result.message))
        }
        sendEvent(HomeEvent.DismissLoadingFeatured)
    }

    private suspend fun loadFeaturedTv() {
        sendEvent(HomeEvent.ShowLoadingFeatured)
        val result = getTvByCategoryUseCase.execute(
            GetTvByCategoryUseCaseParam(TvCategory.AiringToday)
        )
        when (result) {
            is GetTvByCategoryUseCaseResult.Success ->
                sendEvent(HomeEvent.ShowFeaturedItems(result.mediaItems))
            is GetTvByCategoryUseCaseResult.Error ->
                sendEvent(HomeEvent.ShowError(result.message))
        }
        sendEvent(HomeEvent.DismissLoadingFeatured)
    }

    private suspend fun loadMovieCategory(category: MovieCategory, page: Int, append: Boolean) {
        if (append) sendEvent(HomeEvent.ShowLoadingMore) else sendEvent(HomeEvent.ShowLoadingCategory)
        val result = getMovieByCategoryUseCase.execute(GetMovieByCategoryUseCaseParam(category, page))
        when (result) {
            is GetMovieByCategoryUseCaseResult.Success -> {
                val items = result.mediaItems
                sendEvent(HomeEvent.SetCanLoadMore(items.isNotEmpty()))
                if (append) sendEvent(HomeEvent.AppendCategoryItems(items))
                else sendEvent(HomeEvent.ShowCategoryItems(items))
            }
            is GetMovieByCategoryUseCaseResult.Error ->
                sendEvent(HomeEvent.ShowError(result.message))
        }
        if (append) sendEvent(HomeEvent.DismissLoadingMore) else sendEvent(HomeEvent.DismissLoadingCategory)
    }

    private suspend fun loadTvCategory(category: TvCategory, page: Int, append: Boolean) {
        if (append) sendEvent(HomeEvent.ShowLoadingMore) else sendEvent(HomeEvent.ShowLoadingCategory)
        val result = getTvByCategoryUseCase.execute(GetTvByCategoryUseCaseParam(category, page))
        when (result) {
            is GetTvByCategoryUseCaseResult.Success -> {
                val items = result.mediaItems
                sendEvent(HomeEvent.SetCanLoadMore(items.isNotEmpty()))
                if (append) sendEvent(HomeEvent.AppendCategoryItems(items))
                else sendEvent(HomeEvent.ShowCategoryItems(items))
            }
            is GetTvByCategoryUseCaseResult.Error ->
                sendEvent(HomeEvent.ShowError(result.message))
        }
        if (append) sendEvent(HomeEvent.DismissLoadingMore) else sendEvent(HomeEvent.DismissLoadingCategory)
    }

    override fun reduce(oldState: HomeViewState, event: HomeEvent): HomeViewState {
        return when (event) {
            is HomeEvent.ShowFeaturedItems -> oldState.copy(featuredItems = event.items)
            HomeEvent.ShowLoadingFeatured -> oldState.copy(isLoadingFeatured = true, featuredItems = emptyList())
            HomeEvent.DismissLoadingFeatured -> oldState.copy(isLoadingFeatured = false)

            is HomeEvent.ShowCategoryItems -> oldState.copy(categoryItems = event.items)
            is HomeEvent.AppendCategoryItems -> oldState.copy(categoryItems = oldState.categoryItems + event.items)
            HomeEvent.ShowLoadingCategory -> oldState.copy(isLoadingCategory = true, categoryItems = emptyList(), canLoadMore = true)
            HomeEvent.DismissLoadingCategory -> oldState.copy(isLoadingCategory = false)
            HomeEvent.ShowLoadingMore -> oldState.copy(isLoadingMore = true)
            HomeEvent.DismissLoadingMore -> oldState.copy(isLoadingMore = false)
            is HomeEvent.SetCanLoadMore -> oldState.copy(canLoadMore = event.canLoadMore)

            HomeEvent.ShowRefreshing -> oldState.copy(isRefreshing = true)
            HomeEvent.DismissRefreshing -> oldState.copy(isRefreshing = false)

            is HomeEvent.UpdateMediaType -> oldState.copy(selectedMediaType = event.type)
            is HomeEvent.UpdateMovieCategory -> oldState.copy(selectedMovieCategory = event.category)
            is HomeEvent.UpdateTvCategory -> oldState.copy(selectedTvCategory = event.category)
            is HomeEvent.ShowError -> oldState.copy(errorMessage = event.message)
        }
    }
}
