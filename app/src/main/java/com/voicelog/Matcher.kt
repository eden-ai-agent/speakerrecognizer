package com.voicelog

import kotlin.math.sqrt

class Matcher(private val db: SpeakerDB) {

    private fun cosine(a: FloatArray, b: FloatArray): Float {
        var dot = 0f
        var normA = 0f
        var normB = 0f
        val len = minOf(a.size, b.size)
        for (i in 0 until len) {
            dot += a[i] * b[i]
            normA += a[i] * a[i]
            normB += b[i] * b[i]
        }
        return if (normA == 0f || normB == 0f) 0f else dot / sqrt(normA * normB)
    }

    fun isSelf(embedding: FloatArray, threshold: Float = 0.8f): Boolean {
        val self = db.selfSpeaker ?: return false
        return cosine(embedding, self.embedding) >= threshold
    }

    fun match(embedding: FloatArray, threshold: Float = 0.8f): Speaker? {
        var best: Speaker? = null
        var bestScore = 0f
        for (speaker in db.allSpeakers()) {
            val score = cosine(embedding, speaker.embedding)
            if (score > bestScore) {
                bestScore = score
                best = speaker
            }
        }
        return if (bestScore >= threshold) best else null
    }
}
