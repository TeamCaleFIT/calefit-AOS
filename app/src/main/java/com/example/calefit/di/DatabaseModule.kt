package com.example.calefit.di

import android.content.Context
import com.example.calefit.local.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideExerciseDao(
        @ApplicationContext context: Context
    ) = LocalDataBase.getInstance(context)!!.userExerciseDao()

    @Provides
    @Singleton
    fun provideTemplateDao(
        @ApplicationContext context: Context
    ) = LocalDataBase.getInstance(context)!!.userTemplateDao()
}