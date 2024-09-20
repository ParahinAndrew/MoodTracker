package com.example.moodtracker.ui.home.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.MoodCardDomain
import com.example.moodtracker.R
import com.example.moodtracker.databinding.FragmentMoodListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoodListFragment : Fragment() {

    private lateinit var binding: FragmentMoodListBinding

    private val viewModel: MoodListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoodListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        viewModel.moods.observe(viewLifecycleOwner) { moods ->

            (binding.moodRecyclerView.adapter as MoodListAdapter).submitList(moods)

            binding.noMoodText.visibility = if (moods.isEmpty()) View.VISIBLE else View.GONE
        }


    }

    private fun initAdapter() {
        binding.moodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.moodRecyclerView.adapter = MoodListAdapter(
            onItemClick = { mood ->
                Toast.makeText(requireContext(), "Clicked on: ${mood.title}", Toast.LENGTH_SHORT).show()
            },
            onThreeDotsClick = { mood ->
                Toast.makeText(requireContext(), "Clicked on three dots for: ${mood.title}", Toast.LENGTH_SHORT).show()
                viewModel.deleteAndUpdateMood(mood)
            }
        ).apply {
            submitList(viewModel.moods.value ?: emptyList())
        }
        binding.moodRecyclerView.addItemDecoration(MoodItemDecoration(16))
    }

}