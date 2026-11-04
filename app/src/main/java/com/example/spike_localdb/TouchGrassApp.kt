package com.example.spike_localdb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

// Colours
val AppBackground = Color(0xFFF0F0F0)
val TouchGrassGreen = Color(0xFFD4E3B0)
val GoodNewsYellow = Color(0xFFFCF0B0)
val BlobPink = Color(0xFFF0C8D8)
val BlobOrange = Color(0xFFFAD9A8)

// Shows first screen for 3 seconds
@Composable
fun TouchGrassApp() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3.seconds)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        MainScreen()
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset(x = 50.dp, y = (-60).dp)
                .size(160.dp)
                .blur(20.dp)
                .background(BlobPink, CircleShape)
        )
        Box(
            modifier = Modifier
                .offset(x = (-20).dp, y = 50.dp)
                .size(150.dp)
                .blur(20.dp)
                .background(BlobOrange, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(170.dp)
                .blur(20.dp)
                .background(TouchGrassGreen, CircleShape)
        )

        Text(
            text = "TOUCH GRASS",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
    }
}

// Frame + bottom bar
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf("home") }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            if (currentScreen != "home") {
                NavigationBar(containerColor = AppBackground) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { currentScreen = "home" },
                        icon = { Text("🏠") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == "touchgrass",
                        onClick = { currentScreen = "touchgrass" },
                        icon = { Text("🌱") },
                        label = { Text("Touch Grass") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == "goodnews",
                        onClick = { currentScreen = "goodnews" },
                        icon = { Text("⭐") },
                        label = { Text("Good News") }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    onTouchGrassClick = { currentScreen = "touchgrass" },
                    onGoodNewsClick = { currentScreen = "goodnews" }
                )
                "touchgrass" -> PlaceholderScreen("Touch Grass")
                "goodnews" -> PlaceholderScreen("Good News")
            }
        }
    }
}

// The landing page
@Composable
fun HomeScreen(onTouchGrassClick: () -> Unit, onGoodNewsClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "I NEED...",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = onTouchGrassClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = TouchGrassGreen,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text("to touch grass", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onGoodNewsClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = GoodNewsYellow,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text("some good news", fontSize = 20.sp)
        }
    }
}
@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, fontSize = 24.sp)
    }
}