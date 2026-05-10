package com.amolinaj.eventmaster.data.repository.event

import com.amolinaj.eventmaster.ui.model.EventItem
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun observeAll(): Flow<List<EventItem>>
    suspend fun insert(
        categoryId: Int,
        title: String,
        description: String,
        date: String,
        location: String,
        imageResName: String?
    )
}
