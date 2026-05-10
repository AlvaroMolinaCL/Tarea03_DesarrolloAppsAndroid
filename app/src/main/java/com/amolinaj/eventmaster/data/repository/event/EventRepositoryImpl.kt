package com.amolinaj.eventmaster.data.repository.event

import com.amolinaj.eventmaster.data.local.dao.event.EventDao
import com.amolinaj.eventmaster.data.local.entity.event.EventEntity
import com.amolinaj.eventmaster.ui.model.EventItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDao: EventDao
) : EventRepository {

    override fun observeAll(): Flow<List<EventItem>> {
        return eventDao.observeAll().map { events ->
            events.map { it.toModel() }
        }
    }

    override suspend fun insert(
        categoryId: Int,
        title: String,
        description: String,
        date: String,
        location: String,
        imageResName: String?
    ) {
        eventDao.insert(
            EventEntity(
                categoryId = categoryId,
                title = title,
                description = description,
                date = date,
                location = location,
                imageResName = imageResName
            )
        )
    }

    private fun EventEntity.toModel(): EventItem {
        return EventItem(
            id = id,
            categoryId = categoryId,
            title = title,
            description = description,
            date = date,
            location = location,
            imageResName = imageResName
        )
    }
}
