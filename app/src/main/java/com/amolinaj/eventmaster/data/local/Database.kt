package com.amolinaj.eventmaster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amolinaj.eventmaster.data.local.dao.category.CategoryDao
import com.amolinaj.eventmaster.data.local.dao.event.EventDao
import com.amolinaj.eventmaster.data.local.entity.category.CategoryEntity
import com.amolinaj.eventmaster.data.local.entity.event.EventEntity

@Database(
    entities = [CategoryEntity::class, EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventMasterDatabase : RoomDatabase() {
    abstract fun eventCategoryDao(): CategoryDao
    abstract fun eventItemDao(): EventDao
}
