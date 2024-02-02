package com.example.calculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculate.databinding.ActivityMainBinding
import kotlin.math.round
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var result : TextView? = null
    private var input : TextView? = null
    private var firstNum : Double = 0.0
    private var secondNum : Double = 0.0
    private var resultNum : Double = 0.0
    private var Oper : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Input.text = " "
        binding.Result.text = " "

        binding.btnDevide.setOnClickListener {
            operClick("÷")
        }
        binding.btnMulti.setOnClickListener {
            operClick("x")
        }
        binding.btnMinus.setOnClickListener {
            operClick("-")
        }
        binding.btnPLus.setOnClickListener {
            operClick("+")
        }

        binding.btnNum1.setOnClickListener {
            numClick(1)
        }
        binding.btnNum2.setOnClickListener {
            numClick(2)
        }
        binding.btnNum3.setOnClickListener {
            numClick(3)
        }
        binding.btnNum4.setOnClickListener {
            numClick(4)
        }
        binding.btnNum5.setOnClickListener {
            numClick(5)
        }
        binding.btnNum6.setOnClickListener {
            numClick(6)
        }
        binding.btnNum7.setOnClickListener {
            numClick(7)
        }
        binding.btnNum8.setOnClickListener {
            numClick(8)
        }
        binding.btnNum9.setOnClickListener {
            numClick(9)
        }
        binding.btnNum0.setOnClickListener {
            numClick(0)
        }

        binding.btnDot.setOnClickListener {
            dotClick(".")
        }

    }
    fun numClick(num: Int){
        when (num) {
            1 -> binding.Input.text = "1"
            2 -> binding.Input.text = "2"
            3 -> binding.Input.text = "3"
            4 -> binding.Input.text = "4"
            5 -> binding.Input.text = "5"
            6 -> binding.Input.text = "6"
            7 -> binding.Input.text = "7"
            8 -> binding.Input.text = "8"
            9 -> binding.Input.text = "9"
            0 -> binding.Input.text = "0"
        }
    }

    fun operClick(oper: String){
        when (oper) {
            "÷" -> binding.Input.text = "÷"
            "x" -> binding.Input.text = "x"
            "-" -> binding.Input.text = "-"
            "+" -> binding.Input.text = "+"
        }
    }

    fun dotClick(dot: String){
        when (dot) {
            "." -> binding.Input.text ="."
        }
    }

    fun equalClick(equal: String){
        when (equal) {

        }
    }

    fun deleteClick(){

    }

    fun acClick(){
        result?.text = ""
        input?.text = ""
        firstNum = 0.0
        secondNum = 0.0
        Oper = ""
        resultNum = 0.0
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

}