package com.trifcdr.lifestylehub.di

import android.content.Context
import androidx.room.Room
import com.trifcdr.lifestylehub.data.network.ktor.LifestyleHUBHttpClient
import com.trifcdr.lifestylehub.domain.repositories.WeatherRepository
import com.trifcdr.lifestylehub.data.database.leisureDB.LeisureDB
import com.trifcdr.lifestylehub.data.database.leisureDB.LeisureDBImpl
import com.trifcdr.lifestylehub.data.database.leisureDB.room.LeisureDatabase
import com.trifcdr.lifestylehub.data.database.venueDB.DetailsDB
import com.trifcdr.lifestylehub.data.database.venueDB.DetailsDBImpl
import com.trifcdr.lifestylehub.data.database.venueDB.room.VenueDetailsDatabase
import com.trifcdr.lifestylehub.data.network.api.randomUser.RandomUserApi
import com.trifcdr.lifestylehub.data.network.api.randomUser.RandomUserApiImpl
import com.trifcdr.lifestylehub.data.network.api.venue.FSVenueApi
import com.trifcdr.lifestylehub.data.network.api.venue.FSVenueApiImpl
import com.trifcdr.lifestylehub.data.network.api.weather.WeatherApi
import com.trifcdr.lifestylehub.data.network.api.weather.WeatherApiImpl
import com.trifcdr.lifestylehub.data.repository.LeisureRepositoryImpl
import com.trifcdr.lifestylehub.data.repository.WeatherRepositoryImpl
import com.trifcdr.lifestylehub.data.repository.UserRepositoryImpl
import com.trifcdr.lifestylehub.data.repository.VenueRepositoryImpl
import com.trifcdr.lifestylehub.data.storage.AppStorage
import com.trifcdr.lifestylehub.data.storage.AppStorageImpl
import com.trifcdr.lifestylehub.domain.repositories.LeisureRepository
import com.trifcdr.lifestylehub.domain.repositories.UserRepository
import com.trifcdr.lifestylehub.domain.repositories.VenueRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by trifcdr.
 */

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    private val httpClient = LifestyleHUBHttpClient().getHttpClient()

    @Provides
    @Singleton
    fun provideAppStorage(@ApplicationContext context: Context): AppStorage {
        return AppStorageImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return WeatherApiImpl(
            httpClient
        )
    }

    @Provides
    @Singleton
    fun provideFSVenueApi(): FSVenueApi {
        return FSVenueApiImpl(
            httpClient
        )
    }

    @Provides
    @Singleton
    fun provideUserApi(): RandomUserApi {
        return RandomUserApiImpl(
            httpClient
        )
    }

    @Provides
    @Singleton
    fun provideLeisureDB(database: LeisureDatabase): LeisureDB {
        return LeisureDBImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideDetailsDB(database: VenueDetailsDatabase): DetailsDB{
        return DetailsDBImpl(
            database = database
        )
    }

    @Provides
    @Singleton
    fun provideVenueDataBase(@ApplicationContext context: Context): VenueDetailsDatabase =
        Room.databaseBuilder(
            context,
            VenueDetailsDatabase::class.java,
            "venueDetails_table"
        )
            .build()

    @Provides
    @Singleton
    fun provideLeisureDataBase(@ApplicationContext context: Context): LeisureDatabase =
        Room.databaseBuilder(
            context,
            LeisureDatabase::class.java,
            "leisure_table"
        )
            .build()


    @Provides
    @Singleton
    fun provideVenueRepository(
        database: DetailsDB,
        fsVenueApi: FSVenueApi,
        appStorage: AppStorage
    ): VenueRepository {
        return VenueRepositoryImpl(
            venueApi = fsVenueApi,
            database = database,
            appStorage = appStorage
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: RandomUserApi
    ): UserRepository {
        return UserRepositoryImpl(
            userApi = userApi
        )
    }

    @Provides
    @Singleton
    fun provideLifestyleHubRepository(
        weatherApi: WeatherApi
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherApi = weatherApi
        )
    }
    @Provides
    @Singleton
    fun provideLeisureRepository(
        database: LeisureDB
    ): LeisureRepository {
        return LeisureRepositoryImpl(
            database
        )
    }
}