package com.amolinaj.eventmaster.data.repository

import com.amolinaj.eventmaster.data.local.dao.EventCategoryDao
import com.amolinaj.eventmaster.data.local.dao.EventItemDao
import com.amolinaj.eventmaster.data.local.entity.EventCategoryEntity
import com.amolinaj.eventmaster.data.local.entity.EventItemEntity
import com.amolinaj.eventmaster.ui.model.EventCategory
import com.amolinaj.eventmaster.ui.model.EventItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventCategoryDao: EventCategoryDao,
    private val eventItemDao: EventItemDao
) : EventRepository {

    override fun observeCategories(): Flow<List<EventCategory>> {
        return eventCategoryDao.observeAll().map { categories ->
            categories.map { it.toModel() }
        }
    }

    override fun observeEvents(): Flow<List<EventItem>> {
        return eventItemDao.observeAll().map { events ->
            events.map { it.toModel() }
        }
    }

    override suspend fun insertCategory(name: String, description: String) {
        eventCategoryDao.insert(
            EventCategoryEntity(
                name = name,
                description = description
            )
        )
    }

    override suspend fun insertEvent(
        categoryId: Int,
        title: String,
        description: String,
        date: String,
        location: String,
        imageResName: String?
    ) {
        eventItemDao.insert(
            EventItemEntity(
                categoryId = categoryId,
                title = title,
                description = description,
                date = date,
                location = location,
                imageResName = imageResName
            )
        )
    }

    private fun EventCategoryEntity.toModel(): EventCategory {
        return EventCategory(
            id = id,
            name = name,
            description = description
        )
    }

    private fun EventItemEntity.toModel(): EventItem {
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
