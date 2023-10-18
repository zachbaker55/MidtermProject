package com.example.midtermproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.midtermproject.databinding.FragmentGameBinding


/**
 * Fragment that splits into A and B
 */
class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var b: FragmentGameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentGameBinding.bind(view)

        /**
         * Guessing game fragment
         */
        val fragmentA = FragmentA()
        val transactionA: FragmentTransaction = childFragmentManager.beginTransaction()
        transactionA.replace(b.fragmentContainerA.id, fragmentA)
        b.fragmentContainerA.visibility = View.VISIBLE
        transactionA.addToBackStack(null)
        transactionA.commit()


        /**
         * Attempt text fragment
         */
        val fragmentB = FragmentB()
        val transactionB: FragmentTransaction = childFragmentManager.beginTransaction()
        transactionB.replace(b.fragmentContainerB.id, fragmentB)
        b.fragmentContainerA.visibility = View.VISIBLE
        transactionB.addToBackStack(null)
        transactionB.commit()

    }
}
