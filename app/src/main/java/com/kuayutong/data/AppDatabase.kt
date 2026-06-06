package com.kuayutong.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kuayutong.data.dao.SentenceDao
import com.kuayutong.data.dao.UserProgressDao
import com.kuayutong.data.dao.UserSentenceDao
import com.kuayutong.data.entity.SentenceEntity
import com.kuayutong.data.entity.UserProgressEntity
import com.kuayutong.data.entity.UserSentenceEntity

@Database(
    entities = arrayOf(SentenceEntity::class, UserProgressEntity::class, UserSentenceEntity::class),
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun sentenceDao(): SentenceDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun userSentenceDao(): UserSentenceDao
}
