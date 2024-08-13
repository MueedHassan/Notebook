package com.example.notepad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notepad.repo.Task
import com.example.notepad.ui.theme.NotePadTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {

                    NotePadNavGraph(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(navController: NavHostController) {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var taskEntered by rememberSaveable { mutableStateOf("") }
    var titleEntered by rememberSaveable { mutableStateOf("") }
    var vm:TaskViewModel= viewModel()
    var context= LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by rememberSaveable { mutableStateOf("List") }
    var state by remember { mutableStateOf(false) }
    var expandedSort by remember { mutableStateOf(false) }
    var selectedOptionSort by rememberSaveable { mutableStateOf("Title") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var searchIconClicked by remember { mutableStateOf(false) }

    LaunchedEffect(selectedOptionSort) {
        when (selectedOptionSort) {
            "Title" -> vm.getTaskBYTitle(context)
            "Date Created" -> vm.getTaskBYDateEntered(context)
            "Date Modified" -> vm.getTaskBYDateModified(context)
        }
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            vm.searchTasks(searchQuery, context)
        }
    }
    Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor =  Color(0xFF252525),
                        titleContentColor = Color.White,
                    ),
                    title = {
                        if(!searchIconClicked)
                        Text(
                        text = "Notebook",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight=FontWeight.W600,),
                         )
                        else{
                            TextField(
                                value = searchQuery,
                                onValueChange = {
                                    searchQuery = it
                                    vm.searchTasks(searchQuery, context)
                                },
                                placeholder = { Text(text = "Search...", color = Color.Gray) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedTextColor=Color.White,
                                    unfocusedTextColor=Color.White,
                                    containerColor = Color(0xFF252525),
                                    focusedIndicatorColor = Color.White,
                                    unfocusedIndicatorColor = Color.Gray,
                                ),
                                singleLine = true,
                            )
                        }
                    },
                   actions={
                       Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.searchicon2),
                            contentDescription = "",
                            modifier= Modifier
                                .size(20.dp)
                                .clickable {
                                    searchIconClicked = !searchIconClicked
                                },
                            tint=Color.White
                        )
                        Spacer(modifier= Modifier.width(15.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.downarrow),
                            contentDescription = "",
                            modifier= Modifier
                                .padding(top = 8.dp)
                                .size(10.dp)
                                .clickable {
                                    expanded = !expanded
                                },
                            tint=Color.White
                        )
                        Spacer(modifier= Modifier.width(5.dp))
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = "$selectedOption",
                            color=Color.White,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight=FontWeight.W400,),
                        )
                           if(expanded){
                               DropdownMenu(expanded =expanded , onDismissRequest = {expanded=false },
                                   modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                                   offset = DpOffset(10.dp,10.dp)
                               ){
                                   DropdownMenuItem(
                                       text = { Text(text = "Grid") },
                                       onClick = {   },
                                       leadingIcon = {RadioButton(
                                           selected = state,
                                           onClick = { state = true
                                               selectedOption="Grid" },
                                           modifier = Modifier
                                               .clip(shape = RoundedCornerShape(topStart = 70.dp))
                                               .semantics {
                                                   contentDescription = "Localized Description"
                                               })
                                       })
                                   DropdownMenuItem(
                                       text = { Text(text = "List") },
                                       onClick = { },
                                       leadingIcon = {RadioButton(
                                           selected = !state,
                                           onClick = { state = false
                                               selectedOption="List" },
                                           modifier = Modifier.semantics { contentDescription = "Localized Description" })})
                               }
                           }
                       }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {showBottomSheet = true },
                    modifier = Modifier.clip(shape = CircleShape),
                    containerColor =  Color(0xFF30BE71)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = Color(0xFF252525))
                ,
            ) {
                Row(
                    modifier=Modifier.align(Alignment.TopEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.datemodify),
                        contentDescription = "",
                        modifier= Modifier
                            .size(15.dp)
                            .clickable { expandedSort = !expandedSort },
                        tint=Color.White
                    )
                    if(expandedSort){
                        DropdownMenu(expanded =expandedSort, onDismissRequest = {expandedSort=false },
                            modifier = Modifier.clip(shape = RoundedCornerShape(10.dp)),
                            offset = DpOffset(10.dp,10.dp)

                        ){
                            DropdownMenuItem(
                                text = { Text(text = "Title") },
                                onClick = {   },
                                leadingIcon = {RadioButton(
                                    selected = selectedOptionSort=="Title",
                                    onClick = { state = true
                                        selectedOptionSort="Title" },
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(topStart = 70.dp))
                                        .semantics {
                                            contentDescription = "Localized Description"
                                        })})
                            DropdownMenuItem(
                                text = { Text(text = "Date Created") },
                                onClick = { },
                                leadingIcon = {RadioButton(
                                    selected =selectedOptionSort=="Date Created",
                                    onClick = { state = true
                                        selectedOptionSort="Date Created" },
                                    modifier = Modifier.semantics { contentDescription = "Localized Description" })})
                            DropdownMenuItem(
                                text = { Text(text = "Date Modified") },
                                onClick = { },
                                leadingIcon = {RadioButton(
                                    selected = selectedOptionSort=="Date Modified",
                                    onClick = {
                                        state = true
                                        selectedOptionSort="Date Modified" },
                                    modifier = Modifier.semantics { contentDescription = "Localized Description" })})
                        }
                    }
                    Spacer(modifier= Modifier.width(10.dp))
                    Text(
                        modifier= Modifier.padding(end=20.dp),
                        text = "$selectedOptionSort",
                        color=Color.White,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight=FontWeight.W300,),
                    )
                }
                if(vm.tasks.value!=null)
                {
                    if (selectedOption=="List")
                    { list(vm=vm, context = context, navController = navController) }

                    else
                    {lazygrid(vm = vm, context =context ,navController = navController)}
                }
                else{
                    Image(painter = painterResource(id = R.drawable.pic1), contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(height = 300.dp, width = 285.dp))
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(0.dp),
                    containerColor = Color(0xFF252525)
                ) {
                    Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier= Modifier
                           .fillMaxWidth(1f)
                           .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 0.dp)
                   ) {
                        TextField(
                           value = titleEntered,
                           onValueChange = { titleEntered= it },
                           label = { Text(text="               Title",
                               style = MaterialTheme.typography.titleSmall.copy(fontWeight=FontWeight.W600,),
                               modifier = Modifier.align(Alignment.CenterHorizontally)
                               ) },
                           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                           modifier= Modifier
                               .fillMaxHeight(0.10f)
                               .fillMaxWidth(0.45f),
                           colors = TextFieldDefaults.textFieldColors(
                               focusedTextColor=Color.White,
                               unfocusedTextColor=Color.White,
                               containerColor = Color(0xFF252525),
                               cursorColor = Color.White,
                               focusedIndicatorColor = Color.Transparent,
                               unfocusedIndicatorColor = Color.Transparent,
                               focusedLabelColor =Color(0xFFC4C4C4),
                               unfocusedLabelColor = Color(0xFFC4C4C4)))
                       TextField(
                           value = taskEntered,
                           onValueChange = { taskEntered= it },
                           label = { Text("Enter Text") },
                           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                           shape = RoundedCornerShape(10.dp),
                           modifier= Modifier
                               .padding(10.dp)
                               .border(
                                   border = BorderStroke(1.dp, color = Color(0xFFC4C4C4)),
                                   shape = RoundedCornerShape(15.dp)
                               )
                               .fillMaxHeight(0.35f)
                               .fillMaxWidth(),
                           colors = TextFieldDefaults.textFieldColors(
                               focusedTextColor=Color.White,
                               unfocusedTextColor=Color.White,
                               containerColor = Color(0xFF252525),
                               cursorColor = Color.White,
                               focusedIndicatorColor = Color.Transparent,
                               unfocusedIndicatorColor = Color.Transparent,
                               focusedLabelColor =Color(0xFFC4C4C4),
                               unfocusedLabelColor = Color(0xFFC4C4C4)
                           )
                         )
                       Button(onClick = {
                           val newTask = Task(task = taskEntered,
                               dateEntered = Calendar.getInstance().time.toString(),
                               dateModified = Calendar.getInstance().time.toString(),
                               title = titleEntered)
                           vm.insertTask(newTask,context)
                           scope.launch { sheetState.hide() }.invokeOnCompletion {
                               if (!sheetState.isVisible) {
                                   showBottomSheet = false
                               } }
                                        vm.getTask(context)},
                       modifier= Modifier
                           .padding(10.dp)
                           .height(50.dp)
                           .width(210.dp),
                           shape=RoundedCornerShape(15.dp),
                           colors= ButtonDefaults.buttonColors(containerColor = Color(0xFF30BE71)))
                       {
                           Text("Save")
                       } } }}}
}

