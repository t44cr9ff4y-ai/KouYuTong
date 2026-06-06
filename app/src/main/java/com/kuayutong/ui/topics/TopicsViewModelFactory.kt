package com.kuayutong.ui.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuayutong.data.AppDatabase

class TopicsViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopicsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
