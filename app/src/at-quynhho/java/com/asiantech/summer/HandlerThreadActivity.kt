@file:Suppress("DEPRECATION")

package com.asiantech.summer

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-quynhho`.activity_download.btStart
import java.io.*
import java.net.URL
import java.net.URLConnection

class HandlerThreadActivity : AppCompatActivity() {

    lateinit var dialog: ProgressDialog
//    private lateinit var handler: Handler

    companion object {
        private var FILE_URL =
            "https://kynguyenlamdep.com/wp-content/uploads/2020/01/hinh-anh-dep-hoa-bo-cong-anh.jpg"
        private var PROGRESS_BAR = 0
        private var MAX_BAR = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
        btStart.setOnClickListener {
            onClickDownload()
//            handler = @SuppressLint("HandlerLeak")
//            object : Handler() {
//                override fun handleMessage(msg: Message?) {
//                    super.handleMessage(msg)
//                    if (msg != null) {
//                        dialog.progress = msg.arg1
//                    }
//                }
//            }
        }
    }

    private fun onDialog(id: Int) {
        return when (id) {
            PROGRESS_BAR -> {
                dialog = ProgressDialog(this).apply {
                    setTitle("Handler")
                    progress = 0
                    setMessage("Downloading file. Please wait...")
                    isIndeterminate = false
                    max = MAX_BAR
                    setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                    setCancelable(true)
                }
                dialog.show()
            }
            else -> {
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClickDownload() {
        onDialog(PROGRESS_BAR)
        val fileName = "/downloadimgthreadfile2.jpg"
        val storageDir: File =
            File(Environment.getExternalStorageDirectory().path + fileName)
        if (!storageDir.exists()) {
            Log.d(
                "QQQ", "xxxx: " + storageDir.createNewFile()
            )
        }

        Thread(Runnable {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            try {
                val url = URL(FILE_URL)
                val connection: URLConnection = url.openConnection()
                connection.doInput = true
                connection.connect()
                val lengthFile: Int = connection.contentLength
                val input: InputStream =
                    BufferedInputStream(url.openStream(), lengthFile)
                val output: OutputStream = FileOutputStream(storageDir)
                val data = ByteArray(1024)
                var count: Int
                var total: Long = 0
                while (input.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    output.write(data, 0, count)
                    val loadNum: Int = (total * 100 / lengthFile).toInt()
                    Log.d("QQQ", "number: $loadNum")
                    val msg = Message.obtain()
//                    handler.sendMessage(msg)
                    msg.arg1 = loadNum
                    setProgressDialogValue(loadNum)
                }
                output.flush()
                output.close()
            } catch (e: IOException) {
                Log.d("BBB", "exception: $e")
            }
        }).start()
    }

    private fun setProgressDialogValue(value: Int) {
        dialog.progress = value
    }
}
