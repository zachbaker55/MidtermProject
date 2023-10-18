package com.example.midtermproject

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.midtermproject.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.chromium.base.ThreadUtils

class MainActivity : AppCompatActivity() {

    private lateinit var b : ActivityMainBinding;
    private lateinit var sharedVM : SharedVM
    lateinit var database: AppDatabase


    /**
     * Main Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        var score = 0

        //create a database
        database = Room.databaseBuilder(this, AppDatabase::class.java, "scores_database").build()


        //shared view model
        sharedVM = ViewModelProvider(this).get(SharedVM::class.java)
        sharedVM.getName().observe(this, Observer { data ->
            //if not empty string, then it is a result
            if (data != "") {
                b.scoreText.text = "$data score: $score \n Play Another Game?";

                //clear all fragments, returning home
                val fragmentManager = supportFragmentManager
                for (i in 0 until fragmentManager.backStackEntryCount) {
                    fragmentManager.popBackStack()
                }

                //save result to database
                GlobalScope.launch(Dispatchers.IO) {
                    database.scoreDao().insert(Score(name = data, scoreValue = score))
                }

                b.fragmentContainer.visibility = View.GONE
            }
        })

        //update score from shared view model
        sharedVM.getScore().observe(this, Observer { data ->
            score = data
        })


        //Move to the split fragment for the guessing game
        b.playGame.setOnClickListener {
            sharedVM.setName("")
            sharedVM.setScore(0)
            b.scoreText.text = "Welcome to the game"

            val gameFragment = GameFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(b.fragmentContainer.id, gameFragment)
            b.fragmentContainer.visibility = View.VISIBLE
            transaction.addToBackStack(null)
            transaction.commit()
        }


        //Move to the highscore recyclerview fragment
        b.viewScores.setOnClickListener {
            b.scoreText.text = "Welcome to the game"
            val highscoreFragment = HighscoreFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(b.fragmentContainer.id, highscoreFragment)
            b.fragmentContainer.visibility = View.VISIBLE
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    /**
     * On back pressed, remove the fragment view so you can see
     */
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        if (fragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            b.fragmentContainer.visibility = View.GONE
        }
    }
}

