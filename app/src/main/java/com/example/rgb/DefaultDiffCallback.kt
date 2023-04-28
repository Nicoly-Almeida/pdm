package com.example.rgb

import androidx.recyclerview.widget.DiffUtil

class DefaultDiffCallback : DiffUtil.ItemCallback<MyColor>() {

    override fun areItemsTheSame(oldItem: MyColor, newItem: MyColor): Boolean {
        return oldItem.colorName == newItem.colorName
    }

    override fun areContentsTheSame(oldItem: MyColor, newItem: MyColor): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}