package com.kuayutong.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuayutong.data.entity.SentenceEntity

@Dao
interface SentenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sentences: List<SentenceEntity>)

    @Query("SELECT * FROM sentences WHERE cefrLevel = :level ORDER BY sceneId, sentenceId")
    suspend fun getSentencesByLevel(level: String): List<SentenceEntity>

    @Query("SELECT * FROM sentences WHERE cefrLevel = :level AND sceneId = :sceneId ORDER BY sentenceId")
    suspend fun getSentencesByLevelAndScene(level: String, sceneId: Int): List<SentenceEntity>

    @Query("SELECT * FROM sentences WHERE id = :id")
    suspend fun getSentenceById(id: Long): SentenceEntity?

    @Query("SELECT COUNT(*) FROM sentences WHERE cefrLevel = :level")
    suspend fun getSentenceCountByLevel(level: String): Int

    @Query("SELECT COUNT(*) FROM sentences WHERE cefrLevel = :level AND sceneId = :sceneId")
    suspend fun getSentenceCountByLevelAndScene(level: String, sceneId: Int): Int

    @Query("SELECT DISTINCT cefrLevel FROM sentences")
    suspend fun getAllLevels(): List<String>

    @Query("SELECT COUNT(*) FROM sentences")
    suspend fun getTotalSentenceCount(): Int

    @Query("SELECT DISTINCT sceneId, sceneName FROM sentences WHERE cefrLevel = :level ORDER BY sceneId")
    suspend fun getScenesByLevel(level: String): List<SceneInfo>

    // ===== New methods for spaced repetition =====
    
    // Get sentences that user has never practiced (for daily new sentences)
    @Query("""
        SELECT s.* FROM sentences s 
        WHERE s.id NOT IN (
            SELECT DISTINCT sentenceId FROM user_sentences 
            WHERE isCorrect = 1
        )
        AND s.cefrLevel = :level
        ORDER BY s.sceneId, s.sentenceId
        LIMIT :limit
    """)
    suspend fun getNewSentencesByLevel(level: String, limit: Int): List<SentenceEntity>

    // Get all sentence IDs that user has ever practiced correctly
    @Query("""
        SELECT DISTINCT sentenceId FROM user_sentences 
        WHERE sentenceId IN (SELECT id FROM sentences WHERE cefrLevel = :level)
        AND isCorrect = 1
    """)
    suspend fun getPracticedSentenceIdsByLevel(level: String): List<Long>
}
