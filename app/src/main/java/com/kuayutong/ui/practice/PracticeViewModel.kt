package com.kuayutong.ui.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuayutong.KouYuTongApplication
import com.kuayutong.data.entity.SentenceEntity
import com.kuayutong.data.entity.UserSentenceEntity
import kotlinx.coroutines.launch

class PracticeViewModel : ViewModel() {

    private val database = KouYuTongApplication.database

    private val _currentSentence = MutableLiveData<SentenceEntity?>()
    val currentSentence: LiveData<SentenceEntity?> = _currentSentence

    private val _resultMessage = MutableLiveData<String>()
    val resultMessage: LiveData<String> = _resultMessage

    private val _showHint = MutableLiveData(false)
    val showHint: LiveData<Boolean> = _showHint

    private val _hintMessage = MutableLiveData<String>()
    val hintMessage: LiveData<String> = _hintMessage

    private val _canGoNext = MutableLiveData(false)
    val canGoNext: LiveData<Boolean> = _canGoNext

    private var sentences: List<SentenceEntity> = emptyList()
    private var currentIndex = 0
    private var wrongAttempts = 0
    private var currentLevel = ""

    fun loadSentencesByLevel(level: String) {
        currentLevel = level
        viewModelScope.launch {
            sentences = database.sentenceDao().getSentencesByLevel(level)
            if (sentences.isNotEmpty()) {
                currentIndex = 0
                _currentSentence.postValue(sentences[currentIndex])
                resetState()
            }
        }
    }

    fun submitAnswer(userAnswer: String) {
        val sentence = _currentSentence.value ?: return

        val isCorrect = checkAnswer(userAnswer, sentence.englishAnswer, sentence.grammarFocus)

        if (isCorrect) {
            _resultMessage.postValue("正确！")
            _canGoNext.postValue(true)
            _showHint.postValue(false)
            _hintMessage.postValue("")

            // Save to database
            viewModelScope.launch {
                database.userSentenceDao().insertUserSentence(
                    UserSentenceEntity(
                        sentenceId = sentence.id,
                        isCorrect = true,
                        attempts = wrongAttempts + 1,
                        translatedAt = System.currentTimeMillis(),
                        userAnswer = userAnswer
                    )
                )
            }
            wrongAttempts = 0
        } else {
            wrongAttempts++
            _resultMessage.postValue("不正确，请重试")

            // Modification 4: Auto-show hint on 2nd wrong attempt (before showing answer)
            if (wrongAttempts >= 2) {
                _showHint.postValue(true)
                _hintMessage.postValue("💡 ${generateHint(userAnswer, sentence)}")
            }
        }
    }

    // Modification 4: Generate smart hints (not direct answer) after 2nd wrong
    private fun generateHint(userAnswer: String, sentence: SentenceEntity): String {
        val correct = sentence.englishAnswer.trim()
        val user = userAnswer.trim()
        val grammar = sentence.grammarFocus

        val hints = mutableListOf<String>()

        // Check word count difference
        val correctWords = correct.split(Regex("\\s+"))
        val userWords = user.split(Regex("\\s+"))
        val wordDiff = correctWords.size - userWords.size
        if (wordDiff > 0) {
            hints.add("你少写了约${wordDiff}个词")
        } else if (wordDiff < 0) {
            hints.add("你多写了约${-wordDiff}个词")
        }

        // Check grammar focus hints
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

        // Check word order hint
        val userLower = user.lowercase().replace(Regex("[.,!?;:\"'`~]+"), "")
        val correctLower = correct.lowercase().replace(Regex("[.,!?;:\"'`~]+"), "")
        if (userWords.size == correctWords.size && userLower != correctLower) {
            hints.add("词汇基本正确，检查词序排列")
        }

        // Check subject-verb agreement for complex sentences
        if (correctWords.size >= 6) {
            hints.add("确保主语和动词在人称和数上一致")
        }

        if (hints.isEmpty()) {
            hints.add("检查拼写、时态和词序是否正确")
        }

        return hints.take(3).joinToString("；")
    }

    fun showAnswer() {
        val sentence = _currentSentence.value ?: return
        _hintMessage.postValue("正确答案：${sentence.englishAnswer}")
    }

    fun goToNext() {
        if (currentIndex < sentences.size - 1) {
            currentIndex++
            _currentSentence.postValue(sentences[currentIndex])
            resetState()
        }
    }

    fun goToPrev() {
        if (currentIndex > 0) {
            currentIndex--
            _currentSentence.postValue(sentences[currentIndex])
            resetState()
        }
    }

    private fun resetState() {
        _resultMessage.postValue("")
        _showHint.postValue(false)
        _hintMessage.postValue("")
        _canGoNext.postValue(false)
        wrongAttempts = 0
    }

    /**
     * Modification 2 & 3: Flexible answer checking
     * - Ignores punctuation and extra spaces (mod 2)
     * - For complex sentences (6+ words), accepts semantically similar answers (mod 3)
     */
    private fun checkAnswer(userAnswer: String, correctAnswer: String, grammarFocus: String): Boolean {
        // Step 1: Normalize both answers - remove punctuation, extra spaces, lowercase
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

        // Step 2: Exact match after normalization
        if (normalizedUser == normalizedCorrect) return true

        // Step 3: For complex sentences (6+ words), use semantic matching
        val correctWords = normalizedCorrect.split(" ")
        if (correctWords.size >= 6) {
            return isSemanticallySimilar(normalizedUser, normalizedCorrect)
        }

        // Step 4: For shorter sentences, check key words matching
        val userWords = normalizedUser.split(" ")
        if (userWords.size >= 4 && correctWords.size >= 4) {
            return isSemanticallySimilar(normalizedUser, normalizedCorrect)
        }

        return false
    }

    /**
     * Checks if the user's answer is semantically similar to the correct answer.
     * For complex sentences, we check:
     * - Key content words match (nouns, verbs, adjectives)
     * - Phrase structure is similar
     * - No grammatical errors that change meaning
     */
    private fun isSemanticallySimilar(user: String, correct: String): Boolean {
        val userWords = user.split(" ").toSet()
        val correctWords = correct.split(" ").toSet()

        // Common function words to potentially ignore
        val functionWords = setOf("the", "a", "an", "is", "are", "was", "were", "be",
            "been", "has", "have", "had", "do", "does", "did", "will", "would",
            "shall", "should", "can", "could", "may", "might", "must", "of", "in",
            "on", "at", "to", "for", "with", "by", "from", "my", "your", "his")

        // Extract content words
        val userContent = userWords.filter { it !in functionWords && it.length > 2 }
        val correctContent = correctWords.filter { it !in functionWords && it.length > 2 }

        // Calculate content word overlap
        val overlap = userContent.intersect(correctContent).size
        val totalDistinct = userContent.union(correctContent).size

        if (totalDistinct == 0) return false

        val similarity = overlap.toDouble() / totalDistinct

        // For complex sentences: require 65%+ content word similarity
        return similarity >= 0.65
    }
}
