package com.example.calculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.calculate.databinding.ActivityMainBinding
import kotlin.math.round
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {

    var inputDisplayArray = Array<String>(0, { "" })
    var stack = Stack<String>() // stack = []

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

        binding.Input.movementMethod = ScrollingMovementMethod.getInstance()
        binding.Input.post {
            val scrollAmount = binding.Input.layout.getLineTop(binding.Input.lineCount) - binding.Input.height
            if (scrollAmount > 0)
                binding.Input.scrollTo(0, scrollAmount)
            else
                binding.Input.scrollTo(0,0)
        }
        binding.Input.text = ""
        binding.Result.text = ""

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

        binding.btnAC.setOnClickListener {
            acClick()
        }
        binding.btnDelete.setOnClickListener {
            deleteClick()
        }
    }
    fun numClick(num: Int){
        if(inputDisplayArray.size < 11) {
            binding.Input.text = binding.Input.text.toString() + num.toString()
//                inputDisplayArray += num.toString()
        } else {
            binding.Input.textSize = 45f
            binding.Input.text = binding.Input.text.toString() + num.toString()
        }
        inputDisplayArray = inputDisplayArray.plus(num.toString())
        var test = inputDisplayArray.size.toString()
        showToast(test)
    }

    fun operClick(oper: String){
        inputDisplayArray = inputDisplayArray.plus(oper)
        binding.Input.text = binding.Input.text.toString() + oper
        stack.push(oper)
    }

    fun dotClick(dot: String){
        inputDisplayArray = inputDisplayArray.plus(dot)
        binding.Input.text = binding.Input.text.toString() + dot
    }

    fun equalClick(equal: String){
        when (equal) {

        }
    }

    fun deleteClick(){
        inputDisplayArray = inputDisplayArray.dropLast(1).toTypedArray()
//        binding.Input.text = inputDisplayArray.
    }

    fun acClick(){
        inputDisplayArray = Array<String>(0, { "" })
        binding.Input.text = " "
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

}