package com.example.dictionaryapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.dictionaryapp.databinding.FragmentQuizBinding
import com.example.dictionaryapp.viewmodel.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var countOfRightAnswers = 0
    private lateinit var answers: List<String>

    private val viewModel: DictionaryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.getRandomTranslations().observe(viewLifecycleOwner, { translations ->
            binding.apply {
                when (translations.size) {
                    0 -> Toast.makeText(context, "Save Words to start a quiz!", Toast.LENGTH_SHORT)
                        .show()
                    1 -> {
                        word1Tv.text = translations[0].text
                        word2Tv.text = ""
                        word3Tv.text = ""
                        word4Tv.text = ""
                        word5Tv.text = ""
                    }
                    2 -> {
                        word1Tv.text = translations[0].text
                        word2Tv.text = translations[1].text
                        word3Tv.text = ""
                        word4Tv.text = ""
                        word5Tv.text = ""
                    }
                    3 -> {
                        word1Tv.text = translations[0].text
                        word2Tv.text = translations[1].text
                        word3Tv.text = translations[2].text
                        word4Tv.text = ""
                        word5Tv.text = ""
                    }
                    4 -> {
                        word1Tv.text = translations[0].text
                        word2Tv.text = translations[1].text
                        word3Tv.text = translations[2].text
                        word4Tv.text = translations[3].text
                        word5Tv.text = ""
                    }
                    else -> {
                        word1Tv.text = translations[0].text
                        word2Tv.text = translations[1].text
                        word3Tv.text = translations[2].text
                        word4Tv.text = translations[3].text
                        word5Tv.text = translations[4].text
                    }
                }
            }
            answers = translations.map {
                it.tr[0].text
            }
        })

        binding.checkAnswersButton.setOnClickListener {
            if (answers.isNotEmpty() && binding.word1Et.text.toString().trim() == answers[0])
                countOfRightAnswers++
            if (answers.size > 1 && binding.word2Et.text.toString().trim() == answers[1])
                countOfRightAnswers++
            if (answers.size > 2 && binding.word1Et.text.toString().trim() == answers[2])
                countOfRightAnswers++
            if (answers.size > 3 && binding.word1Et.text.toString().trim() == answers[3])
                countOfRightAnswers++
            if (answers.size > 4 && binding.word1Et.text.toString().trim() == answers[4])
                countOfRightAnswers++
            Toast.makeText(context, "You've completed $countOfRightAnswers translations right!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}