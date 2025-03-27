package com.example.afinal

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("ScanPrefs", MODE_PRIVATE)
        val scanButton = findViewById<Button>(R.id.scan_now_button)

        scanButton.setOnClickListener {
            val scanCount = sharedPreferences.getInt("scanCount", 0)

            val nextActivity = if (scanCount % 2 == 0) {
                SuccessfulScan::class.java // Even -> UnsuccessfulScan
            } else {
                UnsuccessfulScan::class.java // Odd -> SuccessfulScan
            }

            // Increment scanCount and store it
            sharedPreferences.edit().putInt("scanCount", scanCount + 1).apply()

            val intent = Intent(this, nextActivity)
            startActivity(intent)
        }
    }
}
