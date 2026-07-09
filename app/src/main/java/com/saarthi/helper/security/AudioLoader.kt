package com.saarthi.helper.security

import java.io.File

object AudioLoader {

    fun load(
        audioPath: String
    ): FloatArray {

        val file =
            File(audioPath)

        if (!file.exists()) {
            return FloatArray(0)
        }

        val bytes =
            file.readBytes()

        if (bytes.size <= 44) {
            return FloatArray(0)
        }

        val pcm =
            FloatArray((bytes.size - 44) / 2)

        var index = 44
        var sample = 0

        while (index + 1 < bytes.size) {

            val low =
                bytes[index].toInt() and 0xff

            val high =
                bytes[index + 1].toInt()

            val value =
                (high shl 8) or low

            pcm[sample] =
                value / 32768f

            sample++
            index += 2

        }

        return pcm

    }

}
