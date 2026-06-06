package com.kuayutong.data.repository

import com.kuayutong.data.AppDatabase
import com.kuayutong.data.entity.SentenceEntity

class SentenceRepository(private val database: AppDatabase) {
    
    suspend fun insertSentences(sentences: List<SentenceEntity>) {
        database.sentenceDao().insertAll(sentences)
    }
    
    suspend fun getSentencesByLevel(level: String): List<SentenceEntity> {
        return database.sentenceDao().getSentencesByLevel(level)
    }
    
    suspend fun getSentencesByLevelAndScene(level: String, sceneId: Int): List<SentenceEntity> {
        return database.sentenceDao().getSentencesByLevelAndScene(level, sceneId)
    }
    
    suspend fun getSentenceById(id: Long): SentenceEntity? {
        return database.sentenceDao().getSentenceById(id)
    }
    
    suspend fun getSentenceCountByLevel(level: String): Int {
        return database.sentenceDao().getSentenceCountByLevel(level)
    }
    
    suspend fun getSentenceCountByLevelAndScene(level: String, sceneId: Int): Int {
        return database.sentenceDao().getSentenceCountByLevelAndScene(level, sceneId)
    }
    
    suspend fun getAllLevels(): List<String> {
        return database.sentenceDao().getAllLevels()
    }
}
