package com.voicelog

data class Speaker(
    val name: String,
    val phone: String?,
    val embedding: FloatArray
)

class SpeakerDB {
    private val speakers = mutableListOf<Speaker>()

    fun addSpeaker(speaker: Speaker) {
        speakers.add(speaker)
    }

    fun allSpeakers(): List<Speaker> = speakers.toList()
}
