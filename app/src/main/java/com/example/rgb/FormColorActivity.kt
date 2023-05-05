package com.example.rgb

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.rgb.databinding.ActivityFormBinding

class FormColorActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EDITCOLOR", MyColor::class.java)
        } else {
            intent.getParcelableExtra<MyColor>("EDITCOLOR")
        }

        color?.let {
            binding.etColor.setText(color.colorName)
            binding.sbBlue.progress = color.blue
            binding.tvBlue.text = color.blue.toString()
            binding.sbGreen.progress = color.green
            binding.tvGreen.text = color.green.toString()
            binding.sbRed.progress = color.red
            binding.tvRed.text = color.red.toString()
        }

        binding.sbRed.setOnSeekBarChangeListener(OnChangeColor())
        binding.sbGreen.setOnSeekBarChangeListener(OnChangeColor())
        binding.sbBlue.setOnSeekBarChangeListener(OnChangeColor())

        binding.btnSave.setOnClickListener({save(color)})
        binding.btnCancel.setOnClickListener({ cancel() })
    }
        fun createColor(): Int{
            val red = binding.sbRed.progress
            val green = binding.sbGreen.progress
            val blue = binding.sbBlue.progress
            return Color.rgb(red, green, blue)
        }

        fun invertColor(): Int{
            val red = binding.sbRed.progress
            val green = binding.sbGreen.progress
            val blue = binding.sbBlue.progress
            return Color.rgb(255 - red, 255 - green, 255 - blue)
        }

        fun save(myColor: MyColor?){
            val red = binding.sbRed.progress
            val green = binding.sbGreen.progress
            val blue = binding.sbBlue.progress
            val name = binding.etColor.text.toString()
            val cor = myColor?.copy(red = red, green = green, blue = blue, colorName = name) ?: MyColor(red, green, blue, name)

            val intent = Intent().apply {
                putExtra("COLOR", cor)
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        fun cancel(){
            finish()
        }

        inner class OnChangeColor: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val red = binding.sbRed.progress
                val green = binding.sbGreen.progress
                val blue = binding.sbBlue.progress

                binding.tvRed.text = red.toString()
                binding.tvGreen.text = green.toString()
                binding.tvBlue.text = blue.toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//            TODO("Not yet implemented")
            }
        }

    }
