package dev.me.mysmov.di

import dev.me.mysmov.data.datasource.LocalMovieDataSource
import dev.me.mysmov.data.datasource.RemoteMovieDataSource
import dev.me.mysmov.data.repository.MovieRepository
import dev.me.mysmov.data.repository.MovieRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    // remote data source
    single { RemoteMovieDataSource(get()) }

    // local data source
    single { LocalMovieDataSource() }

    single(named(ModuleConstant.MOVIE_REPO)) {
        MovieRepositoryImpl(
            get(),
            get()
        ) as MovieRepository
    }
}
