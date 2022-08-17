package com.example.calefit.di

import com.example.calefit.datasource.RemoteDatasource
import com.example.calefit.datasource.RemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    @Binds
    @Singleton
    abstract fun remoteDatasource(
        remoteDatasourceImpl: RemoteDatasourceImpl
    ): RemoteDatasource
}