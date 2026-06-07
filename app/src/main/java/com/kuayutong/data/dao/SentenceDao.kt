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

    @Query("SELECT * FROM sentences WHERE id = :id")
    suspend fun getSentenceById(id: Long): SentenceEntity

    @Query("SELECT COUNT(*) FROM sentences")
    suspend fun getTotalSentenceCount(): Int

    @Query("SELECT * FROM sentences WHERE cefrLevel = :level AND sceneId = :sceneId ORDER BY sentenceId")
    suspend fun getSentencesByScene(level: String, sceneId: Int): List<SentenceEntity>

    @Query("SELECT * FROM sentences WHERE cefrLevel = :level AND id NOT IN (SELECT sentenceId FROM user_sentences) ORDER BY id LIMIT :limit")
    suspend fun getNewSentencesByLevel(level: String, limit: Int): List<SentenceEntity>

    @Query("SELECT DISTINCT cefrLevel FROM sentences ORDER BY cefrLevel")
    suspend fun getAllCefrLevels(): List<String>

    @Query("SELECT COUNT(*) FROM sentences WHERE cefrLevel LIKE :prefix || '%'")
    suspend fun getCountByLevelPrefix(prefix: String): Int

    @Query("SELECT COUNT(*) FROM sentences WHERE cefrLevel = :level")
    suspend fun getSentenceCountByLevel(level: String): Int

    @Query("SELECT DISTINCT sceneId, sceneName FROM sentences WHERE cefrLevel = :level ORDER BY sceneId")
    suspend fun getScenesByLevel(level: String): List<SceneInfo>

    @Query("SELECT COUNT(*) FROM sentences WHERE cefrLevel = :levelCode AND sceneId = :sceneId")
    suspend fun getSentenceCountByLevelAndScene(levelCode: String, sceneId: Int): Int
}
