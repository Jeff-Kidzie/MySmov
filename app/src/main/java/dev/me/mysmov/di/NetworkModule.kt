package dev.me.mysmov.di

import dev.me.mysmov.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val baseUrlQualifier = named("tmdbBaseUrl")

    single(baseUrlQualifier) { "https://api.themoviedb.org/3/" }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(baseUrlQualifier))
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }
    // can add another api service here
    // single { get<Retrofit>().create(AnotherApiService::class.java) }
}
