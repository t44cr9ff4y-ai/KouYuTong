package com.kuayutong.ui.progress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuayutong.data.AppDatabase
import com.kuayutong.data.entity.UserSentenceEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProgressViewModel(private val database: AppDatabase) : ViewModel() {

    private val _totalPractice = MutableLiveData<Int>()
    val totalPractice: LiveData<Int> = _totalPractice

    private val _accuracy = MutableLiveData<Int>()
    val accuracy: LiveData<Int> = _accuracy

    private val _mastered = MutableLiveData<Int>()
    val mastered: LiveData<Int> = _mastered

    private val _streak = MutableLiveData<Int>()
    val streak: LiveData<Int> = _streak

    private val _overallProgress = MutableLiveData<Int>()
    val overallProgress: LiveData<Int> = _overallProgress

    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements

    fun loadProgress() {
        viewModelScope.launch {
            val total = database.userSentenceDao().getTotalPracticeCount()
            val correct = database.userSentenceDao().getTotalCorrectCount()
            val accuracy = if (total > 0) (correct * 100 / total) else 0

            _totalPractice.postValue(total)
            _accuracy.postValue(accuracy)
            _mastered.postValue(correct)
            _streak.postValue(calculateStreak())
            _overallProgress.postValue(calculateOverallProgress(correct))
            _achievements.postValue(calculateAchievements(total, correct, accuracy))
        }
    }

    private suspend fun calculateStreak(): Int {
        val allRecords = database.userSentenceDao().getAllPracticeDates()
        if (allRecords.isEmpty()) return 0

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateSet = allRecords.map { sdf.format(Date(it)) }.toSet()

        var streak = 0
        val calendar = Calendar.getInstance()

        // Check today first
        val todayStr = sdf.format(calendar.time)
        if (!dateSet.contains(todayStr)) {
            // If no practice today, check if yesterday is in the set
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        // Count consecutive days backwards
        while (true) {
            val dateStr = sdf.format(calendar.time)
            if (dateSet.contains(dateStr)) {
                streak++
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }

        return streak
    }

    private suspend fun calculateOverallProgress(correctCount: Int): Int {
        val totalSentences = database.sentenceDao().getTotalSentenceCount()
        return if (totalSentences > 0) (correctCount * 100 / totalSentences) else 0
    }

    private fun calculateAchievements(totalPractice: Int, correct: Int, accuracy: Int): List<Achievement> {
        val list = mutableListOf<Achievement>()

        // Practice milestones
        list.add(Achievement("初来乍到", "完成第1次练习", totalPractice >= 1, "👋"))
        list.add(Achievement("练习新星", "完成10次练习", totalPractice >= 10, "⭐"))
        list.add(Achievement("勤奋学子", "完成100次练习", totalPractice >= 100, "📚"))
        list.add(Achievement("练习达人", "完成500次练习", totalPractice >= 500, "🏆"))
        list.add(Achievement("口语王者", "完成1000次练习", totalPractice >= 1000, "👑"))

        // Accuracy milestones
        list.add(Achievement("初显身手", "正确率达50%", accuracy >= 50, "📈"))
        list.add(Achievement("稳步提升", "正确率达70%", accuracy >= 70, "📊"))
        list.add(Achievement("精准大师", "正确率达90%", accuracy >= 90, "🎯"))
        list.add(Achievement("满分传奇", "正确率达100%", accuracy >= 100, "💎"))

        // Mastery milestones
        list.add(Achievement("掌握学者", "掌握10个句子", correct >= 10, "✅"))
        list.add(Achievement("句子猎人", "掌握50个句子", correct >= 50, "⚡"))
        list.add(Achievement("翻译专家", "掌握100个句子", correct >= 100, "🌟"))
        list.add(Achievement("完美收关", "掌握全部句子", correct >= 500, "🏅"))

        return list
    }
}

data class Achievement(
    val name: String,
    val description: String,
    val unlocked: Boolean,
    val emoji: String
)
