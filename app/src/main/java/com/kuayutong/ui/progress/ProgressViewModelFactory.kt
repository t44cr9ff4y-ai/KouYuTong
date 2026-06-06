package com.kuayutong.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuayutong.data.AppDatabase

class ProgressViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgressViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
