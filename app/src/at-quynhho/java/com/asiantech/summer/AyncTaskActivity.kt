@file:Suppress("DEPRECATION")

package com.asiantech.summer

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-quynhho`.activity_aync_task.*
import java.io.*
import java.net.URL
import java.net.URLConnection

@Suppress("DEPRECATION")
class AyncTaskActivity : AppCompatActivity() {

    lateinit var dialog: ProgressDialog


    companion object {
        private var FILD_URL = "https://kynguyenlamdep.com/wp-content/uploads/2020/01/hinh-anh-dep-hoa-bo-cong-anh.jpg"
        private var PROGRESS_BAR = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aync_task)

        val url = URL(FILD_URL)
        btStart.setOnClickListener {
            MyAsyncTask().execute(url.toString())
        }
    }

    override fun onCreateDialog(id: Int): Dialog? {
        return when (id) {
            PROGRESS_BAR -> {
                dialog = ProgressDialog(this)
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

    @SuppressLint("StaticFieldLeak")
    inner class MyAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            showDialog(PROGRESS_BAR)
        }

        override fun doInBackground(vararg fileUrl: String): String {

            var count : Int
            try {
                val url = URL(fileUrl[0])
                val connection: URLConnection = url.openConnection()
                connection.doInput = true
                connection.connect()
                Log.d("AAA", "total: $connection")
                val lenghtFile: Int = connection.contentLength
                val input: InputStream = BufferedInputStream(url.openStream(), 8192)
                val storageDir: String =
                    Environment.getExternalStorageDirectory().absolutePath
                val fileName = "/downloadimgfile.jpg"
                val imageFile = File(storageDir + fileName)
                val output: OutputStream = FileOutputStream(imageFile)
                val data = ByteArray(1024)
                var total: Long = 0
                while (input.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    publishProgress("" + (total * 100 / lenghtFile).toInt())
                    output.write(data, 0, count)
                }
                output.flush()
                output.close()
                input.close()
            } catch (e: java.lang.Exception) {
                Log.e("Error: ", e.message)
            }
            return null.toString()
        }

        override fun onProgressUpdate(vararg progress: String) {
            dialog.progress = progress[0].toInt()
        }

        override fun onPostExecute(file_url: String?) {
            dismissDialog(PROGRESS_BAR)
            val imagePath =
                Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg"
            imageView.setImageDrawable(Drawable.createFromPath(imagePath))
        }
    }
}



