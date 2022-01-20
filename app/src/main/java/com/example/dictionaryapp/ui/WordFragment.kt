package com.example.dictionaryapp.ui

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionaryapp.databinding.FragmentWordBinding
import com.example.dictionaryapp.util.Constants.Companion.INFO_BUNDLE_ID

class WordFragment : Fragment() {
    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        val view = binding.root

        arguments?.let {
            val translation = it.getStringArray(INFO_BUNDLE_ID)
            if (translation != null){
                binding.wordTitleTv.text = translation[0]
                binding.partOfSpeechTv.text = "(${translation[1]})"
                binding.transcriptionTv.text = "[${translation[2]}]"
                binding.translationTv.text = translation[3]
                binding.exampleTv.text = translation[4]
                if (translation[2] != "No Transcription")
                    binding.transcriptionTv.typeface = Typeface.DEFAULT
                if (translation[3] != "No Translation")
                    binding.translationTv.typeface = Typeface.DEFAULT
                if (translation[4] != "No Example")
                    binding.exampleTv.typeface = Typeface.DEFAULT
            }
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}