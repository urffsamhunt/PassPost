package com.leteps.passpost

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView.*
import android.webkit.CookieManager
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.*
import com.leteps.passpost.ui.theme.PassPostTheme


@Preview(showBackground = true)
@Composable
fun WebPreviewer() {
    PassPostTheme {
        DetailView(DataSet())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(data: DataSet) {

    val url by remember { mutableStateOf<String>("https://org2.passportindia.gov.in/PoliceApplication/certLogin/index.action") }
    val state =
        rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    val openWebViewState = remember { mutableStateOf<Boolean>(true) }

    if (openWebViewState.value) {
        Box(modifier = Modifier.animateContentSize()) {
            OpenWebView(state = state, navigator = navigator)
            FloatingActionButton(
                onClick = {
                    data.cookieString = CookieManager.getInstance()
                        .getCookie("https://org1.passportindia.gov.in/PoliceApplication/certLogin/authenticateAction.action")
                        ?: ""
                    if (data.cookieString.count { it == ';' } == 2) {
                        openWebViewState.value = !openWebViewState.value
                        data.gotCookie = true
                    }
                    println(data.cookieString)
                    println(data.cityString)
                },
                modifier = Modifier
                    .padding(15.dp)
                    .size(50.dp)
                    .align(
                        Alignment.BottomEnd
                    ),

                ) {
                Icon(Icons.Filled.Done, contentDescription = null)
            }
        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun OpenWebView(state: WebViewState, navigator: WebViewNavigator) {

    val webClient = remember {
        object : AccompanistWebViewClient() {
        }
    }

    WebView(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.setSupportZoom(true)
            webView.settings.builtInZoomControls = true
            CookieManager.getInstance().removeSessionCookies(null)

        },
        onDispose = {
            Log.d("Accompanist WebView", "Dispose")
            CookieManager.getInstance().flush()
        },
        client = webClient
    )
}