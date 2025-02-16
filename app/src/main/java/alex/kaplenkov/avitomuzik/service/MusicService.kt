package alex.kaplenkov.avitomuzik.service

import alex.kaplenkov.avitomuzik.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicService : Service() {

    private val binder = MusicBinder()
    private var mediaPlayer: MediaPlayer? = null
    private var currentTrackUrl: String? = null

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    fun playTrack(url: String, name: String?) {
        val trackName  = name.orEmpty()
        if (!compareMp3Prefix(currentTrackUrl, url)) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener {
                    start()
                }
                setOnCompletionListener {
                    stopSelf()
                }
            }
            currentTrackUrl = url
            startForeground(NOTIFICATION_ID, createNotification("Playing - $trackName"))
        } else {
            resumeTrack(trackName)
        }
    }

    fun pauseTrack() {
        mediaPlayer?.pause()
        updateNotification("Paused")
    }

    private fun resumeTrack(trackName: String) {
        mediaPlayer?.start()
        updateNotification("Playing - $trackName")
    }

    fun getTrackProgress(): Int = mediaPlayer?.currentPosition ?: 0
    fun getTrackDuration(): Int = mediaPlayer?.duration ?: 0
    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    private fun createNotification(content: String): Notification {
        return NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Music Player")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "music_channel",
            "Music Playback",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    private fun updateNotification(text: String) {
        val notification = createNotification(text)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun compareMp3Prefix(current: String?, url: String): Boolean {
        val prefix1 = getPrefix(current)
        val prefix2 = getPrefix(url)
        return prefix1 == prefix2
    }

    private fun getPrefix(url: String?): String? {
        val index = url?.indexOf(".mp3")
        return if (index != -1) url?.substring(0, index!! + 4) else null
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}