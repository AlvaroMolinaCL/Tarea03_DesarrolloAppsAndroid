package com.amolinaj.eventmaster.data.repository

import com.amolinaj.eventmaster.ui.model.EventCategory
import com.amolinaj.eventmaster.ui.model.EventItem
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun observeCategories(): Flow<List<EventCategory>>
    fun observeEvents(): Flow<List<EventItem>>
    suspend fun insertCategory(name: String, description: String)
    suspend fun insertEvent(
        categoryId: Int,
        title: String,
        description: String,
        date: String,
        location: String,
        imageResName: String?
    )
}
