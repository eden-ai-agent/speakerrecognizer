package com.voicelog

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class VoiceProcessor(private val listener: Listener) {
    interface Listener {
        fun onBufferReady(data: ShortArray)
    }

    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private var isRecording = false

    private val sampleRate = 16000
    private val chunkSize = sampleRate // one second of audio

    fun start() {
        if (isRecording) return
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            chunkSize
        )
        audioRecord?.startRecording()
        isRecording = true
        recordingThread = Thread {
            val buffer = ShortArray(chunkSize)
            while (isRecording) {
                var offset = 0
                while (offset < chunkSize && isRecording) {
                    val read = audioRecord?.read(buffer, offset, chunkSize - offset) ?: 0
                    if (read > 0) {
                        offset += read
                    }
                }
                if (isRecording) {
                    listener.onBufferReady(buffer.copyOf())
                }
            }
        }.also { it.start() }
    }

    fun stop() {
        isRecording = false
        try {
            recordingThread?.join()
        } catch (_: InterruptedException) {
        }
        recordingThread = null
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}
