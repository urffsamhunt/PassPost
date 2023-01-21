package com.leteps.passpost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Previewer() {
MultiThreader(DataSet())
}

@Composable
fun MultiThreader(data: DataSet) {
    val per by rememberSaveable { mutableStateOf(data.progressPercentage) }
    Row(verticalAlignment = Alignment.CenterVertically) {
Text(text = per.toString())
    }
}