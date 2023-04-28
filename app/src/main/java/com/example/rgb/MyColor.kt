package com.example.rgb

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MyColor (
    var red: Int,
    var green: Int,
    var blue: Int,
    var colorName: String): Parcelable {

    fun getRgb(): Int {
        return Color.rgb(this.red, this.green, this.blue)
    }

    override fun toString(): String{
        return "${this.colorName}"
    }
}