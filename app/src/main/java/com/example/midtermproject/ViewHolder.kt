package com.example.midtermproject

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.midtermproject.databinding.ScoreCardBinding



/**
 * A representation of a singular Score displayed in the RecyclerView
 */

class ViewHolder(private val binding: ScoreCardBinding) : RecyclerView.ViewHolder(binding.root) {
    var nameView: TextView = binding.title
    var deleteButton: ImageButton = binding.XButton
}