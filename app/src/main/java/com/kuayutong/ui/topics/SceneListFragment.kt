package com.kuayutong.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuayutong.KouYuTongApplication
import com.kuayutong.R
import kotlinx.coroutines.launch

class SceneListFragment : Fragment() {

    private var levelCode: String = "A1_ENTRY"
    private var levelName: String = "入门级A1"
    private lateinit var rvScenes: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvPercent: TextView

    companion object {
        fun newInstance(levelCode: String, levelName: String): SceneListFragment {
            val fragment = SceneListFragment()
            fragment.levelCode = levelCode
            fragment.levelName = levelName
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_scene_list, container, false)
        rvScenes = view.findViewById(R.id.rv_scenes)
        tvTitle = view.findViewById(R.id.tv_level_title)
        progressBar = view.findViewById(R.id.progress_level)
        tvPercent = view.findViewById(R.id.tv_level_percent)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.text = levelName

        val database = KouYuTongApplication.database

        lifecycleScope.launch {
            val scenes = database.sentenceDao().getScenesByLevel(levelCode)
            val totalSentences = database.sentenceDao().getSentenceCountByLevel(levelCode)
            val completed = database.userSentenceDao().getMasteredSentenceCountByLevel(levelCode)
            val percent = if (totalSentences > 0) (completed * 100 / totalSentences) else 0

            progressBar.progress = percent
            tvPercent.text = "$percent%"

            val adapter = SceneAdapter(scenes, levelCode, database) { sceneInfo ->
                // Navigate to practice with this scene
                // For now, show a simple dialog
            }
            rvScenes.layoutManager = LinearLayoutManager(requireContext())
            rvScenes.adapter = adapter
        }
    }
}
