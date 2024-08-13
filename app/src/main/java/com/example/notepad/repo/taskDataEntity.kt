package com.example.notepad.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0, // Auto-generate primary key
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Task") val task: String?,
    @ColumnInfo(name = "DataEntered") val dateEntered: String?,
    @ColumnInfo(name = "DataModified") val dateModified: String?
)
