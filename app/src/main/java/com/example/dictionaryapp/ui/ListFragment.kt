package com.example.dictionaryapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapters.listAdapter.ListDefAdapter
import com.example.dictionaryapp.databinding.FragmentListBinding
import com.example.dictionaryapp.model.Def
import com.example.dictionaryapp.util.Constants.Companion.INFO_BUNDLE_ID
import com.example.dictionaryapp.viewmodel.DictionaryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var listDefAdapter: ListDefAdapter
    private val viewModel: DictionaryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        listDefAdapter = ListDefAdapter(
            object : ListDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    val bundle = Bundle()
                    bundle.putStringArray(INFO_BUNDLE_ID, parseDefToArray(def))
                    val wordFragment = WordFragment()
                    wordFragment.arguments = bundle
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.listFragment, wordFragment)
                        commit()
                    }
                }
            },
            object : ListDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    viewModel.deleteWord(def)
                    Toast.makeText(context, "Word ${def.text} is deleted from learning list", Toast.LENGTH_SHORT).show()
                }
            },
            object : ListDefAdapter.OnItemClickListener {
                override fun onItemClick(def: Def) {
                    Toast.makeText(context, "Notification is set", Toast.LENGTH_SHORT)
                }
            }
        )

        setUpRecyclerView(listDefAdapter)

        viewModel.getAllWords().observe(viewLifecycleOwner, { words ->
            binding.apply {
                listDefAdapter.submitList(words)
            }
        })

        viewModel.getSize().observe(viewLifecycleOwner, { size ->
            binding.learningWordsTv.text = "Count of learning words: $size"
        })

        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setUpRecyclerView(listDefAdapter: ListDefAdapter){
        binding.listRv.apply {
            adapter = listDefAdapter
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