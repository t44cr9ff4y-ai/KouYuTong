package com.kuayutong.ui.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuayutong.KouYuTongApplication
import com.kuayutong.data.entity.SentenceEntity
import com.kuayutong.data.entity.UserSentenceEntity
import com.kuayutong.data.util.SM2Algorithm
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

/**
 * Practice modes:
 * - MODE_PRACTICE: Learn new sentences (daily limit)
 * - MODE_REVIEW: Review due sentences (SM-2 algorithm)
 */
enum class PracticeMode {
    MODE_PRACTICE,
    MODE_REVIEW
}

class PracticeViewModel : ViewModel() {

    private val database = KouYuTongApplication.database

    // Current mode
    private val _currentMode = MutableLiveData(PracticeMode.MODE_PRACTICE)
    val currentMode: LiveData<PracticeMode> = _currentMode

    // Current sentence
    private val _currentSentence = MutableLiveData<SentenceEntity?>()
    val currentSentence: LiveData<SentenceEntity?> = _currentSentence

    // Result message
    private val _resultMessage = MutableLiveData("")
    val resultMessage: LiveData<String> = _resultMessage

    // Show hint
    private val _showHint = MutableLiveData(false)
    val showHint: LiveData<Boolean> = _showHint

    // Hint message
    private val _hintMessage = MutableLiveData("")
    val hintMessage: LiveData<String> = _hintMessage

    // Can go next
    private val _canGoNext = MutableLiveData(false)
    val canGoNext: LiveData<Boolean> = _canGoNext

    // Progress: "今日已学 X/N 句" or "今日已复习 X/N 句"
    private val _progressText = MutableLiveData("")
    val progressText: LiveData<String> = _progressText

    // Daily limits (from SharedPreferences, default values)
    private var dailyPracticeLimit = 20
    private var dailyReviewLimit = 50

    // ===== Practice mode state =====
    private var practiceSentences: List<SentenceEntity> = emptyList() // Today's new sentences
    private var practiceIndex = 0
    private var practiceCompletedCount = 0 // Sentences completed today (consecutive 2 correct)
    private val sentenceRetryCount = mutableMapOf<Long, Int>() // sentenceId -> retry count today
    private val sentenceConsecutiveCorrect = mutableMapOf<Long, Int>() // sentenceId -> consecutive correct count today

    // ===== Review mode state =====
    private var reviewSentences: List<UserSentenceEntity> = emptyList() // Today's due reviews
    private var reviewIndex = 0
    private var reviewCompletedCount = 0 // Reviews completed today

    // Current sentence being practiced/reviewed
    private var currentSentenceEntity: SentenceEntity? = null
    private var currentUserSentenceEntity: UserSentenceEntity? = null // For review mode
    private var wrongAttempts = 0

    // Current level
    private var currentLevel = ""

    // ===== Public methods =====

    fun setDailyLimits(practiceLimit: Int, reviewLimit: Int) {
        dailyPracticeLimit = practiceLimit
        dailyReviewLimit = reviewLimit
    }

    fun switchMode(mode: PracticeMode) {
        _currentMode.postValue(mode)
        when (mode) {
            PracticeMode.MODE_PRACTICE -> loadPracticeSentences()
            PracticeMode.MODE_REVIEW -> loadReviewSentences()
        }
    }

    fun loadSentencesByLevel(level: String) {
        currentLevel = level
        when (_currentMode.value) {
            PracticeMode.MODE_PRACTICE -> loadPracticeSentences()
            PracticeMode.MODE_REVIEW -> loadReviewSentences()
            else -> loadPracticeSentences()
        }
    }

    fun submitAnswer(userAnswer: String) {
        val sentence = currentSentenceEntity ?: return

        val isCorrect = checkAnswer(userAnswer, sentence.englishAnswer, sentence.grammarFocus)

        if (isCorrect) {
            wrongAttempts = 0
            _resultMessage.postValue("正确！")
            _canGoNext.postValue(true)
            _showHint.postValue(false)
            _hintMessage.postValue("")

            // Handle based on mode
            when (_currentMode.value) {
                PracticeMode.MODE_PRACTICE -> handlePracticeCorrect(sentence)
                PracticeMode.MODE_REVIEW -> handleReviewCorrect(sentence)
                else -> {}
            }
        } else {
            wrongAttempts++
            _resultMessage.postValue("不正确，请重试")
            _canGoNext.postValue(false)

            // Show hint on 2nd wrong attempt
            if (wrongAttempts >= 2) {
                _showHint.postValue(true)
                _hintMessage.postValue("💡 ${generateHint(userAnswer, sentence)}")
            }
        }
    }

