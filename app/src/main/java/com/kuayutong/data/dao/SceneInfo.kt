package com.kuayutong.data.dao

/**
 * Simple data class for scene list display (used in DAO query results).
 * Maps to: SELECT sceneId, sceneName FROM sentences WHERE cefrLevel = :level
 */
data class SceneInfo(
    val sceneId: Int,
    val sceneName: String
)
