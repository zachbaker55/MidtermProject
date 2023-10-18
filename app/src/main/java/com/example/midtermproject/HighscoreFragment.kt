package com.example.midtermproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.midtermproject.databinding.FragmentHighscoreBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.chromium.base.ThreadUtils.runOnUiThread


/**
 * Highscore RecyclerView fragment
 */
class HighscoreFragment : Fragment(R.layout.fragment_highscore) {

    private lateinit var b: FragmentHighscoreBinding
    lateinit var database: AppDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentHighscoreBinding.bind(view)


        //create a recyclerview
        b.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        var scores: List<Score> = emptyList()

        //Create an adapter with listeners
        lateinit var adapter : Adapter
        adapter = Adapter(requireContext().applicationContext, scores, object : Adapter.ScoreClickListener {

            override fun onDeletePress(score: Score) {
                val dialog = ConfirmationDialog()
                dialog.setConfirmationDialogListener(object : ConfirmationDialog.ConfirmationDialogListener {
                    override fun onDialogPositiveClick(dialog: DialogFragment) {
                        //delete a score if positive dialog is clicked
                        GlobalScope.launch(Dispatchers.IO) {
                            database.scoreDao().delete(score)
                            scores = database.scoreDao().getAllScores()
                            runOnUiThread() {
                                adapter.setData(scores)
                                if (scores.isEmpty()) {
                                        b.highscoreText.visibility = View.VISIBLE
                                }
                            }
                        }
                    }

                    override fun onDialogNegativeClick(dialog: DialogFragment) {
                        //Do nothing
                    }

                })

                dialog.show(childFragmentManager, "ConfirmationDialog")
            }

        })
        b.recyclerView.adapter = adapter

        //Create a database. Update the database with all scores
        database = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "scores_database").build()
        GlobalScope.launch(Dispatchers.IO) {
            scores = database.scoreDao().getAllScores()
            if (scores.isEmpty()) {
                runOnUiThread() {
                    b.highscoreText.visibility = View.VISIBLE
                }
            }
            runOnUiThread() {
                adapter.setData(scores)
            }
        }

    }
}