package com.example.midtermproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.midtermproject.databinding.FragmentBBinding

/**
 * Attempt text fragment
 */
class FragmentB : Fragment(R.layout.fragment_b) {

    private lateinit var b: FragmentBBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentBBinding.bind(view)

        //shared view model
        val sharedVM = ViewModelProvider(requireActivity()).get(SharedVM::class.java)

        sharedVM.getScore().observe(viewLifecycleOwner) { data ->
            b.attemptText.text = "Number of attempts: ${data.toString()}"
        }
    }
}
