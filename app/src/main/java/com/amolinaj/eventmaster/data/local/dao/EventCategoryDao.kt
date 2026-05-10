package com.amolinaj.eventmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amolinaj.eventmaster.data.local.entity.EventCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventCategoryDao {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun observeAll(): Flow<List<EventCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(category: EventCategoryEntity): Long

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun count(): Int
}
