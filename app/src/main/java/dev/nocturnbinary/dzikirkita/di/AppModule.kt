package dev.nocturnbinary.dzikirkita.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nocturnbinary.dzikirkita.BuildConfig
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerRepository
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerRepositoryImpl
import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepository
import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepositoryImpl
import dev.nocturnbinary.dzikirkita.network.DzikirKitaHttpClientBuilder
import dev.nocturnbinary.dzikirkita.network.RequestHandler
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.http.URLProtocol

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideHttpClient(): HttpClient =
        DzikirKitaHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(BuildConfig.DAILY_PRAYER_HOST)
            .build().apply {
                plugin(HttpSend).intercept { request ->
                    when (request.url.pathSegments.first()) { // this is the key
                        "api" -> request.url.host = BuildConfig.DAILY_PRAYER_HOST
                        "books" -> request.url.host = BuildConfig.RANDOM_HADITS_HOST
                        else -> {}
                    }
                    execute(request)
                }
            }

    @Provides
    fun provideRequestHandler(client: HttpClient): RequestHandler = RequestHandler(client)

    @Provides
    fun provideHaditsRepository(impl: HaditsRepositoryImpl): HaditsRepository = impl

    @Provides
    fun provideDailyPrayerRepository(impl: DailyPrayerRepositoryImpl): DailyPrayerRepository = impl
}