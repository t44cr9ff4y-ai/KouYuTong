package com.kuayutong.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuayutong.data.entity.UserSentenceEntity

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
}
