package com.example.afinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class UnsuccessfulScan : AppCompatActivity() { // Capitalized class name
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.unsuccessfulscan) // Ensure this matches your XML file

        val tryAgainButton= findViewById<Button>(R.id.try_again)

        tryAgainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) // Navigate to mainActivity
        }
    }
}
