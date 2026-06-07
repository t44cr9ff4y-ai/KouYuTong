package com.kuayutong.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kuayutong.data.entity.UserSentenceEntity
import java.util.Calendar

@Dao
interface UserSentenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSentence(entity: UserSentenceEntity)

    @Query("""
        SELECT * FROM user_sentences 
        WHERE level = :level 
          AND nextReviewDate <= :todayMillis 
        ORDER BY nextReviewDate ASC 
        LIMIT :limit
    """)
    suspend fun getDueReviewsByLevel(level: String, todayMillis: Long, limit: Int): List<UserSentenceEntity>

    @Query("""
        UPDATE user_sentences 
        SET easeFactor = :easeFactor,
            interval = :interval,
            repetition = :repetition,
            nextReviewDate = :nextReviewDate,
            lastReviewDate = :lastReviewDate,
            lastQuality = :lastQuality
        WHERE id = :id
    """)
    suspend fun updateSM2Fields(
        id: Long,
        easeFactor: Float,
        interval: Int,
        repetition: Int,
        nextReviewDate: Long,
        lastReviewDate: Long,
        lastQuality: Int
    )

    @Query("SELECT * FROM user_sentences WHERE level = :level AND isLearnedToday = 1")
    suspend fun getTodayLearnedByLevel(level: String): List<UserSentenceEntity>

    @Query("UPDATE user_sentences SET isLearnedToday = 0 WHERE level = :level")
    suspend fun resetDailyLearnedFlag(level: String)

    @Query("SELECT COUNT(*) FROM user_sentences WHERE level = :level")
    suspend fun getCountByLevel(level: String): Int
}
