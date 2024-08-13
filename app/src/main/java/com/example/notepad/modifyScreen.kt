package com.example.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Button
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import com.example.notepad.repo.Task
import java.util.Calendar


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val taskId = intent.getStringExtra("taskId")
            val taskTitle = intent.getStringExtra("taskTitle")
            val taskDescription = intent.getStringExtra("taskDescription")
            val taskEntered= intent.getStringExtra("taskDescription")
            println("uid 1 $taskId, $taskDescription,$taskTitle")
            SecondScreen(taskId = taskId, taskTitle = taskTitle, taskDescription = taskDescription,taskEntered=taskEntered)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    taskId: String?,
    taskTitle: String?,
    taskDescription: String?,
    taskEntered: String?
) {
    var taskDescription by rememberSaveable { mutableStateOf("$taskDescription") }
    var titleEntered by rememberSaveable { mutableStateOf("$taskTitle") }
    var vm:TaskViewModel= viewModel()
    var context= LocalContext.current
    val TaskSaver: Saver<Task, Any> = mapSaver(
        save = { task ->
            mapOf(
                "uid" to task.uid,
                "title" to task.title,
                "task" to task.task,
                "dateEntered" to task.dateEntered,
                "dateModified" to task.dateModified
            )
        },
        restore = { map ->
            Task(
                uid = map["uid"] as Int,
                title = map["title"] as String,
                task = map["task"] as String,
                dateEntered = map["dateEntered"] as String,
                dateModified = map["dateModified"] as String
            )
        }
    )
    var newTask by rememberSaveable(stateSaver = TaskSaver) {
        mutableStateOf(
            Task(
                uid = 0,
                task = "",
                dateEntered = "",
                dateModified = "",
                title = ""
            )
        )
    }
    // Handle the back button press
    BackHandler {
        newTask = taskId?.toInt()?.let{
            Task(uid = it,
                task = taskDescription,
                dateEntered = Calendar.getInstance().time.toString(),
                dateModified = Calendar.getInstance().time.toString(),
                title = titleEntered) }!!
        println("task $newTask")
        if (newTask != null) {
            vm.insertTask(newTask,context)
        }

        (context as ComponentActivity).finish() // Close the activity
    }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier= Modifier
            .background(color = Color(0xFF252525))
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 0.dp)

    ) {
        Text(
            text = "Notebook",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight=FontWeight.W600,),
        )
        TextField(
            value = taskDescription,
            label = { Text("$taskDescription") },
          onValueChange ={
              taskDescription= it

          },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp),
            modifier= Modifier
                .padding(10.dp)
                .border(
                    border = BorderStroke(1.dp, color = Color(0xFFC4C4C4)),
                    shape = RoundedCornerShape(15.dp)
                )
                .fillMaxHeight(0.65f)
                .fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                focusedTextColor= Color.White,
                unfocusedTextColor= Color.White,
                containerColor = Color(0xFF252525),
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color(0xFFC4C4C4),
                unfocusedLabelColor = Color(0xFFC4C4C4)
            )
        )
        println("uid $taskId")

   }


}