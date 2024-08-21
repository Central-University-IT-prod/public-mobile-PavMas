package com.trifcdr.lifestylehub.di

import com.trifcdr.lifestylehub.domain.repositories.WeatherRepository
import com.trifcdr.lifestylehub.domain.usecase.GetWeatherByLocationUseCase
import com.trifcdr.lifestylehub.domain.repositories.LeisureRepository
import com.trifcdr.lifestylehub.domain.repositories.UserRepository
import com.trifcdr.lifestylehub.domain.repositories.VenueRepository
import com.trifcdr.lifestylehub.domain.usecase.DeleteLeisureByIdUseCase
import com.trifcdr.lifestylehub.domain.usecase.DeleteVenueDetailsUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetLeisureByIdUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetLeisureUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetRandomUserUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetVenueDetailsByIdUseCase
import com.trifcdr.lifestylehub.domain.usecase.GetVenueRecommendationsByLocationUseCase
import com.trifcdr.lifestylehub.domain.usecase.InsertLeisureUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by trifcdr.
 */

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetWeatherByLocationUseCase(repository: WeatherRepository): GetWeatherByLocationUseCase {
        return GetWeatherByLocationUseCase(
            repository = repository
        )
    }

    @Provides
    fun provideGetVenueRecommendationsByLocationUseCase(repository: VenueRepository): GetVenueRecommendationsByLocationUseCase{
        return GetVenueRecommendationsByLocationUseCase(
            venueRepository = repository
        )
    }

    @Provides
    fun provideGetVenueDetailsByIdUseCase(repository: VenueRepository): GetVenueDetailsByIdUseCase{
        return GetVenueDetailsByIdUseCase(
            venueRepository = repository
        )
    }

    @Provides
    fun provideGetRandomUserUseCase(repository: UserRepository): GetRandomUserUseCase{
        return GetRandomUserUseCase(
            userRepository = repository
        )
    }

    @Provides
    fun provideGetLeisureListUseCase(repository: LeisureRepository): GetLeisureUseCase{
        return GetLeisureUseCase(
            leisureRepository = repository
        )
    }
    @Provides
    fun provideGetLeisureByIdUseCase(repository: LeisureRepository): GetLeisureByIdUseCase{
        return GetLeisureByIdUseCase(
            leisureRepository = repository
        )
    }
    @Provides
    fun provideInsertLeisureIdUseCase(repository: LeisureRepository): InsertLeisureUseCase{
        return InsertLeisureUseCase(
            leisureRepository = repository
        )
    }
    @Provides
    fun provideDeleteLeisureIdUseCase(repository: LeisureRepository): DeleteLeisureByIdUseCase{
        return DeleteLeisureByIdUseCase(
            repository = repository
        )
    }
    @Provides
    fun provideDeleteVenueUseCase(repository: VenueRepository): DeleteVenueDetailsUseCase{
        return DeleteVenueDetailsUseCase(
            venueRepository = repository
        )
    }
}

