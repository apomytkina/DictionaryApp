package com.example.dictionaryapp.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
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
import com.example.dictionaryapp.databinding.FragmentQuizBinding
import com.example.dictionaryapp.util.Constants.Companion.GREEN_COLOR_HASH
import com.example.dictionaryapp.util.Constants.Companion.RED_COLOR_HASH
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

        getRandomWords()

        binding.checkAnswersButton.setOnClickListener {
            if (binding.checkAnswersButton.text == "Refresh Quiz"){
                getRandomWords()
                binding.checkAnswersButton.text = "Check Quiz"
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun checkAnswer(tv: TextView, et: EditText, answer: String){
        if (et.text.toString().trim().lowercase() == answer){
            countOfRightAnswers++
            et.setTextColor(Color.parseColor(GREEN_COLOR_HASH))
        } else {
            et.setText(answer)
            et.setTextColor(Color.parseColor(RED_COLOR_HASH))
        }
    }

    private fun setQuiz(ll: LinearLayout, tv: TextView, et: EditText, word: String, pos: String){
        ll.isVisible = true
        val posSpannable = SpannableString("$word\n($pos)")

        posSpannable.setSpan(
                RelativeSizeSpan(0.7f),
                word.length+1,
                word.length+pos.length+3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        posSpannable.setSpan(
            StyleSpan(Typeface.ITALIC),
            word.length+1,
            word.length+pos.length+3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tv.text = posSpannable
        et.setText("")
        et.setTextColor(Color.BLACK)
    }

    private fun getRandomWords(){
        viewModel.getRandomTranslations().observe(viewLifecycleOwner, { translations ->
            binding.apply {
                if (translations.isNotEmpty())
                    setQuiz(word1Layout, word1Tv, word1Et, translations[0].text, translations[0].pos)
                if (translations.size > 1)
                    setQuiz(word2Layout, word2Tv, word2Et, translations[1].text, translations[1].pos)
                if (translations.size > 2)
                    setQuiz(word3Layout, word3Tv, word3Et, translations[2].text, translations[2].pos)
                if (translations.size > 3)
                    setQuiz(word4Layout, word4Tv, word4Et, translations[3].text, translations[3].pos)
                if (translations.size > 4)
                    setQuiz(word5Layout, word5Tv, word5Et, translations[4].text, translations[4].pos)
            }
            answers = translations.map {
                it.tr[0].text
            }
        })
    }
}