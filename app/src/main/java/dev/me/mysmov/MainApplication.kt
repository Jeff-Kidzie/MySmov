package dev.me.mysmov

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import dev.me.mysmov.di.appModule
import dev.me.mysmov.di.dataSourceModule
import dev.me.mysmov.di.networkModule
import dev.me.mysmov.di.useCaseModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule + networkModule + useCaseModule + dataSourceModule)
        }
    }
}