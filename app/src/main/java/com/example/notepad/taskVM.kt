package com.example.notepad

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.notepad.domian.AppDatabase
import com.example.notepad.repo.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel : ViewModel() {
    var database: AppDatabase? = null
    private val _tasks = MutableLiveData<List<Task>?>()
    val tasks: MutableLiveData<List<Task>?> get() = _tasks

    fun insertTask(task: Task,context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                database?.taskDao()?.insertTask(task)
            }
            }
    }
    fun getTask(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                var task = database?.taskDao()?.getAll()
                _tasks.postValue(task)
            }
        }

    }
    fun getTaskBYDateModified (context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                var task = database?.taskDao()?.getTasksOrderedByDAtAsc()
                _tasks.postValue(task)
            }
        }

    }
    fun getTaskBYTitle (context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                var task = database?.taskDao()?.getTasksOrderedByTitleAsc()
                _tasks.postValue(task)
            }
        }

    }
    fun getTaskBYDateEntered (context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                var task = database?.taskDao()?.getTasksOrderedByDateEnteredeAsc()
                _tasks.postValue(task)
            }
        }

    }

    fun deleteTaskByUid(context: Context,task: Task){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                 database?.taskDao()?.deleteTaskByUid(task)

            }
        }
    }

    fun updateTask(task: Task,context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                database = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "my-database"
                ).fallbackToDestructiveMigration().build()
                database?.taskDao()?.upsertTask(task=task)
        }
    }}

    fun searchTasks(query: String, context: Context) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                getTask(context) // Fetch all tasks if query is empty
            } else {
                val filteredTasks = tasks.value?.filter { task ->
                    (task.title?.contains(query, ignoreCase = true) == true) ||
                            (task.task?.contains(query, ignoreCase = true) == true)
                }

                _tasks.value = filteredTasks
            }
        }
    }
}