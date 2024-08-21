package com.trifcdr.lifestylehub.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.trifcdr.lifestylehub.location.DefaultLocationClient
import com.trifcdr.lifestylehub.location.LocationClient
import com.trifcdr.lifestylehub.presentation.recycler.holders.LeisureHolder
import com.trifcdr.lifestylehub.presentation.recycler.holders.PhotoHolder
import com.trifcdr.lifestylehub.presentation.recycler.holders.VenueHolder
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManagerImpl
import com.trifcdr.lifestylehub.presentation.recycler.types.ItemTypes
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
class AppModule {

    @Provides
    @Singleton
    fun provideAdaptersManager(): ViewHoldersManager = ViewHoldersManagerImpl().apply {
        registerViewHolder(ItemTypes.VENUE, VenueHolder())
        registerViewHolder(ItemTypes.PHOTO_DETAILS, PhotoHolder())
        registerViewHolder(ItemTypes.LEISURE, LeisureHolder())
    }

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context): LocationClient{
        return DefaultLocationClient(
            context,
            LocationServices.getFusedLocationProviderClient(context)
        )
    }

}