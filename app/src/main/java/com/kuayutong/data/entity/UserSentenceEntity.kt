package com.kuayutong.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_sentences")
data class UserSentenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Sentence ID (foreign key to SentenceEntity)
    val sentenceId: Long,
    
    // Whether the user translated correctly
    val isCorrect: Boolean,
    
    // Number of attempts before correct
    val attempts: Int,
    
    // Timestamp when translated
    val translatedAt: Long,
    
    // User's answer
    val userAnswer: String,
    
    // ===== SM-2 Spaced Repetition Fields (added in version 2) =====
    
    // Ease factor (default 2.5), adjusted by SM-2 algorithm
    val easeFactor: Float = 2.5f,
    
    // Current review interval in days (0 = not yet reviewed)
    val interval: Int = 0,
    
    // Number of consecutive correct repetitions (SM-2 repetition counter)
    val repetition: Int = 0,
    
    // Next review date (millis timestamp, 0 = not scheduled)
    val nextReviewDate: Long = 0L,
    
    // Last review date (millis timestamp, 0 = never reviewed)
    val lastReviewDate: Long = 0L,
    
    // Whether this sentence is "learned" for today (consecutive 2 correct in practice)
    val isLearnedToday: Boolean = false,
    
    // Quality of last answer (0-5 SM-2 scale, -1 = not rated)
    val lastQuality: Int = -1
)
