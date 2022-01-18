package com.example.dictionaryapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapters.searchAdapter.SearchDefAdapter
import com.example.dictionaryapp.databinding.FragmentSearchBinding
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.util.Constants.Companion.INFO_BUNDLE_ID
import com.example.dictionaryapp.util.Constants.Companion.SEARCH_WORD_TIME_DELAY
import com.example.dictionaryapp.viewmodel.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var word: String = "Word is not found"
    private var translation: String = "Translation is not found"
    private var example: String = "Example is not found"

    lateinit var translationsList: List<Array<String>>

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var searchDefAdapter: SearchDefAdapter
    private val viewModel: DictionaryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        searchDefAdapter = SearchDefAdapter(
            object : SearchDefAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    if (!translationsList.isNullOrEmpty()){
                        val bundle = Bundle()
                        bundle.putStringArray(INFO_BUNDLE_ID, translationsList[position])
                        val wordFragment = WordFragment()
                        wordFragment.arguments = bundle
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.searchFragment, wordFragment)
                            commit()
                        }
                    }
                }
            },
            object : SearchDefAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

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

                translationsList = mutableListOf()
                response.forEach {
                    word = it.text
                    if (!it.tr.isNullOrEmpty())
                        translation = it.tr[0].text
                    else
                        translation = "Translation is not found"
                    if (!it.tr[0].ex.isNullOrEmpty())
                        example = it.tr[0].ex[0].text
                    else example = "Example is not found"
                    (translationsList as MutableList<Array<String>>).add(arrayOf(word, translation, example))
                }
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
}