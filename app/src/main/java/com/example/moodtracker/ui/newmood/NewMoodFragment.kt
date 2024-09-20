package com.example.moodtracker.ui.newmood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.domain.models.MoodCardDomain
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.databinding.FragmentNewMoodBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class NewMoodFragment : Fragment() {

    private lateinit var binding: FragmentNewMoodBinding

    private val viewModel: NewMoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMoodBinding.inflate(inflater)

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.thisDateText.text = LocalDateTime.now().toString()

        binding.saveButton.setOnClickListener {
            viewModel.mood.value = MoodCardDomain(
                title = binding.titleEditText.text.toString() ?: "No title",
                mood = binding.moodEditText.text.toString() ?: "No mood",
                time = LocalDateTime.now(),
            )

            viewModel.saveMood(viewModel.mood.value!!)

            findNavController().navigateUp()
        }



    }

}