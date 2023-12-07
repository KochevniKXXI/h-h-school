package hh.school.lesson_9_zemskov

import android.os.Build
import java.io.OutputStream
import java.net.URL
import java.util.zip.ZipInputStream

fun URL.downloadAndUnzip(
    output: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    progress: ((Long, Long) -> Unit)? = null
) {
    val connection = openConnection()
    connection.connect()
    val length = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        connection.contentLengthLong
    } else {
        connection.contentLength.toLong()
    }

    ZipInputStream(openStream()).use { zipInput ->
        var bytesCopied: Long = 0
        val buffer = ByteArray(bufferSize)
        while (zipInput.nextEntry != null) {
            var bytes = zipInput.read(buffer)
            while (bytes >= 0) {
                output.write(buffer, 0, bytes)
                bytesCopied += bytes
                progress?.invoke(bytesCopied, length)
                bytes = zipInput.read(buffer)
            }
        }
    }
}