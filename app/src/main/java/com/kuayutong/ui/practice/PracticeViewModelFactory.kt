package com.kuayutong.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PracticeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PracticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PracticeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
