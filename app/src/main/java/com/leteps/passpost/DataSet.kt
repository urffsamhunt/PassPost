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
    var progress: Int = 0
    var totalProgress: Int = 0
    var totalApplications: Int = 1
    var progressPercentage: Int = progress / totalApplications * 100
    var threadNumber: Int = 3
    var threadNumLock: Boolean = false
}