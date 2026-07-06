package com.saarthi.helper

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import com.saarthi.helper.service.HelperService

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = TextView(this)
        text.text = "SAARTHI Helper Running 🚀"
        text.textSize = 24f

        setContentView(text)

        val intent = Intent(this, HelperService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}
