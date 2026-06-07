package com.kuayutong.data.util

/**
 * SM-2 Spaced Repetition Algorithm implementation
 * Based on SuperMemo-2 algorithm
 * 
 * Quality scale (0-5):
 * 0 = Complete blackout (no idea)
 * 1 = Incorrect, but upon seeing the answer it rings a bell
 * 2 = Incorrect, but the answer seems familiar
 * 3 = Correct, but with difficulty (took a while to recall)
 * 4 = Correct, with some hesitation
 * 5 = Perfect response (instant recall)
 * 
 * Only qualities 3, 4, 5 are considered "passed"
 */
object SM2Algorithm {
    
    /**
     * Calculate next review interval using SM-2 algorithm
     * 
     * @param quality Answer quality (0-5)
     * @param oldEaseFactor Current ease factor (default 2.5)
     * @param oldInterval Current interval in days (0 = first time)
     * @param oldRepetition Number of consecutive correct repetitions (0 = first time)
     * @return Triple(newInterval, newEaseFactor, newRepetition)
     */
    fun calculateNextReview(
        quality: Int,
        oldEaseFactor: Float = 2.5f,
        oldInterval: Int = 0,
        oldRepetition: Int = 0
    ): Triple<Int, Float, Int> {
        
        // If quality < 3, reset repetition to 0, interval = 1 (review tomorrow)
        if (quality < 3) {
            return Triple(1, oldEaseFactor, 0)
        }
        
        // Calculate new ease factor
        val newEaseFactor = oldEaseFactor + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f))
        val clampedEaseFactor = newEaseFactor.coerceAtLeast(1.3f)
        
        // Calculate new repetition count
        val newRepetition = oldRepetition + 1
        
        // Calculate new interval
        val newInterval = when (newRepetition) {
            1 -> 1  // First successful review: 1 day
            2 -> 6  // Second successful review: 6 days
            else -> (oldInterval * clampedEaseFactor).toInt().coerceAtLeast(1)
        }
        
        return Triple(newInterval, clampedEaseFactor, newRepetition)
    }
    
    /**
     * Get the next review date timestamp
     */
    fun getNextReviewDateMillis(intervalDays: Int): Long {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, intervalDays)
        // Set to end of day (23:59:59) so it's due on that day
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 23)
        calendar.set(java.util.Calendar.MINUTE, 59)
        calendar.set(java.util.Calendar.SECOND, 59)
        return calendar.timeInMillis
    }
    
    /**
     * Check if a review is due today
     */
    fun isReviewDue(nextReviewDate: Long): Boolean {
        val today = java.util.Calendar.getInstance()
        today.set(java.util.Calendar.HOUR_OF_DAY, 0)
        today.set(java.util.Calendar.MINUTE, 0)
        today.set(java.util.Calendar.SECOND, 0)
        today.set(java.util.Calendar.MILLISECOND, 0)
        return nextReviewDate <= today.timeInMillis
    }
}
