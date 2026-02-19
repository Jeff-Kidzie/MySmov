package dev.me.mysmov.di

import dev.me.mysmov.data.remote.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    val baseUrlQualifier = named("tmdbBaseUrl")
    val defaultAccessToken = named("defaultAccessToken")
    val headerInterceptor = named("headerInterceptor")

    single(baseUrlQualifier) { "https://api.themoviedb.org/3/" }
    single(defaultAccessToken) {"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMGQxY2IwOGI3YjU3ODgyYWRhYTczMWZiMWJkNmI5ZiIs" +
            "Im5iZiI6MTc1NzE0NjY5NS45NjgsInN1YiI6IjY4YmJlZTQ3ZjRmYzc0NGU1MTk4YjU2OCIsInNjb3BlcyI6WyJhcGlfcmVh" +
            "ZCJdLCJ2ZXJzaW9uIjoxfQ.dCQQdo20p9eWcBEjkp6dOWmmSpKtqJPBvZ-QV1-Wtek"}
    single(headerInterceptor) {
        Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer ${get<String>(defaultAccessToken)}")
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(headerInterceptor))
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
