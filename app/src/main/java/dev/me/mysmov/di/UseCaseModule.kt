package dev.me.mysmov.di

import dev.me.mysmov.domain.MovieUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    single { MovieUseCase(get(named(ModuleConstant.MOVIE_REPO))) }
}