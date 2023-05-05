package com.example.rgb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rgb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val adapter = MyAdapter{
        openFormColorActivity(it)
    }
    private lateinit var binding: ActivityMainBinding
    private val colors: MutableList<MyColor> by lazy { mutableListOf() }
    private var formResult =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getParcelableExtra("COLOR", MyColor::class.java)
            } else {
                it.data?.getParcelableExtra<MyColor>("COLOR")
            }
            color?.let {myColor ->
                binding.rvColorNames.adapter = adapter
                binding.rvColorNames.layoutManager = LinearLayoutManager(this)
                if (myColor.position == null){
                    this@MainActivity.colors.add(myColor)
                } else {
                    colors.removeAt(myColor.position)
                    colors.add(myColor.position, myColor)
                }
                adapter.submitList(this@MainActivity.colors)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.fbAdd.setOnClickListener {
            openFormColorActivity()
        }
        ItemTouchHelper(OnSwipe()).attachToRecyclerView(binding.rvColorNames)
    }

    private fun openFormColorActivity(myColor: MyColor? = null) {
            val intent = Intent(this, FormColorActivity::class.java)
            intent.action = "NOVACOR"
            intent.putExtra("EDITCOLOR", myColor)
            formResult.launch(intent)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == 200){
//            val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                data?.getParcelableExtra("COLOR", MyColor::class.java)
//            } else {
//                data?.getParcelableExtra<MyColor>("COLOR")
//            }
//            color?.let{
//                this.colors.add(it)
//                adapter.submitList(this.colors)
//            }
//        }
//    }
//        ItemTouchHelper(OnSwipe()).attachToRecyclerView(binding.rvColorNames)

//        var formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            if (it.resultCode == RESULT_OK){
//                val cor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    it.data?.getSerializableExtra("COR", MyColor::class.java)
//                } else {
//                    it.data?.getSerializableExtra("COR")
//                } as MyColor
//                (binding.rvColorNames.adapter as MyAdapter).add(cor)
//
//
//            }
//        }

//        binding.fbAdd.setOnClickListener({
//            val intent = Intent()
//            intent.action = "NOVACOR"
//            formResult.launch(intent)
//        })

//    inner class OnItemClick: OnItemClickRecyclerView {
//        override fun onItemClick(position: Int) {
//            val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.activity_form, null)
//            val sbRed = view.findViewById<SeekBar>(R.id.sbRed)
//            val sbGreen = view.findViewById<SeekBar>(R.id.sbGreen)
//            val sbBlue = view.findViewById<SeekBar>(R.id.sbBlue)
//
//
//            val colorSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//                    val redValue = sbRed.progress
//                    val greenValue = sbGreen.progress
//                    val blueValue = sbBlue.progress
//                    val color = Color.rgb(redValue, greenValue, blueValue)
//
//                }
//
//                override fun onStartTrackingTouch(seekBar: SeekBar) {
//                    // Não é necessário fazer nada aqui
//                }
//
//                override fun onStopTrackingTouch(seekBar: SeekBar) {
//                    // Não é necessário fazer nada aqui
//                }
//            }
//
//            sbRed.setOnSeekBarChangeListener(colorSeekBarChangeListener)
//            sbGreen.setOnSeekBarChangeListener(colorSeekBarChangeListener)
//            sbBlue.setOnSeekBarChangeListener(colorSeekBarChangeListener)
//        }
//
        inner class OnSwipe : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                (binding.rvColorNames.adapter as MyAdapter).mov(
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
                return true
            }

            @SuppressLint("SuspiciousIndentation")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.END) {
                    run {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Excluir item")
                            .setMessage("Tem certeza que deseja excluir esta cor?")
                            .setPositiveButton("Sim") { dialog, _ ->
                                colors.removeAt(position)
                                adapter.submitList(colors)
                                dialog.dismiss()
                            }
                            .setNegativeButton("Não") { dialog, _ ->
                                (binding.rvColorNames.adapter as MyAdapter)
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                } else{
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        val color = colors[position]
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Estou enviando uma cor em RGB!")
                        putExtra(Intent.EXTRA_TEXT, "Nome hexadecimal: ${color.hexColor()} red: ${color.red} green: ${color.green} blue: ${color.blue}")
                    }
                    val shareIntent = Intent.createChooser(intent, null)
                    startActivity(shareIntent)
                }
            }
        }

}
