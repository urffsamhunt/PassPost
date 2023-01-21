package com.leteps.passpost

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


open class DataSet() {
    var cookieString: String = ""
    var cityString: String = ""
    var gotCookie: Boolean = false
    var isStarted: Boolean = false
    var isFinished: Boolean = false
}