@Composable
fun Home(navController: NavHostController) {
    NotePadTheme {
        Greeting(navController) }
}
@Composable
fun list(vm:TaskViewModel,context: Context,navController: NavHostController){
    vm.getTask(context)
    var t=vm.tasks.value
    val colors = listOf(Color(0xFF30BE71), Color(0xFFFD99FF), Color(0xFFFFFF99), Color(0xFF9EFFFF))
    var index=0
    LazyColumn (modifier = Modifier.padding(top = 20.dp,bottom=100.dp)){
        if(t!=null){
            items(t){items ->
                SwipeToDeleteContainer(item = items, onDelete = {
                    vm.deleteTaskByUid(context,items)
                }, itemtype = "list") {
                    val color = colors[index % colors.size]
                    index++
                    println("uid 0 ${items.uid} ")
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = color)
                            .clickable {
                                val intent = Intent(context, SecondActivity::class.java).apply {
                                    putExtra("taskId", "${items.uid}")
                                    putExtra("taskTitle", items.title)
                                    putExtra("taskDescription", items.task)
                                    putExtra("taskEntered", items.dateEntered)
                                }
                                context.startActivity(intent)
                            }
                    )
                    {
                        Text(
                            text = "${items.title}",
                            color = Black,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700,),
                            modifier = Modifier.padding(
                                top = 10.dp,
                                start = 20.dp,
                                end = 20.dp,
                                bottom = 5.dp))
                        Text(text = "${items.task}",
                            color = Black,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400,),
                            modifier = Modifier.padding(top = 25.dp, start = 20.dp, end = 20.dp,)
                        )
                    }
                }
            }
        }

    }

}

