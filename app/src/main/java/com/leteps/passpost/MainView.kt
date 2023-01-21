package com.leteps.passpost

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
public fun MainView1() {
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
                modifier = Modifier.consumedWindowInsets(innerPadding),
                contentPadding = innerPadding
            ) {
                items(count = cityCount.value) {
                    CardData()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardData() {
    val data = DataSet()
    var expandedState by rememberSaveable { mutableStateOf(false) }
    OutlinedCard(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            ),
    ) {
        Card(
            Modifier
                .height(80.dp)
                .fillMaxWidth()
                , shape = RectangleShape
        ) {
            SelectCity(data)
        }
        if (expandedState) {
            if (!data.gotCookie)
                InfoCard(data)
            else
                TabDetails(data)
        }
        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.secondary)
        Card(modifier = Modifier.fillMaxSize(), shape = RectangleShape) {
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
                        expandedState = !expandedState
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SelectCity(data: DataSet) {

    val cityName = remember { mutableStateOf(data.cityString) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        val webClick = rememberSaveable { mutableStateOf<Boolean>(false) }
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
                style = TextStyle(fontFamily = FontFamily.Monospace,
                fontSize = 35.sp)
            )
        }
        var context = LocalContext.current
        Button(
            onClick = {
                //Do nothing
            }, modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Login")
        }
    }
}

@Composable
fun InfoCard(data: DataSet) {
    DetailView(data)
}

