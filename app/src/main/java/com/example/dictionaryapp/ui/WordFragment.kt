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
                if (translation[0] == "Word is not found")
                    binding.translationTv.typeface = Typeface.DEFAULT
                binding.translationTv.text = translation[1]
                if (translation[1] == "Translation is not found")
                    binding.translationTv.typeface = Typeface.DEFAULT
                binding.exampleTv.text = translation[2]
                if (translation[2] == "Example is not found")
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