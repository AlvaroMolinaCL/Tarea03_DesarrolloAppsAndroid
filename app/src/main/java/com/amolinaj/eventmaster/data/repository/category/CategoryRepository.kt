package com.amolinaj.eventmaster.data.repository.category

import com.amolinaj.eventmaster.ui.model.EventCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun observeAll(): Flow<List<EventCategory>>
    suspend fun insert(name: String, description: String)
}
