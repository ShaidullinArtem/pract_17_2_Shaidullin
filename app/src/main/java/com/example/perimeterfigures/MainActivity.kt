package com.example.perimeterfigures

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var shape: String
    private lateinit var shapeCalculator: ShapeCalculator
    private lateinit var spinner: Spinner

    @SuppressLint("SetTextI18n", "MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        shape = sharedPreferences.getString("selected_shape", "")!!

        spinner = findViewById<Spinner>(R.id.spinner)
        val resultTextView = findViewById<TextView>(R.id.result_text_view)
        val calculateButton = findViewById<Button>(R.id.calculate_button)
        val editText1 = findViewById<EditText>(R.id.input_value)
        val editText2 = findViewById<EditText>(R.id.input_value2)
        val editText3 = findViewById<EditText>(R.id.input_value3)
        var check = 0

        shapeCalculator = ShapeCalculator()

        ArrayAdapter.createFromResource(
            this,
            R.array.shapes_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(getShapeIndex(shape))
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                val selectedShape = parent?.getItemAtPosition(position).toString()
                when (selectedShape) {
                    "Круг" -> {
                        editText1.hint = "Введите радиус"
                        editText1.visibility = View.VISIBLE
                        editText2.visibility = View.GONE
                        editText3.visibility = View.GONE
                        editText1.text = null;
                        editText2.text = null;
                        editText3.text = null;
                        check = 1
                    }
                    "Треугольник" -> {
                        editText1.hint = "Введите длину стороны A"
                        editText2.hint = "Введите длину стороны B"
                        editText3.hint = "Введите длину стороны C"
                        editText1.visibility = View.VISIBLE
                        editText2.visibility = View.VISIBLE
                        editText3.visibility = View.VISIBLE
                        editText1.text = null;
                        editText2.text = null;
                        editText3.text = null;
                        check = 2
                    }
                    "Прямоугольник" -> {
                        editText1.hint = "Введите длину"
                        editText2.hint = "Введите ширину"
                        editText1.visibility = View.VISIBLE
                        editText2.visibility = View.VISIBLE
                        editText3.visibility = View.GONE
                        editText1.text = null;
                        editText2.text = null;
                        check = 3
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        calculateButton.setOnClickListener {
            when (check) {
                1 -> {
                    if (editText1.text.toString().isEmpty()){
                        alertShow()
                        return@setOnClickListener
                    }
                }
                2 -> {
                    if (editText1.text.toString().isEmpty() || editText2.text.toString().isEmpty()
                        || editText3.text.toString().isEmpty()){
                        alertShow()
                        return@setOnClickListener
                    }
                }
                3 -> {
                    if (editText1.text.toString().isEmpty() || editText2.text.toString().isEmpty()){
                        alertShow()
                        return@setOnClickListener
                    }
                }
            }

            val inputValue1 = editText1.text.toString()
            val inputValue2 = editText2.text.toString()
            val inputValue3 = editText3.text.toString()

            val result = when (spinner.selectedItemPosition) {
                0 -> shapeCalculator.calculateCirclePerimeter(inputValue1.toDouble())
                1 -> shapeCalculator.calculateTrianglePerimeter(inputValue1.toDouble(),
                    inputValue2.toDouble(), inputValue3.toDouble())
                else -> shapeCalculator.calculateRectanglePerimeter(inputValue1.toDouble(),
                    inputValue2.toDouble())
            }

            when (check) {
                1 -> {
                    val intent = Intent(this, FinalyActivity::class.java)
                    intent.putExtra("result", result)
                    intent.putExtra("check", check)
                    intent.putExtra("arithmeticOperation",
                        "\n2 × Pi × $inputValue1 = $result")
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this, FinalyActivity::class.java)
                    intent.putExtra("result", result)
                    intent.putExtra("check", check)
                    intent.putExtra("arithmeticOperation",
                        "\n$inputValue1 + $inputValue2 + $inputValue3 = $result")
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(this, FinalyActivity::class.java)
                    intent.putExtra("result", result)
                    intent.putExtra("check", check)
                    intent.putExtra("arithmeticOperation",
                        "\n2 × $inputValue1 + 2 × $inputValue2 = $result")
                    startActivity(intent)
                }
            }


            when (check) {
                1 -> {
                    val textResult = "P круга = $result"
                    resultTextView.text = textResult
                    with(sharedPreferences.edit()) {
                        putString("result", textResult)
                        apply()
                    }
                }
                2 -> {
                    val textResult = "P треугольника = $result"
                    resultTextView.text = textResult
                    with(sharedPreferences.edit()) {
                        putString("result", textResult)
                        apply()
                    }
                }
                3 -> {
                    val textResult = "P прямоугольника = $result"
                    resultTextView.text = textResult
                    with(sharedPreferences.edit()) {
                        putString("result", textResult)
                        apply()
                    }
                }
            }

        }
        val savedResult = sharedPreferences.getString("result", "")
        resultTextView.text = savedResult


    }

    private fun getShapeIndex(shape: String): Int {
        return when (shape) {
            "Круг" -> 0
            "Треугольник" -> 1
            "Прямоугольник" -> 2
            else -> 0
        }
    }

    override fun onStop() {
        super.onStop()
        with(sharedPreferences.edit()) {
            putString("selected_shape", spinner.selectedItem.toString())
            apply()
        }
    }

    private fun alertShow() {
        AlertDialog.Builder(this)
            .setTitle("Ошибка")
            .setMessage("У вас есть незаполненные поле(я).")
            .setPositiveButton("Ok", null)
            .create()
            .show()
    }
}