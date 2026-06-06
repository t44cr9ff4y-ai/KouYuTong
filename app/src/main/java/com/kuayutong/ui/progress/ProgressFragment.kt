package com.kuayutong.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kuayutong.KouYuTongApplication
import com.kuayutong.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProgressViewModel by viewModels {
        ProgressViewModelFactory(KouYuTongApplication.database)
    }

    private lateinit var achievementAdapter: AchievementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup achievements RecyclerView
        achievementAdapter = AchievementAdapter(emptyList())
        binding.rvAchievements.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAchievements.adapter = achievementAdapter

        setupObservers()
        viewModel.loadProgress()
    }

    private fun setupObservers() {
        viewModel.totalPractice.observe(viewLifecycleOwner) { total ->
            binding.tvTotalPracticeValue.text = total.toString()
        }

        viewModel.accuracy.observe(viewLifecycleOwner) { accuracy ->
            binding.tvAccuracyValue.text = "$accuracy%"
        }

        viewModel.mastered.observe(viewLifecycleOwner) { mastered ->
            binding.tvMasteredValue.text = mastered.toString()
        }

        viewModel.streak.observe(viewLifecycleOwner) { streak ->
            binding.tvStreakValue.text = streak.toString()
        }

        viewModel.overallProgress.observe(viewLifecycleOwner) { progress ->
            binding.progressOverall.progress = progress
            binding.tvOverallPercent.text = "$progress%"
        }

        viewModel.achievements.observe(viewLifecycleOwner) { achievements ->
            achievementAdapter = AchievementAdapter(achievements)
            binding.rvAchievements.adapter = achievementAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
