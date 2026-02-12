package dev.me.mysmov.di

import dev.me.mysmov.domain.GetCastUseCase
import dev.me.mysmov.domain.GetMovieDetailUseCase
import dev.me.mysmov.domain.GetVideoTrailersUseCase
import dev.me.mysmov.domain.MovieUseCase
import dev.me.mysmov.domain.NowPlayingMovieUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    single { MovieUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
    single { NowPlayingMovieUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
    single { GetMovieDetailUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
    single { GetCastUseCase(movieRepository = get(named(ModuleConstant.MOVIE_REPO))) }
    single { GetVideoTrailersUseCase(movieRepository = get(named(ModuleConstant.MOVIE_REPO))) }
}