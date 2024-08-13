package com.example.notepad.domian

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notepad.repo.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertTask(task: Task)

    @Query("SELECT * FROM Task ORDER BY title ASC")
    fun getTasksOrderedByTitleAsc(): List<Task>

    @Query("SELECT * FROM Task ORDER BY DataEntered ASC")
    fun getTasksOrderedByDateEnteredeAsc(): List<Task>
    @Query("SELECT * FROM Task ORDER BY DataModified ASC")
    fun getTasksOrderedByDAtAsc(): List<Task>

    @Delete
    suspend fun deleteTaskByUid(task: Task)


    @Update
    suspend fun upsertTask(task: Task)

}