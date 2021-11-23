package com.mel.kalku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .setFragmentResultListener("resetKey", this) { requestKey, bundle ->
                val intent = intent
                finish()
                startActivity(intent)
            }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}