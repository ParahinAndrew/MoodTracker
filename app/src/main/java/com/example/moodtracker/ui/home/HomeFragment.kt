package com.example.moodtracker.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R
import com.example.moodtracker.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isMoodListFragmentVisible = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_container_in_home) as NavHostFragment
        Log.d("HomeFragment", "navHostFragment: ${navHostFragment.id}")

        // Инициализация с MoodListFragment
        navHostFragment.findNavController().navigate(R.id.moodListFragment)
        Log.d("HomeFragment", "navHostFragment: ${navHostFragment.id}")

        binding.toolbarButton.setOnClickListener {
            if (isMoodListFragmentVisible) {
                // Переход к MoodCalendarFragment
                navHostFragment.findNavController().navigate(R.id.moodCalendarFragment)
                Log.d("HomeFragment", "navHostFragment: ${navHostFragment.id}")
            } else {
                // Переход к MoodListFragment
                navHostFragment.findNavController().navigate(R.id.moodListFragment)
                Log.d("HomeFragment", "navHostFragment: ${navHostFragment.id}")
            }
            // Инвертируем флаг
            isMoodListFragmentVisible = !isMoodListFragmentVisible
        }
    }

}