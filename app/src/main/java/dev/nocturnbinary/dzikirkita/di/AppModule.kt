package dev.nocturnbinary.dzikirkita.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nocturnbinary.dzikirkita.BuildConfig
import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepository
import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepositoryImpl
import dev.nocturnbinary.dzikirkita.network.DzikirKitaHttpClientBuilder
import dev.nocturnbinary.dzikirkita.network.RequestHandler
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
    fun provideRequestHandler(client: HttpClient): RequestHandler = RequestHandler(client)

    @Provides
    fun provideHaditsRepository(impl: HaditsRepositoryImpl): HaditsRepository = impl
}