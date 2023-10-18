package com.example.midtermproject

import androidx.room.Database
import androidx.room.RoomDatabase


/**
 * The database for Score
 */

@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}
