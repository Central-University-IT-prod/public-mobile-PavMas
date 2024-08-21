package com.trifcdr.lifestylehub.di

import android.content.Context
import androidx.room.Room
import com.trifcdr.lifestylehub.authorization.AuthorizationApi
import com.trifcdr.lifestylehub.authorization.AuthorizationApiImpl
import com.trifcdr.lifestylehub.authorization.database.UsersDB
import com.trifcdr.lifestylehub.data.database.venueDB.room.VenueDetailsDatabase
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
class AuthorizationModule {

    @Provides
    @Singleton
    fun provideAuthorizationApi(@ApplicationContext context: Context): AuthorizationApi =
        AuthorizationApiImpl(
            context  = context
        )

}