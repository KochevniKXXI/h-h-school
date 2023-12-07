package hh.school.lesson_9_zemskov.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE
import androidx.core.app.ServiceCompat
import hh.school.lesson_9_zemskov.MainActivity
import hh.school.lesson_9_zemskov.R
import hh.school.lesson_9_zemskov.downloadAndUnzip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadService : Service() {

    private val intentProgress by lazy {
        Intent(MainActivity.PROGRESS_RECEIVER_ACTION).apply {
            setPackage(
                this@DownloadService.packageName
            )
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val KEY_URL = "key_url"
        const val KEY_PROGRESS = "key_progress"
        const val KEY_FILE_URI = "key_file_uri"

        fun createStartIntent(context: Context, url: String) =
            Intent(context, DownloadService::class.java).apply {
                putExtra(KEY_URL, url)
            }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ServiceCompat.startForeground(
            this,
            100,
            createNotification(),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            }
        )
        CoroutineScope(Dispatchers.IO).launch {
            URL(intent?.getStringExtra(KEY_URL)).downloadAndUnzip(
                FileOutputStream(File(filesDir, "image.jpg"))
            ) { bytesCopied, length ->
                val percents = (bytesCopied * 100 / length).toInt()
                sendBroadcast(intentProgress.apply {
                    putExtra(KEY_PROGRESS, percents)
                })
            }

            sendBroadcast(intentProgress.apply {
                putExtra(
                    KEY_FILE_URI,
                    File(filesDir, "image.jpg").path
                )
            })
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.notification_download_title))
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setForegroundServiceBehavior(FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }
}