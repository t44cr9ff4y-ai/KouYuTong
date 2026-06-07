package com.kuayutong.data.seed

import com.kuayutong.data.AppDatabase

object SentenceSeeder {

    suspend fun seedDatabase(database: AppDatabase) {
        val totalCount = database.sentenceDao().getTotalSentenceCount()
        if (totalCount == 0) {
            // First launch: seed both CEFR and NCE data
            val cefr = SentenceData.generateAllSentences()
            database.sentenceDao().insertAll(cefr)
            val nce = NewConceptData.generateAllSentences()
            database.sentenceDao().insertAll(nce)
        } else {
            // Check if NCE data is already loaded (cefrLevel starts with "NCE")
            val nceCount = database.sentenceDao().getCountByLevelPrefix("NCE")
            if (nceCount == 0) {
                val nce = NewConceptData.generateAllSentences()
                database.sentenceDao().insertAll(nce)
            }
        }
    }
}
