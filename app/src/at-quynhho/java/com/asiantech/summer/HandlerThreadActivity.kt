@file:Suppress("DEPRECATION")

package com.asiantech.summer

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-quynhho`.activity_download.btStart
import kotlinx.android.synthetic.`at-quynhho`.activity_download.imageView
import java.io.*
import java.net.URL
import java.net.URLConnection

@Suppress("DEPRECATION")
class HandlerThreadActivity : AppCompatActivity() {

    lateinit var dialog: ProgressDialog
    private lateinit var handler: Handler

    companion object {
        private var FILE_URL =
            "https://kynguyenlamdep.com/wp-content/uploads/2020/01/hinh-anh-dep-hoa-bo-cong-anh.jpg"
        private var PROGRESS_BAR = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)


        btStart.setOnClickListener {
            onClickDownload()
            handler = @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg)
                    dismissDialog(PROGRESS_BAR)
                    val imagePath =
                        Environment.getExternalStorageDirectory().toString() + "/downloadimgthreadfile.jpg"
                    imageView.setImageDrawable(Drawable.createFromPath(imagePath))
                }
            }
        }
    }

    private fun onDialog(id: Int): Dialog? {
        return when (id) {
            PROGRESS_BAR -> {
                dialog = ProgressDialog(this)
                dialog.setTitle("Handler")
                dialog.setMessage("Downloading file. Please wait...")
                dialog.isIndeterminate = false
                dialog.max = 100
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                dialog.setCancelable(true)
                dialog.show()
                return dialog
            }
            else -> null
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onClickDownload() {
        onDialog(PROGRESS_BAR)
        Thread(Runnable {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
            try {
                while (dialog.progress <= dialog.max) {
                    imageView.post {
                        dialog.incrementProgressBy(1)
                        if (dialog.progress == dialog.max) {

                            val url = URL(FILE_URL)
                            val connection: URLConnection = url.openConnection()
                            connection.doInput = true
                            connection.connect()
                            val lengthFile: Int = connection.contentLength
                            val input: InputStream = BufferedInputStream(url.openStream())
                            val fileName = "/downloadimgthreadfile.jpg"
                            val storageDir: String = Environment.getExternalStorageState()
                            val imageFile = File(storageDir + fileName)
                            val output: OutputStream = FileOutputStream(imageFile)
                            val data = ByteArray(1024)
                            var count: Int
                            var total: Long = 0
                            while (input.read(data).also { count = it } != -1) {
                                total += count.toLong()
                                val loadNum: Int = (total * 100 / lengthFile).toInt()
                                setProgressDialogValue(loadNum)
                                output.write(data, 0, count)
                            }
                            output.flush()
                            output.close()
                            input.close()
                        }
                        dialog.dismiss()

                    }
                }
            } catch (e: Exception) {
                Log.d("BBB", "exception: $e")
            }
        }).start()
    }

    private fun setProgressDialogValue(value: Int) {
        dialog.progress = value
    }
}