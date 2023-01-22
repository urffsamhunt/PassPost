package com.leteps.passpost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Previewer() {
    MultiThreader(DataSet(), 1)
}

@Composable
fun MultiThreader(data: DataSet, i: Int) {
    val per by rememberSaveable { mutableStateOf(data.progressPercentage) }
    Box() {
        Row() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Thread : " + (i + 1))
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}