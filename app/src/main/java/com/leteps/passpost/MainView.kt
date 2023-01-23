package com.leteps.passpost

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leteps.passpost.ui.theme.PassPostTheme
import java.io.File


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyApp() {
    PassPostTheme {
        MainView1()
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainView1() {
    val cityCount = rememberSaveable { mutableStateOf(1) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "PassPost",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }, floatingActionButtonPosition = FabPosition.End,

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    cityCount.value = cityCount.value + 1
                },
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }, content = { innerPadding ->
            LazyColumn(
                // consume insets as scaffold doesn't do it by default
                modifier = Modifier.consumeWindowInsets(innerPadding).animateContentSize(),
                contentPadding = innerPadding
            ) {
                items(count = cityCount.value) {
                    CardData()
                }
            }
        }
    )
}

@Composable
fun CardData() {
    val data = DataSet()
    val expandedState = rememberSaveable { mutableStateOf(false) }
    OutlinedCard(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentSize()
            .animateContentSize()
            ,
    ) {
        Card(
            Modifier
                .height(80.dp)
                .fillMaxWidth(), shape = RectangleShape, colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant  ,
            ),
        ) {
            SelectCity(data)
        }

        AnimatedVisibility(visible = expandedState.value) {
            if (data.gotCookie)
                InfoCard(data, expandedState)
            else
                TabDetails(data)
        }


        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.secondary)
        Card(
            modifier = Modifier
                .fillMaxSize(), shape = RectangleShape
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "2%",
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.CenterVertically)
                )
                val animationProgress = animateFloatAsState(
                    targetValue = 0.5f,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                ).value
                LinearProgressIndicator(
                    progress = animationProgress,
                )
                Spacer(modifier = Modifier.size(15.dp, 1.dp))
                IconButton(
                    onClick = {
                        expandedState.value = !expandedState.value
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                ) {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = "")
                }
            }
        }
    }
}

@Composable
fun SelectCity(data: DataSet) {

    val cityName = remember { mutableStateOf(data.cityString) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        //val City = rememberSaveable { mutableStateOf<String>("") }
        val xlFile = rememberSaveable { mutableStateOf<File?>(null) }


        val pickLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { fileUri ->
            if (fileUri != null) {
                xlFile.value = File(fileUri.path.toString())
                val a = fileUri
                    .path
                    ?.substringAfterLast('/')
                    ?.substringBeforeLast('.')
                    ?: ""
                cityName.value = a.lowercase().replaceFirstChar { a[0].uppercase() }
                data.cityString = cityName.value

            }
        }

        if (cityName.value == "") {
            Button(
                onClick = {
                    pickLauncher.launch(arrayOf("application/vnd.ms-excel"))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .wrapContentWidth(Alignment.Start),
            ) {
                Text("Select City")
            }
        }
        if (cityName.value != "") {
            Text(
                text = cityName.value,
                modifier = Modifier
                    .padding(horizontal = 21.dp)
                    .weight(1f),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 35.sp
                )
            )
        }
        Button(
            onClick = {
                //Do nothing
            }, modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Login")
        }
    }
}

@Composable
fun InfoCard(data: DataSet, exp: MutableState<Boolean>) {
    DetailView(data, exp)
}

