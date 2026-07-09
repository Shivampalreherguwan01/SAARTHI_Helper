package com.saarthi.helper.security

object AudioFramer {

    private const val FRAME_SIZE = 400
    private const val HOP_SIZE = 160
    private const val MAX_FRAMES = 360

    fun frame(
        pcm: FloatArray
    ): Array<FloatArray> {

        val frames =
            Array(MAX_FRAMES) {
                FloatArray(FRAME_SIZE)
            }

        var offset = 0
        var index = 0

        while (
            index < MAX_FRAMES &&
            offset < pcm.size
        ) {

            for (i in 0 until FRAME_SIZE) {

                val p = offset + i

                if (p < pcm.size) {
                    frames[index][i] = pcm[p]
                }

            }

            offset += HOP_SIZE
            index++

        }

        return frames

    }

}
