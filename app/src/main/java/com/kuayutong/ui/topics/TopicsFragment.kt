package com.kuayutong.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuayutong.KouYuTongApplication
import com.kuayutong.R
import com.kuayutong.data.model.CefrLevel
import com.kuayutong.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    private var _binding: FragmentTopicsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TopicsViewModel by viewModels {
        TopicsViewModelFactory(KouYuTongApplication.database)
    }

    private lateinit var adapter: LevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.loadLevels()
    }

    private fun setupRecyclerView() {
        adapter = LevelAdapter { levelItem ->
            // Navigate to scene list for this level
            val sceneFragment = SceneListFragment.newInstance(levelItem.level.code, levelItem.level.displayName)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, sceneFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.rvLevels.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLevels.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.levels.observe(viewLifecycleOwner) { levels ->
            adapter.submitList(levels)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
