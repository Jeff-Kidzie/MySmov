package dev.me.mysmov.data.repository

import dev.me.mysmov.data.datasource.LocalMovieDataSource
import dev.me.mysmov.data.datasource.RemoteMovieDataSource
import dev.me.mysmov.domain.repository.MovieRepository

class MovieRepositoryImpl(local: LocalMovieDataSource, remote: RemoteMovieDataSource) :
    MovieRepository by remote {
}