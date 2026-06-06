package com.kuayutong.ui.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuayutong.data.AppDatabase
import com.kuayutong.data.entity.SentenceEntity
import kotlinx.coroutines.launch

class TopicsViewModel(private val database: AppDatabase) : ViewModel() {
    
    private val _levels = MutableLiveData<List<LevelItem>>()
    val levels: LiveData<List<LevelItem>> = _levels
    
    fun loadLevels() {
        viewModelScope.launch {
            val levelList = mutableListOf<LevelItem>()
            
            // Define all CEFR levels
            val cefrLevels = listOf(
                CEFRLevel("A1_ENTRY", "入门级A1"),
                CEFRLevel("A1_BASIC", "初级A1"),
                CEFRLevel("A1_ADVANCED", "高级A1"),
                CEFRLevel("A2", "A2"),
                CEFRLevel("B1_BASIC", "初级B1"),
                CEFRLevel("B1_ADVANCED", "高级B1"),
                CEFRLevel("B2_BASIC", "初级B2"),
                CEFRLevel("B2_ADVANCED", "高级B2"),
                CEFRLevel("C1", "C1"),
                CEFRLevel("C2", "C2")
            )
            
            for (cefrLevel in cefrLevels) {
                val totalSentences = database.sentenceDao().getSentenceCountByLevel(cefrLevel.code)
                val completedSentences = database.userSentenceDao().getMasteredSentenceCountByLevel(cefrLevel.code)
                val progressPercent = if (totalSentences > 0) (completedSentences * 100 / totalSentences) else 0
                
                levelList.add(
                    LevelItem(
                        level = cefrLevel,
                        totalSentences = totalSentences,
                        completedSentences = completedSentences,
                        progressPercent = progressPercent
                    )
                )
            }
            
            _levels.postValue(levelList)
        }
    }
}

data class CEFRLevel(
    val code: String,
    val displayName: String
)

data class LevelItem(
    val level: CEFRLevel,
    val totalSentences: Int,
    val completedSentences: Int,
    val progressPercent: Int
)
