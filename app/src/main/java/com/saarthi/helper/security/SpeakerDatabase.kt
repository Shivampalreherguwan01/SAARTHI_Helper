package com.saarthi.helper.security

import android.content.Context
import org.json.JSONArray

class SpeakerDatabase(
    private val context: Context
) {

    private val prefs =
        context.getSharedPreferences(
            "speaker_db",
            Context.MODE_PRIVATE
        )

    fun save(embedding: FloatArray) {

        val json = JSONArray()

        embedding.forEach {

            json.put(it.toDouble())

        }

        prefs.edit()

            .putString(
                "voice_embedding",
                json.toString()
            )

            .apply()

    }

    fun load(): FloatArray? {

        val raw =
            prefs.getString(
                "voice_embedding",
                null
            ) ?: return null

        val json = JSONArray(raw)

        val result =
            FloatArray(json.length())

        for (i in 0 until json.length()) {

            result[i] =
                json.getDouble(i).toFloat()

        }

        return result

    }

    fun exists(): Boolean {

        return prefs.contains("voice_embedding")

    }

    fun clear() {

        prefs.edit().clear().apply()

    }

}
