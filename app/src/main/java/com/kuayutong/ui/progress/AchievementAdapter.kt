package com.kuayutong.ui.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuayutong.R

class AchievementAdapter(
    private val achievements: List<Achievement>
) : RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(achievements[position])
    }

    override fun getItemCount(): Int = achievements.size

    inner class ViewHolder(android.view.View) : RecyclerView.ViewHolder(android.view.View) {
        private val tvEmoji: TextView = itemView.findViewById(R.id.tv_achievement_emoji)
        private val tvName: TextView = itemView.findViewById(R.id.tv_achievement_name)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_achievement_desc)

        fun bind(achievement: Achievement) {
            if (achievement.unlocked) {
                tvEmoji.text = achievement.emoji
                tvName.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_primary))
                tvDesc.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_secondary))
                tvDesc.text = achievement.description
            } else {
                tvEmoji.text = "🔒"
                tvName.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_hint))
                tvDesc.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_hint))
                tvDesc.text = achievement.description
            }
            tvName.text = achievement.name
        }
    }
}