@Composable
fun lazygrid(vm: TaskViewModel, context: Context, navController: NavHostController){
    val colors = listOf(Color(0xFF30BE71), Color(0xFFFD99FF), Color(0xFFFFFF99), Color(0xFF9EFFFF))
    var index=0
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.padding(top = 20.dp,bottom=100.dp)
    ) {
        vm.getTask(context)
        var t=vm.tasks.value
        if(t!=null){
            items(t) { items ->
                SwipeToDeleteContainer(
                    item = items,
                    onDelete = {
                    vm.deleteTaskByUid(context,items)
                },
                    itemtype = "grid"){
                val color = colors[index % colors.size]
                index++
                Box (modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = color)
                    .clickable {
                        val intent = Intent(context, SecondActivity::class.java).apply {
                            putExtra("taskId", "${items.uid}")
                            putExtra("taskTitle", items.title)
                            putExtra("taskDescription", items.task)
                            putExtra("taskEntered", items.dateEntered)
                        }
                        context.startActivity(intent)
                    }
                )
                {
                    Text(text ="${items.title}",
                        color= Black,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight=FontWeight.W600,),
                        modifier=Modifier.padding(top=10.dp, start = 20.dp, end = 20.dp)
                    )
                    Text(text ="${items.task}",
                        color= Black,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight=FontWeight.W300,),
                        modifier=Modifier.padding(top=25.dp, start = 20.dp, end = 20.dp)
                    )
                }}
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    itemtype:String,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )
    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }
    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                if(itemtype=="list")
                {
                    DeleteBackground(swipeDismissState = state)
                }
                else{
                    DeleteBackgroundGrid(swipeDismissState = state)
                    }

            },
            dismissContent = { content(item) },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackgroundGrid(
    swipeDismissState: DismissState
) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent
    Box(
        modifier = Modifier
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}
