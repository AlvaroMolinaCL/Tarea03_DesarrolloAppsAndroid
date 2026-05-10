package com.amolinaj.eventmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amolinaj.eventmaster.data.local.entity.EventItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventItemDao {

    @Query("SELECT * FROM events ORDER BY id ASC")
    fun observeAll(): Flow<List<EventItemEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(event: EventItemEntity): Long

    @Query("SELECT COUNT(*) FROM events")
    suspend fun count(): Int
}
