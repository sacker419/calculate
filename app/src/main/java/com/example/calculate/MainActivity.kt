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

    var inputDisplayList = List<Any>(0) {}
    var stack = Stack<String>() // stack = []

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Input.movementMethod = ScrollingMovementMethod.getInstance()
        binding.Input.post {
            val scrollAmount = binding.Input.layout.getLineTop(binding.Input.lineCount) - binding.Input.height
            if (scrollAmount > 0) {
                binding.Input.scrollTo(0, scrollAmount)
            }
            else {
                binding.Input.scrollTo(0, 0)
            }
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
        binding.btnDot.setOnClickListener {
            operClick(".")
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

        binding.btnAC.setOnClickListener {
            acClick()
        }
        binding.btnDelete.setOnClickListener {
            deleteClick()
        }
    }
    fun numClick(num: Int){
        var lastElement = inputDisplayList.lastOrNull()
        if(inputDisplayList.size < 11) {
            binding.Input.textSize = 60f
            if (lastElement == null) {
                inputDisplayList = inputDisplayList.plus(num)
                binding.Input.text = inputDisplayList.joinToString("")
            } else {
                if (inputDisplayList.size < 2 && inputDisplayList[0] == 0) {
                    inputDisplayList = inputDisplayList.drop(1)
                    binding.Input.text = binding.Input.text.drop(1)
                    binding.Input.text = inputDisplayList.joinToString("")
                } else {
//                inputDisplayList = inputDisplayList.dropLast(1)
                    inputDisplayList = inputDisplayList.plus(num)
                    binding.Input.text = binding.Input.text.toString() + num
                }
            }
//                inputDisplayList += num.toString()
        } else {
            binding.Input.textSize = 45f
            inputDisplayList = inputDisplayList.plus(num)
            binding.Input.text = binding.Input.text.toString() + num
        }
//        var test = inputDisplayList.size.toString()
//        showToast(test)
    }

    fun operClick(oper: String){
        var lastElement = inputDisplayList.lastOrNull()
        if (lastElement != null) {
            if (lastElement is String) {
                inputDisplayList = inputDisplayList.dropLast(1)
                inputDisplayList = inputDisplayList.plus(oper)
                binding.Input.text = inputDisplayList.joinToString("")
//                showToast(inputDisplayList.size.toString())

                if (oper == ".") {

                }



            } else if (lastElement is Int) {
                binding.Input.text = binding.Input.text.toString() + oper
                inputDisplayList = inputDisplayList.plus(oper)
//                showToast(inputDisplayList.size.toString())
                stack.push(oper)
            } else {
                showToast("error")
            }
        } else {
            inputDisplayList = inputDisplayList.plus(0).plus(oper)
            binding.Input.text = inputDisplayList.joinToString("")
//            showToast(inputDisplayList.size.toString())
        }
    }

    fun equalClick(equal: String){
        when (equal) {

        }
    }

    fun deleteClick(){
        var lastElement = inputDisplayList.lastOrNull()
        if (lastElement != null) {
            inputDisplayList = inputDisplayList.dropLast(1)
            binding.Input.text = inputDisplayList.joinToString("")
        }
    }

    fun acClick(){
        binding.Input.textSize = 60f
        inputDisplayList = List<Any>(0) {}
        binding.Input.text = ""
        binding.Result.text = ""
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

}