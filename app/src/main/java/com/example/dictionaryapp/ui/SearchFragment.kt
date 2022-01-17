package com.example.dictionaryapp.ui

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.FragmentListBinding
import com.example.dictionaryapp.databinding.FragmentSearchBinding
import com.example.dictionaryapp.util.Constants.Companion.SEARCH_WORD_TIME_DELAY
import com.example.dictionaryapp.viewmodel.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DictionaryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        var job: Job? = null
        binding.searchWordEt.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_WORD_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty())
                        viewModel.getTranslation(editable.toString())
                }
            }
        }

        viewModel.def.observe(viewLifecycleOwner, Observer { response ->
            binding.apply {
                //wordsAdapter.submitList(response)
            }
        })

        return view
    }
}