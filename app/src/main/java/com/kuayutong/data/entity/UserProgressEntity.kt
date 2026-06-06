package com.kuayutong.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey
    val id: Int = 1, // Single row for user progress
    
    // Total practice count
    val totalPracticeCount: Int = 0,
    
    // Correct count
    val correctCount: Int = 0,
    
    // Mastered sentences count
    val masteredSentenceCount: Int = 0,
    
    // Streak days
    val streakDays: Int = 0,
    
    // Last practice date (timestamp)
    val lastPracticeDate: Long = 0L,
    
    // Accuracy rate (0-100)
    val accuracyRate: Int = 0
)
