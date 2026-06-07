package com.kuayutong.util

import java.util.Calendar

/**
 * SM-2 (SuperMemo-2) Spaced Repetition Algorithm.
 * Used to calculate next review interval based on user performance.
 *
 * Quality scale (0-5):
 * 0 = Blackout (complete failure to recall)
 * 1 = Incorrect response, but upon seeing the answer it feels familiar
 * 2 = Incorrect response, but upon seeing the answer the correct one pops up
 * 3 = Correct response, but required significant effort to recall
 * 4 = Correct response, after some hesitation
 * 5 = Perfect response, like the answer was on the tip of your tongue
 */
object SM2Algorithm {

    /**
     * Calculate next review parameters based on SM-2 algorithm.
     * Returns Triple(newInterval, newEaseFactor, newRepetition)
     */
    fun calculateNextReview(
        quality: Int,          // 0-5
        oldEaseFactor: Float,  // previous EF
        oldInterval: Int,       // previous interval (days)
        oldRepetition: Int     // previous repetition count
    ): Triple<Int, Float, Int> {
        var ef = oldEaseFactor
        var rep = oldRepetition
        var intv = oldInterval

        if (quality >= 3) {
            // Correct response
            if (rep == 0) {
                intv = 1
            } else if (rep == 1) {
                intv = 6
            } else {
                intv = (intv * ef).toInt()
            }
            rep++

            // Update ease factor
            ef = ef + (0.1f - (5 - quality) * (0.08f + (5 - quality) * 0.02f))
            if (ef < 1.3f) ef = 1.3f
        } else {
            // Incorrect response -> reset repetition, interval = 1
            rep = 0
            intv = 1
            // Ease factor also decreases slightly
            ef = (ef - 0.2f).coerceAtLeast(1.3f)
        }

        return Triple(intv, ef, rep)
    }

    /**
     * Calculate next review date (millis) from today.
     */
    fun getNextReviewDateMillis(intervalDays: Int): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.add(Calendar.DAY_OF_YEAR, intervalDays)
        // Set to start of day (00:00:00) for cleaner scheduling
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    /**
     * Get today's start millis (00:00:00).
     */
    fun getTodayStartMillis(): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}
