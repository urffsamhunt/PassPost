package com.leteps.passpost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Previewer12() {
    val a = remember { mutableStateOf(false) }
    TabDetails(DataSet(),a)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabDetails(data: DataSet, isStarted: MutableState<Boolean>) {
    val showLog = rememberSaveable { mutableStateOf(false) }
    val logger = rememberSaveable { mutableStateOf(false) }
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Divider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        showLog.value = !showLog.value
                    },
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "3/342",
                    )
                }
                AssistChip(
                    colors = if (logger.value) AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primary, labelColor = MaterialTheme.colorScheme.inversePrimary) else AssistChipDefaults.
                    elevatedAssistChipColors(containerColor = Color.Black, labelColor = Color.White),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = { logger.value =!logger.value },
                    label = { Text("Logging") }
                )
            }
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )

        if (showLog.value) {
            AlertDialog(onDismissRequest = {
                showLog.value = false
            }) {
                InfoTab(showLog)
            }
        }


        var threadCount by rememberSaveable { mutableStateOf(data.threadNumber.toString()) }
        var row by rememberSaveable { mutableStateOf("") }
        var col by rememberSaveable { mutableStateOf("") }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                enabled = !isStarted.value,
                value = threadCount,
                onValueChange = {
                    threadCount = it
                    data.threadNumber = ("0$threadCount").toIntOrNull() ?: 0
                },
                label = { Text("Threads", fontSize = 15.sp) },
                readOnly = false,
                modifier = Modifier
                    .padding(10.dp)
                    .width(120.dp),
                supportingText = { Text("Default: 3") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                enabled = !isStarted.value,
                value = row,
                onValueChange = { row = it },
                label = { Text("Row") },
                readOnly = false,
                modifier = Modifier
                    .padding(10.dp)
                    .width(70.dp),
                supportingText = { Text("3") }
            )

            OutlinedTextField(
                enabled = !isStarted.value,
                value = col,
                onValueChange = { col = it },
                label = { Text("Column") },
                readOnly = false,
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp),
                supportingText = { Text("3") }
            )
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        LazyRow {
            items(data.threadNumber) {
                MultiThreader(data, it, isStarted)
            }
        }
    }
}