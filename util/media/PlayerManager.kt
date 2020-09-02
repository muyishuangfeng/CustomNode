package com.yk.silence.customnode.util.media

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.io.IOException

object PlayerManager : LifecycleObserver {
    private var player: MediaPlayer? = null

    /**
     * 播放语音
     */
    fun startPlay(fileName: String) {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 停止播放
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopPlay() {
        if (player != null) {
            player?.release()
            player = null
        }

    }
}