    fun showAnswer() {
        val sentence = currentSentenceEntity ?: return
        _hintMessage.postValue("正确答案：${sentence.englishAnswer}")
    }

    fun goToNext() {
        when (_currentMode.value) {
            PracticeMode.MODE_PRACTICE -> moveToNextPracticeSentence()
            PracticeMode.MODE_REVIEW -> moveToNextReviewSentence()
            else -> {}
        }
    }

    fun goToPrev() {
        // Not needed for now, keep simple
    }

    // ===== Practice mode logic =====

    private fun loadPracticeSentences() {
        viewModelScope.launch {
            // Get today's new sentences (not practiced before or not mastered)
            val todayMillis = getTodayStartMillis()
            val newSentences = database.sentenceDao().getNewSentencesByLevel(currentLevel, dailyPracticeLimit)
            
            practiceSentences = newSentences
            practiceIndex = 0
            practiceCompletedCount = 0
            sentenceRetryCount.clear()
            sentenceConsecutiveCorrect.clear()

            updatePracticeProgress()
            
            if (practiceSentences.isNotEmpty()) {
                showPracticeSentence(practiceSentences[practiceIndex])
            } else {
                _currentSentence.postValue(null)
                _progressText.postValue("今日新句子已全部完成！")
            }
        }
    }

    private fun showPracticeSentence(sentence: SentenceEntity) {
        currentSentenceEntity = sentence
        currentUserSentenceEntity = null
        wrongAttempts = 0
        _currentSentence.postValue(sentence)
        _resultMessage.postValue("")
        _showHint.postValue(false)
        _hintMessage.postValue("")
        _canGoNext.postValue(false)
    }

    private fun handlePracticeCorrect(sentence: SentenceEntity) {
        val sentenceId = sentence.id
        val consecutiveCorrect = sentenceConsecutiveCorrect.getOrDefault(sentenceId, 0) + 1
        sentenceConsecutiveCorrect[sentenceId] = consecutiveCorrect

        // Save to database
        viewModelScope.launch {
            database.userSentenceDao().insertUserSentence(
                UserSentenceEntity(
                    sentenceId = sentenceId,
                    isCorrect = true,
                    attempts = wrongAttempts + 1,
                    translatedAt = System.currentTimeMillis(),
                    userAnswer = "", // Will be filled by caller
                    lastQuality = 3 // Default quality for practice mode
                )
            )
        }

        if (consecutiveCorrect >= 2) {
            // Mastered for today, move to next sentence
            practiceCompletedCount++
            sentenceConsecutiveCorrect[sentenceId] = 0 // Reset for potential future reviews
            updatePracticeProgress()
            moveToNextPracticeSentence()
        } else {
            // Need one more correct answer, stay on this sentence
            _resultMessage.postValue("正确！再试一次确认掌握")
        }
    }

    private fun moveToNextPracticeSentence() {
        if (practiceCompletedCount >= dailyPracticeLimit) {
            _currentSentence.postValue(null)
            _progressText.postValue("今日学习完成！已学 $practiceCompletedCount/$dailyPracticeLimit 句")
            return
        }

        if (practiceIndex < practiceSentences.size - 1) {
            practiceIndex++
            showPracticeSentence(practiceSentences[practiceIndex])
        } else {
            // No more sentences, try to load more
            viewModelScope.launch {
                val moreSentences = database.sentenceDao().getNewSentencesByLevel(currentLevel, dailyPracticeLimit - practiceCompletedCount)
                if (moreSentences.isNotEmpty()) {
                    practiceSentences = practiceSentences + moreSentences
                    practiceIndex++
                    showPracticeSentence(practiceSentences[practiceIndex])
                } else {
                    _currentSentence.postValue(null)
                    _progressText.postValue("今日学习完成！已学 $practiceCompletedCount/$dailyPracticeLimit 句")
                }
            }
        }
    }

