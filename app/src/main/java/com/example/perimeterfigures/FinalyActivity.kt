package com.example.perimeterfigures
import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class FinalyActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finaly)

        val returnImage = findViewById<ImageView>(R.id.returnImage)
        val viewImage = findViewById<ImageView>(R.id.viewImage)
        val resultTextView = findViewById<TextView>(R.id.result_text_view2)
        val check = intent.getIntExtra("check", 0)
        val arithmeticOperation = intent.getStringExtra("arithmeticOperation")

        when (check) {
            1 -> {
                val textResult = "P круга:$arithmeticOperation"
                resultTextView.text = textResult
                viewImage.setImageResource(R.drawable.circle)
            }
            2 -> {
                val textResult = "P треугольника:$arithmeticOperation"
                resultTextView.text = textResult
                viewImage.setImageResource(R.drawable.triangle)
            }
            3 -> {
                val textResult = "P прямоугольника:$arithmeticOperation"
                resultTextView.text = textResult
                viewImage.setImageResource(R.drawable.rectangle)
            }
        }
        returnImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}