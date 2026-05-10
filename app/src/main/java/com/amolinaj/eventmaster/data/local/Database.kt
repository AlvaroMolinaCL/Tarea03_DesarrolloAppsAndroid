package com.amolinaj.eventmaster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amolinaj.eventmaster.data.local.dao.EventCategoryDao
import com.amolinaj.eventmaster.data.local.dao.EventItemDao
import com.amolinaj.eventmaster.data.local.entity.EventCategoryEntity
import com.amolinaj.eventmaster.data.local.entity.EventItemEntity

@Database(
    entities = [EventCategoryEntity::class, EventItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventMasterDatabase : RoomDatabase() {
    abstract fun eventCategoryDao(): EventCategoryDao
    abstract fun eventItemDao(): EventItemDao
}
