package com.leteps.passpost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun Pre() {
    var a = remember { mutableStateOf(false) }
    InfoTab(a)
}

@Composable
fun InfoTab(showLog: MutableState<Boolean>) {
    Surface(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Logs",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(10.dp),
            )
            Divider(modifier = Modifier.padding(10.dp))
            OutlinedCard(
                modifier = Modifier
                    .padding(5.dp)
                    .height(400.dp)
                    .fillMaxWidth(),
                shape = RectangleShape
            ) {
                LazyColumn(modifier = Modifier.padding(10.dp), state = LazyListState(firstVisibleItemIndex = 200)) {
                    items(290) {
                        val a = it + 1
                        val c = a % 4 == 0
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("$a. ", style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = "LKITN2B00133445  : ",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Box() {
                                Text(
                                    text = if (c) "Adverse" else "Success",
                                    style = TextStyle(color = if (c) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary)
                                )
                            }
                        }
                        Divider()
                    }
                }
            }
            TextButton(
                onClick = {
                    showLog.value = false
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Dismiss")
            }
        }
    }
}