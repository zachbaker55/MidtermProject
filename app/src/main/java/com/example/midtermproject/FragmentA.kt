package com.example.midtermproject

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.midtermproject.databinding.FragmentABinding
import kotlin.random.Random


/**
 * Guessing game fragment
 */


const val LOWEST_GUESS = 1
const val HIGHEST_GUESS = 100

class FragmentA : Fragment(R.layout.fragment_a) {

    private lateinit var b: FragmentABinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentABinding.bind(view)

        //create a media player for sound effects
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.negative)
        var attempts = 0

        //shared view model
        val sharedVM = ViewModelProvider(requireActivity()).get(SharedVM::class.java)

        val correctGuess = getRandom()


        /**
         * Decrements guess by 1
         */
        b.minusButton.setOnClickListener {
            val text = b.enteredGuess.text.toString()
            if (text != "") {
                var guess = text.toInt()
                guess = clamp(guess - 1)
                b.enteredGuess.setText(guess.toString())
            }
        }

        /**
         * Increments guess by 1
         */
        b.plusButton.setOnClickListener {
            val text = b.enteredGuess.text.toString()
            if (text != "") {
                var guess = text.toInt()
                guess = clamp(guess + 1)
                b.enteredGuess.setText(guess.toString())
            }
        }

        /**
         * Check guess
         */
        b.buttonOK.setOnClickListener {
            val text = b.enteredGuess.text.toString()
            if (text != "") {
                val guess = text.toInt()
                when {

                    //Guess is correct
                    guess == correctGuess -> {
                        Toast.makeText(requireContext(), "WOW! You got it!", Toast.LENGTH_SHORT).show()
                        attempts++
                        var name = b.enteredName.text.toString()
                        if (name == "") {
                            name = "NoName"
                        }
                        sharedVM.setScore(attempts)
                        sharedVM.setName(name)
                    }

                    //Guess is too high
                    guess > correctGuess -> {
                        Toast.makeText(requireContext(), "Your guess is too high!", Toast.LENGTH_SHORT).show()
                        attempts++
                        mediaPlayer.start()
                        sharedVM.setScore(attempts)
                    }

                    //Guess is too low
                    else -> {
                        Toast.makeText(requireContext(), "Your guess is too low!", Toast.LENGTH_SHORT).show()
                        attempts++
                        mediaPlayer.start()
                        sharedVM.setScore(attempts)
                    }
                }

                //Didnt enter a guess
            } else {
                Toast.makeText(requireContext(), "Enter a guess.", Toast.LENGTH_SHORT).show()
                mediaPlayer.start()
            }
        }


    }

    /**
     * Clamps a number between the lowest and highest guess
     */
    private fun clamp(value: Int): Int {
        return when {
            value < LOWEST_GUESS -> LOWEST_GUESS
            value > HIGHEST_GUESS -> HIGHEST_GUESS
            else -> value
        }
    }

    /**
     * Gives a random integer between lowest and highest guess
     */
    fun getRandom(): Int {
        return Random.nextInt(LOWEST_GUESS, HIGHEST_GUESS + 1)
    }


}
