package com.kuayutong.util

import android.content.Context

/**
 * SharedPreferences utility for practice settings
 */
object PracticeSettings {
    
    private const val PREFS_NAME = "practice_settings"
    
    private const val KEY_DAILY_PRACTICE_LIMIT = "daily_practice_limit"
    private const val KEY_DAILY_REVIEW_LIMIT = "daily_review_limit"
    
    const val DEFAULT_PRACTICE_LIMIT = 20
    const val DEFAULT_REVIEW_LIMIT = 50
    
    const val MIN_PRACTICE_LIMIT = 10
    const val MAX_PRACTICE_LIMIT = 100
    const val PRACTICE_STEP = 10
    
    const val MIN_REVIEW_LIMIT = 20
    const val MAX_REVIEW_LIMIT = 200
    const val REVIEW_STEP = 20
    
    fun getDailyPracticeLimit(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_DAILY_PRACTICE_LIMIT, DEFAULT_PRACTICE_LIMIT)
    }
    
    fun setDailyPracticeLimit(context: Context, limit: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_DAILY_PRACTICE_LIMIT, limit).apply()
    }
    
    fun getDailyReviewLimit(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_DAILY_REVIEW_LIMIT, DEFAULT_REVIEW_LIMIT)
    }
    
    fun setDailyReviewLimit(context: Context, limit: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_DAILY_REVIEW_LIMIT, limit).apply()
    }
}
