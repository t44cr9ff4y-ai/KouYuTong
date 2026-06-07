package com.kuayutong.ui.practice

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kuayutong.R
import com.kuayutong.databinding.FragmentPracticeBinding
import com.kuayutong.util.PracticeSettings
import com.kuayutong.util.TtsManager

class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null
    private val binding get() = _binding!!

    private val viewModelFactory by lazy {
        PracticeViewModelFactory()
    }
    private val viewModel: PracticeViewModel by viewModels { viewModelFactory }

    private var selectedLevelCode = "A1_ENTRY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Apply saved settings to ViewModel
        val practiceLimit = PracticeSettings.getDailyPracticeLimit(requireContext())
        val reviewLimit = PracticeSettings.getDailyReviewLimit(requireContext())
        viewModel.setDailyLimits(practiceLimit, reviewLimit)
        
        setupObservers()
        setupListeners()
        viewModel.loadSentencesByLevel(selectedLevelCode)
    }

    private fun setupObservers() {
        viewModel.currentSentence.observe(viewLifecycleOwner) { sentence ->
            if (sentence != null) {
                binding.tvChineseSentence.text = sentence.chineseSentence
                binding.etEnglishInput.text?.clear()
                binding.btnNext.isEnabled = false
                binding.tvResult.text = ""
            }
        }

        viewModel.resultMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                binding.tvResult.text = message
                binding.tvResult.visibility = View.VISIBLE
                if (message == "正确！") {
                    binding.tvResult.setTextColor(resources.getColor(R.color.success, null))
                    viewModel.currentSentence.value?.let { sentence ->
                        TtsManager.speak(sentence.englishAnswer)
                    }
                } else {
                    binding.tvResult.setTextColor(resources.getColor(R.color.error, null))
                }
            } else {
                binding.tvResult.visibility = View.GONE
            }
        }

        viewModel.showHint.observe(viewLifecycleOwner) { show ->
            binding.btnHint.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.hintMessage.observe(viewLifecycleOwner) { hint ->
            if (hint.isNotEmpty()) {
                binding.tvResult.text = hint
                binding.tvResult.setTextColor(resources.getColor(R.color.warning, null))
                viewModel.currentSentence.value?.let { sentence ->
                    TtsManager.speak(sentence.englishAnswer)
                }
            }
        }

        viewModel.canGoNext.observe(viewLifecycleOwner) { canGo ->
            binding.btnNext.isEnabled = canGo
        }

        // Progress text observer
        viewModel.progressText.observe(viewLifecycleOwner) { progress ->
            binding.tvProgress.text = progress
        }

        // Mode switch observer
        viewModel.currentMode.observe(viewLifecycleOwner) { mode ->
            when (mode) {
                PracticeMode.MODE_PRACTICE -> {
                    binding.btnModePractice.setBackgroundTintList(
                        resources.getColorStateList(R.color.primary, null)
                    )
                    binding.btnModeReview.setBackgroundTintList(
                        resources.getColorStateList(R.color.text_secondary, null)
                    )
                }
                PracticeMode.MODE_REVIEW -> {
                    binding.btnModePractice.setBackgroundTintList(
                        resources.getColorStateList(R.color.text_secondary, null)
                    )
                    binding.btnModeReview.setBackgroundTintList(
                        resources.getColorStateList(R.color.primary, null)
                    )
                }
                null -> {}
            }
        }
    }

    private fun setupListeners() {
        binding.btnSubmit.setOnClickListener {
            val answer = binding.etEnglishInput.text?.toString() ?: ""
            if (answer.isNotEmpty()) {
                viewModel.submitAnswer(answer)
            }
        }

        binding.btnHint.setOnClickListener {
            viewModel.showAnswer()
        }

        binding.btnNext.setOnClickListener {
            viewModel.goToNext()
        }

        binding.btnPrev.setOnClickListener {
            viewModel.goToPrev()
        }

        binding.btnLevelSelector.setOnClickListener {
            showLevelSelectorDialog()
        }

        // Mode switch buttons
        binding.btnModePractice.setOnClickListener {
            viewModel.switchMode(PracticeMode.MODE_PRACTICE)
        }

        binding.btnModeReview.setOnClickListener {
            viewModel.switchMode(PracticeMode.MODE_REVIEW)
        }

        // Settings button
        binding.btnSettings.setOnClickListener {
            showSettingsDialog()
        }
    }

    private fun showLevelSelectorDialog() {
        val levels = arrayOf(
            "入门级A1" to "A1_ENTRY",
            "初级A1" to "A1_BASIC",
            "高级A1" to "A1_ADVANCED",
            "A2" to "A2",
            "初级B1" to "B1_BASIC",
            "高级B1" to "B1_ADVANCED",
            "初级B2" to "B2_BASIC",
            "高级B2" to "B2_ADVANCED",
            "C1" to "C1",
            "C2" to "C2"
        )

        val levelNames = levels.map { it.first }.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("选择等级")
            .setItems(levelNames) { _, which ->
                selectedLevelCode = levels[which].second
                binding.btnLevelSelector.text = "等级: ${levels[which].first}"
                viewModel.loadSentencesByLevel(selectedLevelCode)
            }
            .show()
    }

    private fun showSettingsDialog() {
        val context = requireContext()
        val currentPractice = PracticeSettings.getDailyPracticeLimit(context)
        val currentReview = PracticeSettings.getDailyReviewLimit(context)
        
        // Build practice options: 10, 20, 30, ..., 100
        val practiceOptions = (PracticeSettings.MIN_PRACTICE_LIMIT..PracticeSettings.MAX_PRACTICE_LIMIT step PracticeSettings.PRACTICE_STEP).toList()
        val reviewOptions = (PracticeSettings.MIN_REVIEW_LIMIT..PracticeSettings.MAX_REVIEW_LIMIT step PracticeSettings.REVIEW_STEP).toList()
        
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }
        
        // Practice limit label
        layout.addView(TextView(context).apply {
            text = "每日学习新句数量（1-100，每10句一档）："
            textSize = 14f
            setPadding(0, 0, 0, 8)
        })
        
        // Practice limit spinner
        val practiceSpinner = Spinner(context).apply {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, 
                practiceOptions.map { "${it}句" })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            setAdapter(adapter)
            setSelection(practiceOptions.indexOf(currentPractice).coerceAtLeast(0))
        }
        layout.addView(practiceSpinner)
        
        // Spacer
        layout.addView(View(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 24)
        })
        
        // Review limit label
        layout.addView(TextView(context).apply {
            text = "每日复习旧句数量（1-200，每20句一档）："
            textSize = 14f
            setPadding(0, 0, 0, 8)
        })
        
        // Review limit spinner
        val reviewSpinner = Spinner(context).apply {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item,
                reviewOptions.map { "${it}句" })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            setAdapter(adapter)
            setSelection(reviewOptions.indexOf(currentReview).coerceAtLeast(0))
        }
        layout.addView(reviewSpinner)
        
        AlertDialog.Builder(context)
            .setTitle("练习设置")
            .setView(layout)
            .setPositiveButton("保存") { _, _ ->
                val newPractice = practiceOptions[practiceSpinner.selectedItemPosition]
                val newReview = reviewOptions[reviewSpinner.selectedItemPosition]
                
                PracticeSettings.setDailyPracticeLimit(context, newPractice)
                PracticeSettings.setDailyReviewLimit(context, newReview)
                viewModel.setDailyLimits(newPractice, newReview)
                
                // Reload with new limits
                when (viewModel.currentMode.value) {
                    PracticeMode.MODE_PRACTICE -> viewModel.switchMode(PracticeMode.MODE_PRACTICE)
                    PracticeMode.MODE_REVIEW -> viewModel.switchMode(PracticeMode.MODE_REVIEW)
                    else -> {}
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        TtsManager.stop()
        _binding = null
    }
}
