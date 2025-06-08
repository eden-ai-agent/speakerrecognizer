package com.voicelog

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val REQUEST_RECORD_AUDIO = 1

class MainActivity : AppCompatActivity(), VoiceProcessor.Listener {
    private lateinit var voiceProcessor: VoiceProcessor
    private val embedder = Embedder()
    private val db = SpeakerDB()
    private val matcher = Matcher(db)
    private val logDB = LogDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        voiceProcessor = VoiceProcessor(this)
    }

    override fun onResume() {
        super.onResume()
        startListening()
    }

    override fun onPause() {
        super.onPause()
        voiceProcessor.stop()
    }

    private fun startListening() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            voiceProcessor.start()
        } else {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            voiceProcessor.start()
        }
    }

    override fun onBufferReady(data: ShortArray) {
        val embedding = embedder.embed(data)
        if (matcher.isSelf(embedding)) {
            return
        }
        val match = matcher.match(embedding)
        if (match != null) {
            runOnUiThread {
                val info = match.phone?.let { "${'$'}{match.name} (${ '$' }it)" } ?: match.name
                Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
                logDB.addLogEntry(match.name, match.phone, System.currentTimeMillis())
            }
        } else {
            runOnUiThread {
                AddSpeakerDialog(this) { name, phone, isSelf ->
                    val speaker = Speaker(name, phone, embedding)
                    if (isSelf) {
                        db.setSelfSpeaker(speaker)
                    } else {
                        db.addSpeaker(speaker)
                        logDB.addLogEntry(name, phone, System.currentTimeMillis())
                    }
                }.show()
            }
        }
    }
}
