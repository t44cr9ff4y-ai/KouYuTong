package com.kuayutong.data.repository

import com.kuayutong.data.AppDatabase
import com.kuayutong.data.entity.UserProgressEntity
import com.kuayutong.data.entity.UserSentenceEntity

class ProgressRepository(private val database: AppDatabase) {
    
    suspend fun getProgress(): UserProgressEntity? {
        return database.userProgressDao().getProgress()
    }
    
    suspend fun updateProgress(progress: UserProgressEntity) {
        database.userProgressDao().insertProgress(progress)
    }
    
    suspend fun insertUserSentence(userSentence: UserSentenceEntity) {
        database.userSentenceDao().insertUserSentence(userSentence)
    }
    
    suspend fun getLatestUserSentence(sentenceId: Long): UserSentenceEntity? {
        return database.userSentenceDao().getLatestUserSentence(sentenceId)
    }
    
    suspend fun getTotalPracticeCount(): Int {
        return database.userSentenceDao().getTotalPracticeCount()
    }
    
    suspend fun getTotalCorrectCount(): Int {
        return database.userSentenceDao().getTotalCorrectCount()
    }
    
    suspend fun getCorrectCountByLevel(level: String): Int {
        return database.userSentenceDao().getCorrectCountByLevel(level)
    }
    
    suspend fun getMasteredSentenceCountByLevel(level: String): Int {
        return database.userSentenceDao().getMasteredSentenceCountByLevel(level)
    }
}
