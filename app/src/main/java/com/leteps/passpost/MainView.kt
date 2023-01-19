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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState
import com.leteps.passpost.ui.theme.PassPostTheme
import kotlinx.coroutines.MainScope
import java.io.File
import java.nio.file.FileSystemLoopException

@Preview(showBackground = true)
@Composable
fun MyApp() {
    PassPostTheme {
        MainView1("Pass")
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
public fun MainView1(title: String) {
    val cityCount = remember { mutableStateOf(1) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                items(cityCount.value) {
                    CardData()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardData() {
    var expandedState by remember { mutableStateOf(false) }
    Card(
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
                .fillMaxWidth(),
        ) {
            SelectCity()
        }
        if (expandedState) {
            InfoCard()
        }
        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.secondary)
        Card(modifier = Modifier.fillMaxSize()) {
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
                    onClick = { expandedState = !expandedState },
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "")
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SelectCity() {

    Row(verticalAlignment = Alignment.CenterVertically) {
        val state = rememberWebViewState("https://www.google.com")
        val webClick = remember { mutableStateOf<Boolean>(false) }
        val City = rememberSaveable { mutableStateOf<String>("") }
    val xlFile = remember { mutableStateOf<File?>(null) }


        val pickLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { fileUri ->
            if (fileUri != null) {
                xlFile.value = File(fileUri.path.toString())
                City.value = fileUri.path?.substringAfterLast('/')?.substringBeforeLast('.') ?: ""
            }
        }

        if (City.value == "") {
            Button(
                onClick = {
                    pickLauncher.launch(arrayOf("application/vnd.ms-excel"))
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
            ) {
                Text("Select City")
            }
        }
        if (City.value != "") {
            Text(
                text = City.value,
                modifier = Modifier
                    .weight(1f)
                    .padding(15.dp),
                fontSize = 25.sp,
            )
        }

        if (webClick.value) {
            OpenWebView(state)
        }

        Button(
            onClick = {
                webClick.value = !webClick.value
            }, modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(imageVector = Icons.Filled.AccountBox, contentDescription = "Login")
        }
    }
}

@Composable
fun InfoCard() {

}

@OptIn(ExperimentalPermissionsApi::class)
fun getPermissions(filePermissionState: PermissionState) {
    filePermissionState.launchPermissionRequest()
}

@Composable
fun OpenWebView(state: WebViewState) {
    WebView(
        state,
        modifier = Modifier.fillMaxSize()
    )
}