    private fun updatePracticeProgress() {
        _progressText.postValue("今日已学 ${practiceCompletedCount}/$dailyPracticeLimit 句")
    }

    // ===== Review mode logic =====

    private fun loadReviewSentences() {
        viewModelScope.launch {
            val todayMillis = getTodayStartMillis()
            val dueReviews = database.userSentenceDao().getDueReviewsByLevel(currentLevel, todayMillis, dailyReviewLimit)
            
            reviewSentences = dueReviews
            reviewIndex = 0
            reviewCompletedCount = 0

            updateReviewProgress()

            if (reviewSentences.isNotEmpty()) {
                showReviewSentence(reviewSentences[reviewIndex])
            } else {
                _currentSentence.postValue(null)
                _progressText.postValue("今日无待复习句子")
            }
        }
    }

    private fun showReviewSentence(userSentence: UserSentenceEntity) {
        currentUserSentenceEntity = userSentence
        viewModelScope.launch {
            val sentence = database.sentenceDao().getSentenceById(userSentence.sentenceId)
            currentSentenceEntity = sentence
            wrongAttempts = 0
            _currentSentence.postValue(sentence)
            _resultMessage.postValue("")
            _showHint.postValue(false)
            _hintMessage.postValue("")
            _canGoNext.postValue(false)
        }
    }

    private fun handleReviewCorrect(sentence: SentenceEntity) {
        // For review mode, we need to ask user for quality (0-5)
        // Simplified: auto-detect quality based on wrongAttempts
        val quality = when {
            wrongAttempts == 0 -> 5 // Instant correct
            wrongAttempts == 1 -> 4 // Correct after 1 try
            else -> 3 // Correct after multiple tries
        }

        val userSentence = currentUserSentenceEntity ?: return

        // Calculate next review using SM-2
        val (newInterval, newEaseFactor, newRepetition) = SM2Algorithm.calculateNextReview(
            quality = quality,
            oldEaseFactor = userSentence.easeFactor,
            oldInterval = userSentence.interval,
            oldRepetition = userSentence.repetition
        )

        val nextReviewDate = SM2Algorithm.getNextReviewDateMillis(newInterval)

        // Update database
        viewModelScope.launch {
            database.userSentenceDao().updateSM2Fields(
                id = userSentence.id,
                easeFactor = newEaseFactor,
                interval = newInterval,
                repetition = newRepetition,
                nextReviewDate = nextReviewDate,
                lastReviewDate = System.currentTimeMillis(),
                lastQuality = quality
            )
        }

        reviewCompletedCount++
        updateReviewProgress()
        moveToNextReviewSentence()
    }

    private fun moveToNextReviewSentence() {
        if (reviewCompletedCount >= dailyReviewLimit) {
            _currentSentence.postValue(null)
            _progressText.postValue("今日复习完成！已复习 $reviewCompletedCount/$dailyReviewLimit 句")
            return
        }

        if (reviewIndex < reviewSentences.size - 1) {
            reviewIndex++
            showReviewSentence(reviewSentences[reviewIndex])
        } else {
            _currentSentence.postValue(null)
            _progressText.postValue("今日复习完成！已复习 $reviewCompletedCount/$dailyReviewLimit 句")
        }
    }

    private fun updateReviewProgress() {
        _progressText.postValue("今日已复习 ${reviewCompletedCount}/$dailyReviewLimit 句")
    }

    // ===== Utility methods =====

    private fun getTodayStartMillis(): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    /**
     * Check answer (same as before, with normalization and semantic matching)
     */
    private fun checkAnswer(userAnswer: String, correctAnswer: String, grammarFocus: String): Boolean {
        val normalize: (String) -> String = { s ->
            s.trim()
                .lowercase()
                .replace(Regex("""[,!?;:"'()\[\]{}`~]+"""), "")
                .replace(Regex("""\.(?=\s|$)"""), "")
                .replace(Regex("""\s+"""), " ")
                .trim()
        }
        val normalizedUser = normalize(userAnswer)
        val normalizedCorrect = normalize(correctAnswer)

        if (normalizedUser == normalizedCorrect) return true

        val correctWords = normalizedCorrect.split(" ")
        if (correctWords.size >= 6) {
            return isSemanticallySimilar(normalizedUser, normalizedCorrect)
        }

        val userWords = normalizedUser.split(" ")
        if (userWords.size >= 4 && correctWords.size >= 4) {
            return isSemanticallySimilar(normalizedUser, normalizedCorrect)
        }

        return false
    }

