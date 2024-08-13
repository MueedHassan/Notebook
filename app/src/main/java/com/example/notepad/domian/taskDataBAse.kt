package com.example.notepad.domian

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notepad.repo.Task

@Database(entities = [Task::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}