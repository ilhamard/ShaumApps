package dev.nocturnbinary.dzikirkita.di

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.nocturnbinary.dzikirkita.BuildConfig
import dev.nocturnbinary.dzikirkita.features.asmaulhusna.data.AsmaulHusnaRepository
import dev.nocturnbinary.dzikirkita.features.asmaulhusna.data.AsmaulHusnaRepositoryImpl
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerRepository
import dev.nocturnbinary.dzikirkita.features.dailyprayer.data.DailyPrayerRepositoryImpl
import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepository
import dev.nocturnbinary.dzikirkita.features.hadits.data.HaditsRepositoryImpl
import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.PrayerScheduleRepository
import dev.nocturnbinary.dzikirkita.features.prayerschedule.data.PrayerScheduleRepositoryImpl
import dev.nocturnbinary.dzikirkita.network.DzikirKitaHttpClientBuilder
import dev.nocturnbinary.dzikirkita.network.RequestHandler
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.http.URLProtocol
import javax.inject.Qualifier

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
                        "v1" -> request.url.host = BuildConfig.PRAYER_SCHEDULE_HOST
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

    @Provides
    fun provideAsmaulHusnaRepository(impl: AsmaulHusnaRepositoryImpl): AsmaulHusnaRepository = impl

    @Provides
    fun providePrayerScheduleRepository(impl: PrayerScheduleRepositoryImpl): PrayerScheduleRepository = impl

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @Provides
    @AccelerometerSensor
    fun provideAccelerometer(sensorManager: SensorManager): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    @Provides
    @MagnetometerSensor
    fun provideMangetometer(sensorManager: SensorManager): Sensor? {
        return sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccelerometerSensor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MagnetometerSensor