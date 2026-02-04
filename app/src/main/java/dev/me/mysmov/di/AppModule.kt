package dev.me.mysmov.di

import dev.me.mysmov.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    viewModel { HomeViewModel() }
}
