package com.example.notepad.ui.theme

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notepad.Greeting
import com.example.notepad.R
import com.example.notepad.TaskViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
//
//@Composable
//fun SplashScreen(navigator: NavHostController){
//    val colors= MaterialTheme.colorScheme.primary.value
//    var targetvalue=(MaterialTheme.colorScheme.onPrimary.value).toFloat()
//    val scale = remember { Animatable(initialValue =0f) }
//    val color = remember { Animatable(initialValue = colors.toFloat()) }
//    val vm:TaskViewModel= viewModel()
//    var currentProgress by remember { mutableStateOf(0f) }
//    var loading by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope() // Create a coroutine scope
//
//    LaunchedEffect(key1 =true,){
//        scale.animateTo(
//            targetValue =2f,
//            animationSpec = tween(
//                durationMillis =500,
//                easing ={
//                    OvershootInterpolator(8f).getInterpolation(it)
//                }
//            ))
//        color.animateTo(
//            targetValue=(targetvalue),
//            animationSpec = tween(durationMillis=1000)
//        )
//        delay(3000)
//        loading = true
//        loadProgress { progress ->
//            currentProgress = progress
//        }
//        navigator.navigate("home")
//    }
//
//
//    Box(
//        modifier= Modifier
//            .fillMaxSize()
//            .background(color = Color(0xFF252525))
//    ){Image(painter = painterResource(id = R.drawable.splash),
//              contentDescription = "SplashScreen",
//        modifier = Modifier
//            .align(Alignment.Center)
//            .scale(scale.value))
//        LinearProgressIndicator(
//            currentProgress ,
//            modifier = Modifier.fillMaxWidth(),
//            color = Color.White
//
//        )
//
//
//
//
//
//
//    }
//}
//
//suspend fun loadProgress(updateProgress: (Float) -> Unit) {
//    for (i in 1..100) {
//        updateProgress(i.toFloat() / 100)
//        delay(100)
//    }
//}
//
//@Composable
//fun LinearDeterminateIndicator() {
//    var currentProgress by remember { mutableStateOf(0f) }
//    var loading by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope() // Create a coroutine scope
//
//    Column(
//        verticalArrangement = Arrangement.spacedBy(12.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        loading=true
//        if (loading) {
//            LinearProgressIndicator(
//                currentProgress ,
//                modifier = Modifier.fillMaxWidth(),
//                color = Color.White
//
//            )
//        }
//    }
//}
@Composable
fun SplashScreen(navigator: NavHostController) {
    val colors = MaterialTheme.colorScheme.primary.value
    val targetValue = MaterialTheme.colorScheme.onPrimary.value.toFloat()
    val scale = remember { Animatable(initialValue = 0f) }
    val color = remember { Animatable(initialValue = colors.toFloat()) }
    val vm: TaskViewModel = viewModel()
    var currentProgress by remember { mutableStateOf(0f) }
    var currentProgressDisplay by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        color.animateTo(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = 1000)
        )
        scope.launch {
            loadProgress { progress ->
                currentProgress = progress
            }
            navigator.navigate("home")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF252525))
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "SplashScreen",
            modifier = Modifier
                .align(Alignment.Center)
                .scale(scale.value)
        )
        currentProgressDisplay= (currentProgress*100).toInt()
        Text(
            text = "$currentProgressDisplay%",
            modifier=Modifier
                .padding(bottom = 100.dp)
            .align(Alignment.BottomCenter)
            ,
            color =Color(0xFF30BE71)


        )
        LinearProgressIndicator(
            progress = currentProgress,
            modifier = Modifier
                .padding(bottom = 70.dp)
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(0.60f)
                .fillMaxHeight(0.01f)

               ,
            color = Color(0xFF30BE71)
        )
    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(30) // Adjust the delay for smoother progress animation
    }
}
