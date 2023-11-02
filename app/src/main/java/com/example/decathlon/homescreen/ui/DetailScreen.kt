package com.example.decathlon.homescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DetailScreen() {
    Column {
        Text(text = "Detail Screen")
    }
}