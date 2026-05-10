package com.amolinaj.eventmaster.di

import com.amolinaj.eventmaster.data.repository.category.CategoryRepository
import com.amolinaj.eventmaster.data.repository.category.CategoryRepositoryImpl
import com.amolinaj.eventmaster.data.repository.event.EventRepository
import com.amolinaj.eventmaster.data.repository.event.EventRepositoryImpl
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
    abstract fun bindCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(repositoryImpl: EventRepositoryImpl): EventRepository
}
