package com.kuayutong.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sentences")
data class SentenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // CEFR level: A1_ENTRY, A1_BASIC, A1_ADVANCED, A2, B1_BASIC, B1_ADVANCED, B2_BASIC, B2_ADVANCED, C1, C2
    val cefrLevel: String,
    
    // Scene ID (1-10)
    val sceneId: Int,
    
    // Scene name (e.g., "打招呼与自我介绍")
    val sceneName: String,
    
    // Sentence ID within scene (1-20)
    val sentenceId: Int,
    
    // Chinese sentence to translate
    val chineseSentence: String,
    
    // Correct English translation
    val englishAnswer: String,
    
    // Grammar focus (语法重点)
    val grammarFocus: String
)
