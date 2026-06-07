package com.kuayutong.ui.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuayutong.data.AppDatabase
import com.kuayutong.data.entity.SentenceEntity
import com.kuayutong.data.model.CefrLevel
import kotlinx.coroutines.launch

class TopicsViewModel(private val database: AppDatabase) : ViewModel() {
    
    private val _levels = MutableLiveData<List<LevelItem>>()
    val levels: LiveData<List<LevelItem>> = _levels
    
    fun loadLevels() {
        viewModelScope.launch {
            val levelList = mutableListOf<LevelItem>()
            
            // Dynamically load all levels from CefrLevel enum (CEFR + NCE)
            for (cefrLevel in CefrLevel.getAllLevels()) {
                val totalSentences = database.sentenceDao().getSentenceCountByLevel(cefrLevel.name)
                val completedSentences = database.userSentenceDao().getMasteredSentenceCountByLevel(cefrLevel.name)
                val progressPercent = if (totalSentences > 0) (completedSentences * 100 / totalSentences) else 0
                
                levelList.add(
                    LevelItem(
                        level = CEFRLevel(cefrLevel.name, cefrLevel.displayName),
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
