package com.example.midtermproject

import androidx.recyclerview.widget.DiffUtil


/**
 *  Implementation for the DiffUtil for scores
 */

class ScoreDiffUtil (private val oldList : List<Score>, private val newList : List<Score>)
    : DiffUtil.Callback() {


    /**
     * Returns size of old list
     */

    override fun getOldListSize(): Int {
        return oldList.count()
    }


    /**
     * Returns size of new list
     */

    override fun getNewListSize(): Int {
        return newList.count()
    }

    /**
     * Checks if scores are the same
     */

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }


    /**
     * Updates individual scores if something is changed
     */

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }
            oldList[oldItemPosition].scoreValue != newList[newItemPosition].scoreValue -> {
                false
            }
            else -> true
        }
    }
}