package com.example.midtermproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Shared view model, share livedata between objects
 */
class SharedVM : ViewModel() {
    //Shared name data
    private val name = MutableLiveData<String>()

    fun setName(str : String) {
        name.value = str
    }

    fun getName() : LiveData<String> {
        return name
    }


    //Shared score data
    private val score = MutableLiveData<Int>()

    fun setScore(value : Int) {
        score.value = value
    }

    fun getScore() : LiveData<Int> {
        return score
    }
}
