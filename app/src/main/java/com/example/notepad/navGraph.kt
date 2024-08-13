package com.example.notepad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notepad.repo.Task
import com.example.notepad.ui.theme.SplashScreen

@Composable
fun NotePadNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("home") {
            Home(navController)
        }
        composable("splash") {
            SplashScreen(navigator =navController )
        }

    }
}

