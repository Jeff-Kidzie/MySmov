package dev.me.mysmov.di

import dev.me.mysmov.domain.usecase.GetCastUseCase
import dev.me.mysmov.domain.movies.GetMovieDetailUseCase
import dev.me.mysmov.domain.usecase.GetVideoTrailersUseCase
import dev.me.mysmov.domain.usecase.GetMovieByCategoryUseCase
import dev.me.mysmov.domain.movies.NowPlayingMovieUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMovieByCategoryUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
    single { NowPlayingMovieUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
    single { GetMovieDetailUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
    single { GetCastUseCase(movieRepository = get(named(ModuleConstant.MOVIE_REPO))) }
    single { GetVideoTrailersUseCase(movieRepository = get(named(ModuleConstant.MOVIE_REPO))) }
}