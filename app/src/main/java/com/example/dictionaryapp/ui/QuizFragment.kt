package com.example.dictionaryapp.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.dictionaryapp.R
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
                if (translations.isNotEmpty())
                    setQuiz(word1Layout, word1Tv, translations[0].text)
                if (translations.size > 1)
                    setQuiz(word2Layout, word2Tv, translations[1].text)
                if (translations.size > 2)
                    setQuiz(word3Layout, word3Tv, translations[2].text)
                if (translations.size > 3)
                    setQuiz(word4Layout, word4Tv, translations[3].text)
                if (translations.size > 4)
                    setQuiz(word5Layout, word5Tv, translations[4].text)
            }
            answers = translations.map {
                it.tr[0].text
            }
        })

        binding.checkAnswersButton.setOnClickListener {
            if (binding.checkAnswersButton.text == "Refresh Quiz"){
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.quizFragment, QuizFragment())
                    commit()
                }
            }
            else {
                if (answers.isNotEmpty())
                    checkAnswer(binding.word1Tv, binding.word1Et, answers[0])
                if (answers.size > 1)
                    checkAnswer(binding.word2Tv, binding.word2Et, answers[1])
                if (answers.size > 2)
                    checkAnswer(binding.word3Tv, binding.word3Et, answers[2])
                if (answers.size > 3)
                    checkAnswer(binding.word4Tv, binding.word4Et, answers[3])
                if (answers.size > 4)
                    checkAnswer(binding.word5Tv, binding.word5Et, answers[4])

                if (countOfRightAnswers == 1)
                    Toast.makeText(context, "You have $countOfRightAnswers right answer!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "You have $countOfRightAnswers right answers!", Toast.LENGTH_SHORT).show()

                binding.checkAnswersButton.text = "Refresh Quiz"
            }
        }
        return view
    }

    private fun checkAnswer(tv: TextView, et: EditText, answer: String){
        if (et.text.toString().trim().lowercase() == answer){
            countOfRightAnswers++
            //tv.setTextColor(Color.GREEN)
            et.setTextColor(Color.GREEN)
        } else {
            et.setText(answer)
            //tv.setTextColor(Color.RED)
            et.setTextColor(Color.RED)
        }
    }

    private fun setQuiz(ll: LinearLayout, tv: TextView, word: String){
        ll.isVisible = true
        tv.text = word
    }
}