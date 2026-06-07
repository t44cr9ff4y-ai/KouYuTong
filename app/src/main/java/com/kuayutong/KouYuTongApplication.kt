package com.kuayutong

import android.app.Application
import androidx.room.Room
import com.kuayutong.data.AppDatabase
import com.kuayutong.data.seed.SentenceSeeder
import com.kuayutong.util.TtsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KouYuTongApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "kuayutong_database"
        ).addMigrations(AppDatabase.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()

        // Initialize TTS for American English pronunciation
        TtsManager.init(this)

        // Seed database on first launch
        CoroutineScope(Dispatchers.IO).launch {
            val totalCount = database.sentenceDao().getTotalSentenceCount()
            if (totalCount == 0) {
                SentenceSeeder.seedDatabase(database)
            }
        }
    }
}
