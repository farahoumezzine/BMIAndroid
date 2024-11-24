package com.example.checkmybmi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

const val BMI = "bmi"
const val INTERPRETATION = "interpretation"

const val SHARED_PREFS = "mySharedPrefs"
const val PREVIOUS_WEIGHT_VALUE = "previous_weight_value"
const val PREVIOUS_HEIGHT_VALUE = "previous_Height_value"

class MainActivity : AppCompatActivity() {

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnCalculateBMI: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        btnCalculateBMI = findViewById(R.id.btnCalculateBMI)
        loadData()

        // Set click listener on the button
        btnCalculateBMI.setOnClickListener {
            calculateBMI()
        }
    }

    // Method to save data
    private fun save() {
        val sharedPrefs = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(PREVIOUS_HEIGHT_VALUE, etHeight.text.toString())
        editor.putString(PREVIOUS_WEIGHT_VALUE, etWeight.text.toString())
        editor.apply() // Use commit() if you need synchronous saving
    }


    private fun loadData() {
        val sharedPrefs = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val heightValue = sharedPrefs.getString(PREVIOUS_HEIGHT_VALUE, "")
        val weightValue = sharedPrefs.getString(PREVIOUS_WEIGHT_VALUE, "")
        etHeight.setText(heightValue)
        etWeight.setText(weightValue)
    }

    private fun calculateBMI() {
        // Get input values

        val weight = etWeight.text.toString().toFloatOrNull()
        val height = etHeight.text.toString().toFloatOrNull()

        if (weight != null && height != null && height > 0.5 && height < 3 && weight > 0) {
            // Calculate BMI
            val bmi = weight / (height * height)
            val bmiFormatted = String.format("%.2f", bmi)

            // Interpret the result
            val interpretation = when {
                bmi < 18.5 -> "Underweight"
                bmi in 18.5..24.9 -> "Normal weight"
                bmi in 25.0..29.9 -> "Overweight"
                else -> "Obesity"
            }

            // Save data in SharedPreferences before navigating to ResultActivity
            save()

            // Send data to ResultActivity via an Intent
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra(BMI, "Your BMI: $bmiFormatted")
                putExtra(INTERPRETATION, interpretation)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
        }
    }
}
