package com.saarthi.helper.security

class AudioFeatureExtractor {

    fun extract(audioPath: String): Array<Array<FloatArray>> {

        /*
         अभी placeholder है।
         अगले step में:
         PCM -> STFT -> Mel Filter -> Log Mel
         बनाकर यही method [1][360][80] return करेगी।
        */

        return Array(1) {
            Array(360) {
                FloatArray(80)
            }
        }

    }

}
