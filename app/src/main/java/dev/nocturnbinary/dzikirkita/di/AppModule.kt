package dev.nocturnbinary.dzikirkita.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nocturnbinary.dzikirkita.BuildConfig
import dev.nocturnbinary.dzikirkita.features.home.data.HomeRepository
import dev.nocturnbinary.dzikirkita.features.home.data.HomeRepositoryImpl
import dev.nocturnbinary.dzikirkita.network.DzikirKitaHttpClientBuilder
import io.ktor.client.HttpClient
import io.ktor.http.URLProtocol

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideHttpClient(): HttpClient =
        DzikirKitaHttpClientBuilder()
            .protocol(URLProtocol.HTTPS)
            .host(BuildConfig.RANDOM_HADITS_HOST)
            .build()

    @Provides
    fun provideHomeRepository(impl: HomeRepositoryImpl): HomeRepository = impl
}