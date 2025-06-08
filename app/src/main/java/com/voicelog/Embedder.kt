package com.voicelog

class Embedder {
    fun embed(audioData: ShortArray): FloatArray {
        // Placeholder embedding: convert first 16 samples to normalized floats
        val length = 16
        val embedding = FloatArray(length)
        for (i in 0 until length) {
            embedding[i] =
                if (i < audioData.size) audioData[i].toFloat() / Short.MAX_VALUE else 0f
        }
        return embedding
    }
}
