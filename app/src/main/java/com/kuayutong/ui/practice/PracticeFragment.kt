package com.kuayutong.ui.practice

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kuayutong.R
import com.kuayutong.databinding.FragmentPracticeBinding
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
            // TODO: open settings fragment/dialog
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

    override fun onDestroyView() {
        super.onDestroyView()
        TtsManager.stop()
        _binding = null
    }
}
