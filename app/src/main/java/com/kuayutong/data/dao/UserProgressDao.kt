package com.kuayutong.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kuayutong.data.entity.UserProgressEntity

@Dao
interface UserProgressDao {
    
    @Query("SELECT * FROM user_progress WHERE id = 1")
    suspend fun getProgress(): UserProgressEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: UserProgressEntity)
    
    @Update
    suspend fun updateProgress(progress: UserProgressEntity)
    
    @Query("DELETE FROM user_progress")
    suspend fun deleteAll()
}
