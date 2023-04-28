package com.example.rgb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.rgb.databinding.ColorItemBinding
import java.util.*

class MyAdapter(private val onItemClick: (MyColor) -> Unit) : ListAdapter<MyColor , MyHolder>(DefaultDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ColorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }





//
//    override fun getItemCount(): Int = this.colorList.size
//
//    fun add(myColor: MyColor){
//        this.colorList.add(myColor)
//        this.notifyDataSetChanged()
//    }
//
//    fun del(position: Int){
//        this.colorList.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, this.colorList.size)
//        this.notifyDataSetChanged()
//    }
//
//    fun mov(from: Int, to: Int){
//        Collections.swap(this.colorList, from, to)
//        notifyItemMoved(from, to)
//    }




}

class MyHolder(val binding: ColorItemBinding): ViewHolder(binding.root){

    fun bind(myColor : MyColor, onItemClick: (MyColor) -> Unit){
        binding.tvColorName.text = myColor.colorName
        binding.root.setOnClickListener {
            onItemClick(myColor)
        }
    }
}