package com.example.checkmybmi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvBMI: TextView
    private lateinit var tvInterpretation: TextView
    private lateinit var btnReturn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Initialize views
        tvBMI = findViewById(R.id.tvBMI)
        tvInterpretation = findViewById(R.id.tvInterpretation)
        btnReturn = findViewById(R.id.btnReturn)

        // Retrieve data from the intent
        tvBMI.text = intent.getStringExtra(BMI)
        tvInterpretation.text = intent.getStringExtra(INTERPRETATION)


        // Set click listener on the button to return
        btnReturn.setOnClickListener {
            finish() // Close ResultActivity and return to MainActivity
        }
    }
}
