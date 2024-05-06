package com.arnavigation.app.di

import com.arnavigation.app.data.AuthRepository
import com.arnavigation.app.data.AuthRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesepositoryImpl(firebaseAuth: FirebaseAuth):AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }

}