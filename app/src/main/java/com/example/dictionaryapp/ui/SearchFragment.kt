package com.example.dictionaryapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.translationMatrix
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapters.searchAdapter.SearchDefAdapter
import com.example.dictionaryapp.databinding.FragmentSearchBinding
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.model.Ex
import com.example.dictionaryapp.model.Tr
import com.example.dictionaryapp.util.Constants.Companion.INFO_BUNDLE_ID
import com.example.dictionaryapp.util.Constants.Companion.SEARCH_WORD_TIME_DELAY
import com.example.dictionaryapp.viewmodel.DictionaryViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchDefAdapter: SearchDefAdapter
    private val viewModel: DictionaryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        searchDefAdapter = SearchDefAdapter(
            object : SearchDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    val bundle = Bundle()
                    bundle.putStringArray(INFO_BUNDLE_ID, parseDefToArray(def))
                    val wordFragment = WordFragment()
                    wordFragment.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.searchFragment, wordFragment)
                        commit()
                    }
                }
            },
            object : SearchDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    viewModel.saveWord(def)
                    Toast.makeText(context, "Word ${def.text} is added to learning list", Toast.LENGTH_SHORT).show()
                }
            }
        )

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

        setUpRecyclerView(searchDefAdapter)

        viewModel.def.observe(viewLifecycleOwner, Observer { response ->
            binding.apply {
                searchDefAdapter.submitList(response)
            }
        })
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecyclerView(searchAdapter: SearchDefAdapter){
        binding.searchRv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun parseDefToArray(def: Def): Array<String>{
        var translation = "Translation is not found"
        var example = "Example is not found"
        var word = def.text
        if (!def.tr.isNullOrEmpty())
            translation = def.tr[0].text
        if (!def.tr[0].ex.isNullOrEmpty())
            example = def.tr[0].ex[0].text
        return arrayOf(word, translation, example)
    }
}