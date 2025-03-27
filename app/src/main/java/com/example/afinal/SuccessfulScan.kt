package com.example.afinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SuccessfulScan : AppCompatActivity() { // Capitalized class name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sucessfulscan) // Ensure this matches your XML file


        val scanAgainButton= findViewById<Button>(R.id.scan_again_button)

        scanAgainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Navigate to mainActivity
        }
    }
}
