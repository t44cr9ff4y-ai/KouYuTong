package com.kuayutong.data.seed

import com.kuayutong.data.AppDatabase

object SentenceSeeder {

    suspend fun seedDatabase(database: AppDatabase) {
        val sentences = SentenceData.generateAllSentences()
        database.sentenceDao().insertAll(sentences)
    }
}
