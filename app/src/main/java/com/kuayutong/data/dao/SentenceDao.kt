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
}

// Raw query projection for scene listing
data class SceneInfo(
    val sceneId: Int,
    val sceneName: String
)
