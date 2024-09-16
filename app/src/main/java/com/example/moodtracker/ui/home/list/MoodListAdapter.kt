package com.example.moodtracker.ui.home.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.MoodCardDomain
import com.example.moodtracker.databinding.ItemMoodCardBinding

class MoodListAdapter(
    private val onItemClick: (MoodCardDomain) -> Unit,
    private val onThreeDotsClick: (MoodCardDomain) -> Unit
) : ListAdapter<MoodCardDomain, MoodListAdapter.MoodListViewHolder>(MoodDiffCallback()) {

    inner class MoodListViewHolder(private val binding: ItemMoodCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mood: MoodCardDomain) {
            binding.imageMood.setImageResource(mood.imageResId)
            binding.textTitleMood.text = mood.title
            binding.textDataMood.text = mood.time.toString()
            binding.textDescriptionMood.text = mood.mood

            // Устанавливаем слушатель клика на весь элемент
            itemView.setOnClickListener {
                onItemClick(mood)
            }

            // Устанавливаем слушатель клика на image_three_dots
            binding.imageThreeDots.setOnClickListener {
                onThreeDotsClick(mood)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMoodCardBinding.inflate(inflater, parent, false)
        return MoodListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MoodListViewHolder, position: Int) {
        val mood = getItem(position)
        holder.bind(mood)
    }

    private class MoodDiffCallback : DiffUtil.ItemCallback<MoodCardDomain>() {
        override fun areItemsTheSame(oldItem: MoodCardDomain, newItem: MoodCardDomain): Boolean {
            return oldItem.imageResId == newItem.imageResId
        }

        override fun areContentsTheSame(oldItem: MoodCardDomain, newItem: MoodCardDomain): Boolean {
            return oldItem == newItem
        }
    }
}