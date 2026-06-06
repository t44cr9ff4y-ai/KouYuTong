package com.kuayutong.ui.topics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuayutong.data.model.CefrLevel
import com.kuayutong.databinding.ItemLevelBinding

class LevelAdapter(
    private val onItemClick: (LevelItem) -> Unit
) : ListAdapter<LevelItem, LevelAdapter.LevelViewHolder>(LevelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val binding = ItemLevelBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LevelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LevelViewHolder(
        private val binding: ItemLevelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LevelItem) {
            binding.tvLevelName.text = item.level.displayName
            binding.progressBar.progress = item.progressPercent
            binding.tvProgressPercent.text = "${item.progressPercent}%"
            binding.tvCompleted.text = "${item.completedSentences}/${item.totalSentences}"

            // Use level-specific icon
            val cefrLevel = CefrLevel.fromString(item.level.code)
            if (cefrLevel.iconRes != 0) {
                binding.ivLevelIcon.setImageResource(cefrLevel.iconRes)
            }

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}

class LevelDiffCallback : DiffUtil.ItemCallback<LevelItem>() {
    override fun areItemsTheSame(oldItem: LevelItem, newItem: LevelItem): Boolean {
        return oldItem.level == newItem.level
    }

    override fun areContentsTheSame(oldItem: LevelItem, newItem: LevelItem): Boolean {
        return oldItem == newItem
    }
}
