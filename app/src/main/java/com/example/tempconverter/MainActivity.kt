package com.example.tempconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.tempconverter.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Reasonable range
    private val MIN_TEMP_C = -273.15
    private val MAX_TEMP_C = 1_000_000.0

    enum class Scale { C, F, K }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()

        binding.convertBtn.setOnClickListener {
            binding.inputLayout.error = null

            val raw = binding.inputTemp.text?.toString()?.trim().orEmpty()

            val inputValue = raw.toDoubleOrNull()
            if (inputValue == null) {
                binding.inputLayout.error = "Please enter a valid number."
                binding.resultText.text = "Result: —"
                return@setOnClickListener
            }

            val from = positionToScale(binding.fromSpinner.selectedItemPosition)
            val to = positionToScale(binding.toSpinner.selectedItemPosition)

            // Convert input to Celsius first, then Celsius to output
            val celsius = toCelsius(inputValue, from)

            // Range validation based on Celsius equivalent
            if (celsius < MIN_TEMP_C || celsius > MAX_TEMP_C) {
                binding.inputLayout.error = "Temperature is out of reasonable range."
                binding.resultText.text = "Result: —"
                return@setOnClickListener
            }

            // Kelvin physical constraint
            if (from == Scale.K && inputValue < 0.0) {
                binding.inputLayout.error = "Kelvin cannot be below 0."
                binding.resultText.text = "Result: —"
                return@setOnClickListener
            }

            val output = fromCelsius(celsius, to)
            val outputRounded = roundTo(output, 2)

            binding.resultText.text = "Result: $outputRounded ${scaleUnit(to)}"
        }

        binding.clearBtn.setOnClickListener {
            binding.inputLayout.error = null
            binding.inputTemp.text?.clear()

            // Reset spinners (optional defaults)
            binding.fromSpinner.setSelection(0) // Celsius
            binding.toSpinner.setSelection(1)   // Fahrenheit

            binding.resultText.text = "Result: —"

            // Bring cursor back to input
            binding.inputTemp.requestFocus()
        }

        binding.swapBtn.setOnClickListener {
            val fromPos = binding.fromSpinner.selectedItemPosition
            val toPos = binding.toSpinner.selectedItemPosition

            binding.fromSpinner.setSelection(toPos)
            binding.toSpinner.setSelection(fromPos)

            // Optional: clear previous result + errors to avoid confusion
            binding.inputLayout.error = null
            binding.resultText.text = "Result: —"
        }
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.temp_scales,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.fromSpinner.adapter = adapter
        binding.toSpinner.adapter = adapter

        // Default: Celsius -> Fahrenheit
        binding.fromSpinner.setSelection(0)
        binding.toSpinner.setSelection(1)
    }

    private fun positionToScale(pos: Int): Scale = when (pos) {
        0 -> Scale.C
        1 -> Scale.F
        else -> Scale.K
    }

    private fun toCelsius(value: Double, from: Scale): Double {
        return when (from) {
            Scale.C -> value
            Scale.F -> (value - 32.0) * 5.0 / 9.0
            Scale.K -> value - 273.15
        }
    }

    private fun fromCelsius(c: Double, to: Scale): Double {
        return when (to) {
            Scale.C -> c
            Scale.F -> (c * 9.0 / 5.0) + 32.0
            Scale.K -> c + 273.15
        }
    }

    private fun scaleUnit(scale: Scale): String = when (scale) {
        Scale.C -> "°C"
        Scale.F -> "°F"
        Scale.K -> "K"
    }

    private fun roundTo(value: Double, decimals: Int): Double {
        val factor = Math.pow(10.0, decimals.toDouble())
        return round(value * factor) / factor
    }
}
