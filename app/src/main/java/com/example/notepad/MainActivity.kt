package com.example.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ComplexColorCompat
import com.example.notepad.ui.theme.NotePadTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotePadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var taskEntered by rememberSaveable { mutableStateOf("") }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor =  Color(0xFF252525),
                        titleContentColor = Color.White,
                    ),
                    title = {
                        Text(
                        text = "Notebook",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight=FontWeight.W600,),
                    )
                    },
                   actions={
                       Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.searchicon2),
                            contentDescription = "",
                            modifier=Modifier
                                .size(20.dp),
                            tint=Color.White
                        )
                        Spacer(modifier= Modifier.width(15.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.downarrow),
                            contentDescription = "",
                            modifier= Modifier
                                .padding(top = 8.dp)
                                .size(10.dp),
                            tint=Color.White
                        )
                        Spacer(modifier= Modifier.width(5.dp))
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = "Grid",
                            color=Color.White,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight=FontWeight.W400,),
                        )


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
                        modifier=Modifier
                            .size(15.dp),
                        tint=Color.White
                    )
                    Spacer(modifier= Modifier.width(10.dp))
                    Text(
                        modifier= Modifier.padding(end=20.dp),
                        text = "Date Modify",
                        color=Color.White,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight=FontWeight.W300,),
                    )


                }

                Image(painter = painterResource(id = R.drawable.pic1), contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(height = 300.dp, width = 285.dp))



            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState,
                    shape = RoundedCornerShape(0.dp),
                    containerColor = Color(0xFF252525)

                ) {
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       modifier=Modifier.fillMaxWidth(1f).padding(start=20.dp,end=20.dp, bottom = 20.dp,top=0.dp)
                   ) {
                       Text(
                           text = "Title",
                           style = MaterialTheme.typography.headlineMedium.copy(fontWeight=FontWeight.W600,),
                           color = Color.White,
                           modifier = Modifier.padding(bottom = 10.dp)
                       )

                       TextField(
                           value = taskEntered,
                           onValueChange = { taskEntered= it },
                           label = { Text("Enter Text") },

                           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                           shape = RoundedCornerShape(10.dp),
                           modifier= Modifier
                               .padding(10.dp)
                               .border(border = BorderStroke(1.dp,color =Color(0xFFC4C4C4)),
                                   shape = RoundedCornerShape(15.dp))
                               .fillMaxHeight(0.35f).fillMaxWidth(),
                           colors = TextFieldDefaults.textFieldColors(
                               focusedTextColor=Color.White,
                               unfocusedTextColor=Color.White,
                               containerColor = Color(0xFF252525),
                               cursorColor = Color.White,
                               focusedIndicatorColor = Color.Transparent, // removes the underline when focused
                               unfocusedIndicatorColor = Color.Transparent, // removes the underline when not focused
                               focusedLabelColor =Color(0xFFC4C4C4), // label color when focused
                               unfocusedLabelColor = Color(0xFFC4C4C4) // label color when not focused
                           )
                         )

                       Button(onClick = {
                           scope.launch { sheetState.hide() }.invokeOnCompletion {
                               if (!sheetState.isVisible) {
                                   showBottomSheet = false
                               }
                           }
                       },
                       modifier=Modifier
                           .padding(10.dp)
                           .height(50.dp)
                           .width(210.dp),
                           shape=RoundedCornerShape(15.dp),
                           colors= ButtonDefaults.buttonColors(containerColor = Color(0xFF30BE71))

                       ) {
                           Text("Save")
                       }
                   }

                }}
        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotePadTheme {
        Greeting("Android")
    }
}