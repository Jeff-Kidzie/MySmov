package dev.me.mysmov.di

import androidx.room.Room
import dev.me.mysmov.data.datasource.LocalMovieDataSource
import dev.me.mysmov.data.datasource.RemoteMovieDataSource
import dev.me.mysmov.data.local.db.AppDatabase
import dev.me.mysmov.domain.repository.MovieRepository
import dev.me.mysmov.data.repository.MovieRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "app_database").build() }

    // remote data source
    single { RemoteMovieDataSource(get()) }

    // local data source
    single { LocalMovieDataSource(get()) }

    single(named(ModuleConstant.MOVIE_REPO)) {
        MovieRepositoryImpl(
            get(),
            get()
        ) as MovieRepository
    }
}
