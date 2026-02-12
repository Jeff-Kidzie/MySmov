package dev.me.mysmov.di

import dev.me.mysmov.feature.detail.MovieDetailViewModel
import dev.me.mysmov.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MovieDetailViewModel(get(), get()) }
}
