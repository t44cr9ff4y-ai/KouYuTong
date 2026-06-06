package com.kuayutong

import android.app.Application
import androidx.room.Room
import com.kuayutong.data.AppDatabase
import com.kuayutong.data.seed.SentenceSeeder
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
        ).fallbackToDestructiveMigration()
            .build()

        // Seed database on first launch
        CoroutineScope(Dispatchers.IO).launch {
            val totalCount = database.sentenceDao().getTotalSentenceCount()
            if (totalCount == 0) {
                SentenceSeeder.seedDatabase(database)
            }
        }
    }
}