    private fun isSemanticallySimilar(user: String, correct: String): Boolean {
        val userWords = user.split(" ").toSet()
        val correctWords = correct.split(" ").toSet()

        val functionWords = setOf("the", "a", "an", "is", "are", "was", "were", "be",
            "been", "has", "have", "had", "do", "does", "did", "will", "would",
            "shall", "should", "can", "could", "may", "might", "must", "of", "in ",
            "on", "at", "to", "for", "with", "by", "from", "my", "your", "his")

        val userContent = userWords.filter { it !in functionWords && it.length > 2 }
        val correctContent = correctWords.filter { it !in functionWords && it.length > 2 }

        val overlap = userContent.intersect(correctContent).size
        val totalDistinct = userContent.union(correctContent).size

        if (totalDistinct == 0) return false

        val similarity = overlap.toDouble() / totalDistinct
        return similarity >= 0.65
    }

    private fun generateHint(userAnswer: String, sentence: SentenceEntity): String {
        val correct = sentence.englishAnswer.trim()
        val user = userAnswer.trim()
        val grammar = sentence.grammarFocus

        val hints = mutableListOf<String>()

        val correctWords = correct.split(Regex("\\s+"))
        val userWords = user.split(Regex("\\s+"))
        val wordDiff = correctWords.size - userWords.size
        if (wordDiff > 0) {
            hints.add("你少写了约${wordDiff}个词")
        } else if (wordDiff < 0) {
            hints.add("你多写了约${-wordDiff}个词")
        }

        val grammarLower = grammar.lowercase()
        if (grammarLower.contains("时态") || grammarLower.contains("现在") || grammarLower.contains("过去") || grammarLower.contains("将来")) {
            hints.add("检查时态——现在时/过去时/将来时是否正确？")
        }
        if (grammarLower.contains("疑问") || grammarLower.contains("what") || grammarLower.contains("where") || grammarLower.contains("how")) {
            hints.add("疑问句的语序是否正确？")
        }
        if (grammarLower.contains("否定") || grammarLower.contains("don't") || grammarLower.contains("doesn't")) {
            hints.add("否定形式是否正确？")
        }
        if (grammarLower.contains("介词") || grammarLower.contains("in") || grammarLower.contains("on") || grammarLower.contains("at")) {
            hints.add("注意介词（in/on/at/for/with等）选择")
        }
        if (grammarLower.contains("比较") || grammarLower.contains("形容")) {
            hints.add("检查形容词比较级或最高级形式")
        }
        if (grammarLower.contains("复数") || grammarLower.contains("名词")) {
            hints.add("名词的单复数形式是否正确？")
        }
        if (grammarLower.contains("进行时") || grammarLower.contains("ing")) {
            hints.add("检查进行时的动词形式：be + doing")
        }
        if (grammarLower.contains("完成时") || grammarLower.contains("have")) {
            hints.add("完成时：have/has + 过去分词")
        }
        if (grammarLower.contains("情态") || grammarLower.contains("can") || grammarLower.contains("should") || grammarLower.contains("must")) {
            hints.add("情态动词后应接动词原形")
        }

        val userLower = user.lowercase().replace(Regex("[.,!?;:\"'`~]+"), "")
        val correctLower = correct.lowercase().replace(Regex("[.,!?;:\"'`~]+"), "")
        if (userWords.size == correctWords.size && userLower != correctLower) {
            hints.add("词汇基本正确，检查词序排列")
        }

        if (correctWords.size >= 6) {
            hints.add("确保主语和动词在人称和数上一致")
        }

        if (hints.isEmpty()) {
            hints.add("检查拼写、时态和词序是否正确")
        }

        return hints.take(3).joinToString("；")
    }
}
