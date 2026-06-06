package com.kuayutong.ui.topics

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.kuayutong.R
import com.kuayutong.data.AppDatabase
import com.kuayutong.data.dao.SceneInfo
import kotlinx.coroutines.launch

class SceneAdapter(
    private val scenes: List<SceneInfo>,
    private val levelCode: String,
    private val database: AppDatabase,
    private val onItemClick: (SceneInfo) -> Unit
) : RecyclerView.Adapter<SceneAdapter.SceneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scene, parent, false)
        return SceneViewHolder(view)
    }

    override fun onBindViewHolder(holder: SceneViewHolder, position: Int) {
        val scene = scenes[position]
        holder.bind(scene)
    }

    override fun getItemCount(): Int = scenes.size

    inner class SceneViewHolder(android.view.View) : RecyclerView.ViewHolder(android.view.View) {
        private val tvNumber: android.widget.TextView = itemView.findViewById(R.id.tv_scene_number)
        private val tvName: android.widget.TextView = itemView.findViewById(R.id.tv_scene_name)
        private val progressBar: android.widget.ProgressBar = itemView.findViewById(R.id.progress_scene)
        private val tvProgress: android.widget.TextView = itemView.findViewById(R.id.tv_scene_progress)
        private val tvPercent: android.widget.TextView = itemView.findViewById(R.id.tv_scene_percent)

        fun bind(scene: SceneInfo) {
            tvNumber.text = "${scenes.indexOf(scene) + 1}"
            tvName.text = scene.sceneName

            // Load progress for this scene
            val context = itemView.context
            kotlinx.coroutines.GlobalScope.launch {
                val total = database.sentenceDao().getSentenceCountByLevelAndScene(levelCode, scene.sceneId)
                val mastered = database.userSentenceDao().getMasteredCountByScene(levelCode, scene.sceneId)
                val percent = if (total > 0) (mastered * 100 / total) else 0

                tvNumber.post {
                    progressBar.progress = percent
                    tvProgress.text = "$mastered/$total 完成"
                    tvPercent.text = "$percent%"
                }
            }

            itemView.setOnClickListener {
                onItemClick(scene)
            }
        }
    }
}
