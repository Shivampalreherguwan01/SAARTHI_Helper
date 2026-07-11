package com.saarthi.helper.voice

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicBoolean

class AudioRecorder(
    private val outputFile: File
) {

    companion object {
        private const val SAMPLE_RATE = 16000
    }

    private val recording = AtomicBoolean(false)

    private var audioRecord: AudioRecord? = null
    private var worker: Thread? = null

    fun start(): Boolean {

        if (recording.get()) {
            return false
        }

        val minBuffer = AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        if (minBuffer <= 0) {
            return false
        }

        val recorder = AudioRecord(
            MediaRecorder.AudioSource.VOICE_RECOGNITION,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            minBuffer * 2
        )

        if (recorder.state != AudioRecord.STATE_INITIALIZED) {
            recorder.release()
            return false
        }

        outputFile.parentFile?.mkdirs()

        audioRecord = recorder
        recording.set(true)

        worker = Thread {

            val pcmFile = File(
                outputFile.parentFile,
                "${outputFile.name}.pcm"
            )

            try {

                FileOutputStream(pcmFile).use { output ->

                    val buffer = ByteArray(minBuffer)

                    recorder.startRecording()

                    while (recording.get()) {

                        val count = recorder.read(
                            buffer,
                            0,
                            buffer.size
                        )

                        if (count > 0) {
                            output.write(buffer, 0, count)
                        }
                    }
                }

            } finally {

                try {
                    recorder.stop()
                } catch (_: Exception) {
                }

                recorder.release()

                writeWav(pcmFile, outputFile)

                pcmFile.delete()
            }

        }.apply {
            name = "SaarthiAudioRecorder"
            start()
        }

        return true
    }

    fun stop(): File? {

        if (!recording.getAndSet(false)) {
            return null
        }

        try {
            worker?.join(3000)
        } catch (_: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        worker = null
        audioRecord = null

        return if (
            outputFile.exists() &&
            outputFile.length() > 44
        ) {
            outputFile
        } else {
            null
        }
    }

    private fun writeWav(
        pcmFile: File,
        wavFile: File
    ) {

        if (!pcmFile.exists()) {
            return
        }

        val pcm = pcmFile.readBytes()

        FileOutputStream(wavFile).use { output ->

            val header = ByteBuffer
                .allocate(44)
                .order(ByteOrder.LITTLE_ENDIAN)

            header.put("RIFF".toByteArray())
            header.putInt(36 + pcm.size)
            header.put("WAVE".toByteArray())

            header.put("fmt ".toByteArray())
            header.putInt(16)
            header.putShort(1.toShort())
            header.putShort(1.toShort())
            header.putInt(SAMPLE_RATE)
            header.putInt(SAMPLE_RATE * 2)
            header.putShort(2.toShort())
            header.putShort(16.toShort())

            header.put("data".toByteArray())
            header.putInt(pcm.size)

            output.write(header.array())
            output.write(pcm)
        }
    }
}
