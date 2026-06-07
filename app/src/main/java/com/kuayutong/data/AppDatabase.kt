package com.kuayutong.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kuayutong.data.dao.SentenceDao
import com.kuayutong.data.dao.UserProgressDao
import com.kuayutong.data.dao.UserSentenceDao
import com.kuayutong.data.entity.SentenceEntity
import com.kuayutong.data.entity.UserProgressEntity
import com.kuayutong.data.entity.UserSentenceEntity

@Database(
    entities = arrayOf(SentenceEntity::class, UserProgressEntity::class, UserSentenceEntity::class),
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun sentenceDao(): SentenceDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun userSentenceDao(): UserSentenceDao
    
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add SM-2 spaced repetition columns to user_sentences table
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN easeFactor REAL NOT NULL DEFAULT 2.5")
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN `interval` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN repetition INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN nextReviewDate INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN lastReviewDate INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN isLearnedToday INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN lastQuality INTEGER NOT NULL DEFAULT -1")
            }
        }
        
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add level column to user_sentences table
                database.execSQL("ALTER TABLE user_sentences ADD COLUMN `level` TEXT NOT NULL DEFAULT ''")
                // Update existing rows: set level from sentences table
                database.execSQL("""
                    UPDATE user_sentences 
                    SET level = (
                        SELECT cefrLevel FROM sentences 
                        WHERE sentences.id = user_sentences.sentenceId
                    )
                    WHERE EXISTS (
                        SELECT 1 FROM sentences 
                        WHERE sentences.id = user_sentences.sentenceId
                    )
                """.trimIndent())
            }
        }
    }
}
