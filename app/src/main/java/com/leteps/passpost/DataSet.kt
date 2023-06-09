package com.leteps.passpost

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import java.io.File
import java.io.InputStream


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
    var threadNumber: Int = 1
    var threadNumLock: Boolean = false
    var excelFile: InputStream? = null
    var rownum: Int = 3
    var colnum: Int = 5
}