package com.kuayutong.data.model

import com.kuayutong.R

enum class CefrLevel(
    val displayName: String,
    val order: Int,
    val iconRes: Int
) {
    A1_ENTRY("入门级A1", 1, R.drawable.ic_level_a1_entry),
    A1_BASIC("初级A1", 2, R.drawable.ic_level_a1_basic),
    A1_ADVANCED("高级A1", 3, R.drawable.ic_level_a1_advanced),
    A2("A2", 4, R.drawable.ic_level_a2),
    B1_BASIC("初级B1", 5, R.drawable.ic_level_b1_basic),
    B1_ADVANCED("高级B1", 6, R.drawable.ic_level_b1_advanced),
    B2_BASIC("初级B2", 7, R.drawable.ic_level_b2_basic),
    B2_ADVANCED("高级B2", 8, R.drawable.ic_level_b2_advanced),
    C1("C1", 9, R.drawable.ic_level_c1),
    C2("C2", 10, R.drawable.ic_level_c2),
    NCE1("新概念1", 11, R.mipmap.ic_level_nce1),
    NCE2("新概念2", 12, R.mipmap.ic_level_nce2),
    NCE3("新概念3", 13, R.mipmap.ic_level_nce3),
    NCE4("新概念4", 14, R.mipmap.ic_level_nce4);

    companion object {
        fun getAllLevels(): List<CefrLevel> = entries.sortedBy { it.order }

        fun fromString(levelStr: String): CefrLevel {
            return entries.find { it.name == levelStr } ?: A1_ENTRY
        }
    }
}
