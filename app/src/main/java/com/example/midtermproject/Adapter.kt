package com.example.midtermproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermproject.databinding.ScoreCardBinding

/**
 * Adapter adapts Score data into ViewHolder score_card
 */
class Adapter(private val context: Context, private var scores: List<Score>, private val listener: ScoreClickListener) : RecyclerView.Adapter<ViewHolder>() {

    /**
     * Creates a ViewHolder for the RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScoreCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Binds the ViewHolder's data to a Score
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val score : Score = scores[position]
        val text = "${score.scoreValue} - ${score.name}"
        holder.nameView.text = text


        holder.deleteButton.setOnClickListener {
            listener.onDeletePress(scores[position])
        }
    }

    /**
     * Returns number of Scores in adapter
     */
    override fun getItemCount(): Int {
        return scores.size
    }

    /**
     * DiffUtil update the ViewHolder
     */
    fun setData(newScoreList : List<Score>) {
        val sortedScoreList = newScoreList.sortedBy { it.scoreValue }
        val diffUtil = ScoreDiffUtil(scores, sortedScoreList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        scores = sortedScoreList
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Listeners for clicking on the score
     */
    interface ScoreClickListener {
        fun onDeletePress(score: Score)
    }


}
