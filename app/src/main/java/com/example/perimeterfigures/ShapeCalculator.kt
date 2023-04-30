package com.example.perimeterfigures

class ShapeCalculator {
    fun calculateCirclePerimeter(radius: Double): Double {
        return 2 * Math.PI * radius
    }
    fun calculateTrianglePerimeter(side1: Double, side2: Double, side3: Double): Double {
        return side1 + side2 + side3
    }
    fun calculateRectanglePerimeter(length: Double, width: Double): Double {
        return 2 * length + 2 * width
    }
}
