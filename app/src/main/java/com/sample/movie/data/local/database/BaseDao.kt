package com.sample.movie.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface BaseDao<T> {
    @Upsert
    suspend fun upsert(data: T): Long

    @Upsert
    suspend fun upsertAll(data: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(data: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllOrIgnore(data: List<T>): List<Long>

    @Update
    suspend fun update(data: T)

    @Update
    suspend fun update(data: List<T>)

    @Delete
    suspend fun delete(data: T)

    @Delete
    suspend fun delete(data: List<T>)
}