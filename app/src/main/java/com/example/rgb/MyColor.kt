package com.example.rgb

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MyColor (
    var red: Int,
    var green: Int,
    var blue: Int,
    var colorName: String,
    val position: Int? = null): Parcelable {

    fun getRgb(): Int {
        return Color.rgb(this.red, this.green, this.blue)
    }

    fun hexColor(): String {
        val color = getRgb()
        return Integer.toHexString(color)
    }

    override fun toString(): String{
        return "${this.colorName}"
    }
}