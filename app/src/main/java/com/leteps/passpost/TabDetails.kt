package com.leteps.passpost

import android.widget.ToggleButton
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Previewer12() {
    TabDetails(DataSet())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabDetails(data: DataSet) {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Divider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(
                    text = "3/342",
                )
            }
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )

        var threadCount by rememberSaveable { mutableStateOf(data.threadNumber) }
        var row by rememberSaveable { mutableStateOf("") }
        var col by rememberSaveable { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = threadCount.toString(),
                onValueChange = { threadCount = it.toInt() },
                label = { Text("Threads", fontSize = 15.sp) },
                readOnly = false,
                modifier = Modifier
                    .padding(10.dp)
                    .width(120.dp),
                supportingText = {Text("Default: 3")}
            )

            OutlinedTextField(
                value = row,
                onValueChange = { row = it},
                label = { Text("Row") },
                readOnly = false,
                modifier = Modifier
                    .padding(10.dp)
                    .width(70.dp),
                supportingText = {Text("3")}
            )

            OutlinedTextField(
                value = col,
                onValueChange = { col = it },
                label = { Text("Column") },
                readOnly = false,
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp),
                supportingText = {Text("3")}
            )
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        LazyRow() {
            items(threadCount.toInt()) {
                MultiThreader(data, it)
            }
        }
    }
}