package com.amolinaj.eventmaster.data.repository.category

import com.amolinaj.eventmaster.data.local.dao.category.CategoryDao
import com.amolinaj.eventmaster.data.local.entity.category.CategoryEntity
import com.amolinaj.eventmaster.ui.model.EventCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun observeAll(): Flow<List<EventCategory>> {
        return categoryDao.observeAll().map { categories ->
            categories.map { it.toModel() }
        }
    }

    override suspend fun insert(name: String, description: String) {
        categoryDao.insert(
            CategoryEntity(
                name = name,
                description = description
            )
        )
    }

    private fun CategoryEntity.toModel(): EventCategory {
        return EventCategory(
            id = id,
            name = name,
            description = description
        )
    }
}
