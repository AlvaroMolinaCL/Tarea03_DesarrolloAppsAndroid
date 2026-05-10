package com.amolinaj.eventmaster.di

import com.amolinaj.eventmaster.data.repository.EventRepository
import com.amolinaj.eventmaster.data.repository.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEventRepository(repositoryImpl: EventRepositoryImpl): EventRepository
}
