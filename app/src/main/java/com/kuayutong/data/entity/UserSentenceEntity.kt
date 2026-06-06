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
    val userAnswer: String
)
