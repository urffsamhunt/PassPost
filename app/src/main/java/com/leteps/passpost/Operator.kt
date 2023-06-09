package com.leteps.passpost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.RequestParams
import com.loopj.android.http.TextHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


fun operator(client: AsyncHttpClient, data: DataSet) {
    val workbook = HSSFWorkbook(data.excelFile)
    val sheet = workbook.getSheetAt(0)
    val arr = Array<String>(sheet.lastRowNum) {it.toString()}
    for (rowIndex in data.colnum-1..sheet.lastRowNum) {
        val row = sheet.getRow(rowIndex)
        if (row != null) {
            val cell: Cell = row.getCell(data.rownum-1)
            if (cell != null) {
                arr[rowIndex-data.colnum+1] = cell.stringCellValue
            }
        }
    }
    data.excelFile?.close()
    Worker(arr, data, client)

}

@OptIn(DelicateCoroutinesApi::class)
fun Worker(arr: Array<String>, data: DataSet, client: AsyncHttpClient) {
    client.addHeader("Cookie", data.cookieString)
    for (i in 1..data.threadNumber) {
        GlobalScope.launch {
            for (j in 1..20)
            asyncRequest(client, arr, j)
        }
    }
}

fun asyncRequest(client: AsyncHttpClient, arr: Array<String>, i: Int) {
    val params = RequestParams()
    params.put("strFileNo", arr[i])
    params.put("basicSearch", "1")
    client["https://org1.passportindia.gov.in/PoliceApplication/policeverification/FetchEnterPVRListAction.action", params , object : TextHttpResponseHandler() {
        override fun onSuccess(statusCode: Int, headers: Array<Header?>?, res: String?) {
            val doc: Document = Jsoup.parse(res.toString())
            val name = doc.selectXpath("/html/body/table[3]/tbody/tr[2]/td/div/table/tbody/tr[1]/td/form/table/tbody/tr/td[3]").text()
            println(name)
        }

        override fun onFailure(
            statusCode: Int,
            headers: Array<Header?>?,
            res: String?,
            t: Throwable?
        ) {
            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            println("Failed")
        }
    }]
}
