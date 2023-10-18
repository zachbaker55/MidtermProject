package com.example.midtermproject

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update


/**
 * Score data, represents a single player's score
 */
@Entity(tableName = "scores")
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var scoreValue: Int
)


/**
 * ROOM Dao for each Score
 * 5 methods -
 * insert - inserts the Score into the database
 * getAllScores - gets a List<Score> from the database
 * getScoreById - gets a single Score from its Long id
 * update - updates a Score in the database with new data
 * delete - removes a Score from the database
 */

@Dao
interface ScoreDao {

    @Insert
    fun insert(score : Score)

    @Query("SELECT * FROM scores")
    fun getAllScores() : List<Score>

    @Query("SELECT * FROM scores WHERE id = :scoreId")
    fun getScoreById(scoreId : Long): Score?

    @Update
    fun update(score : Score)

    @Delete
    fun delete(score : Score)
}

