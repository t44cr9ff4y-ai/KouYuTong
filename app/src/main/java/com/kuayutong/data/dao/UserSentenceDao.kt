package com.kuayutong.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuayutong.data.entity.UserSentenceEntity
import java.util.Date

@Dao
interface UserSentenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSentence(userSentence: UserSentenceEntity)

    @Query("SELECT * FROM user_sentences WHERE sentenceId = :sentenceId ORDER BY translatedAt DESC LIMIT 1")
    suspend fun getLatestUserSentence(sentenceId: Long): UserSentenceEntity?

    @Query("SELECT COUNT(*) FROM user_sentences WHERE sentenceId IN (SELECT id FROM sentences WHERE cefrLevel = :level) AND isCorrect = 1")
    suspend fun getCorrectCountByLevel(level: String): Int

    @Query("SELECT COUNT(DISTINCT sentenceId) FROM user_sentences WHERE sentenceId IN (SELECT id FROM sentences WHERE cefrLevel = :level) AND isCorrect = 1")
    suspend fun getMasteredSentenceCountByLevel(level: String): Int

    @Query("SELECT COUNT(*) FROM user_sentences")
    suspend fun getTotalPracticeCount(): Int

    @Query("SELECT COUNT(*) FROM user_sentences WHERE isCorrect = 1")
    suspend fun getTotalCorrectCount(): Int

    @Query("SELECT DISTINCT translatedAt FROM user_sentences ORDER BY translatedAt DESC")
    suspend fun getAllPracticeDates(): List<Long>

    @Query("SELECT COUNT(DISTINCT sentenceId) FROM user_sentences WHERE sentenceId IN (SELECT id FROM sentences WHERE cefrLevel = :level AND sceneId = :sceneId) AND isCorrect = 1")
    suspend fun getMasteredCountByScene(level: String, sceneId: Int): Int

    // ===== New methods for spaced repetition =====

    // Get sentences due for review (nextReviewDate <= today)
    @Query("""
        SELECT us.* FROM user_sentences us
        INNER JOIN sentences s ON us.sentenceId = s.id
        WHERE us.nextReviewDate <= :todayMillis
        AND us.isCorrect = 1
        AND s.cefrLevel = :level
        ORDER BY us.nextReviewDate ASC
        LIMIT :limit
    """)
    suspend fun getDueReviewsByLevel(level: String, todayMillis: Long, limit: Int): List<UserSentenceEntity>

    // Get all due reviews count by level
    @Query("""
        SELECT COUNT(DISTINCT us.sentenceId) FROM user_sentences us
        INNER JOIN sentences s ON us.sentenceId = s.id
        WHERE us.nextReviewDate <= :todayMillis
        AND us.isCorrect = 1
        AND s.cefrLevel = :level
    """)
    suspend fun getDueReviewCountByLevel(level: String, todayMillis: Long): Int

    // Update SM-2 fields for a user sentence record
    @Query("""
        UPDATE user_sentences 
        SET easeFactor = :easeFactor, 
            `interval` = :interval, 
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

    // Reset isLearnedToday flag for all records (call on new day)
    @Query("UPDATE user_sentences SET isLearnedToday = 0")
    suspend fun resetDailyLearnedFlag()

    // Get sentences that are learned today but not yet mastered (for practice mode)
    @Query("""
        SELECT us.* FROM user_sentences us
        WHERE us.isLearnedToday = 1
        AND us.sentenceId IN (SELECT id FROM sentences WHERE cefrLevel = :level)
    """)
    suspend fun getTodayLearnedByLevel(level: String): List<UserSentenceEntity>
}
