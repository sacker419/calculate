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

        binding.btnEqual.setOnClickListener {
            equalClick()
        }

        binding.btnBracketOpen.setOnClickListener {
            bracketClick("(")
        }
        binding.btnBracketClose.setOnClickListener {
            bracketClick(")")
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

    //후위 표기법으로 변환
    fun infixToPostfix(expression: String): String {
        val precedence = mapOf('+' to 1, '-' to 1, 'x' to 2, '÷' to 2)
        val output = StringBuilder()
        val stack = Stack<Char>()

        var prevChar: Char? = null // 이전 문자를 추적하기 위한 변수

        for (char in expression) {
            when {
                char.isDigit() || char == '.' -> {
                    if (prevChar != null && (prevChar.isDigit() || prevChar == '.')) {
                        // 이전 문자가 숫자나 소수점이면 공백 추가하지 않음
                        output.append(char)
                    } else {
                        // 이전 문자가 연산자거나 null이면 공백을 추가한 후 문자 추가
                        output.append(" ").append(char)
                    }
                }
                char in precedence -> {
                    while (stack.isNotEmpty() && stack.peek() in precedence && precedence[stack.peek()]!! >= precedence[char]!!) {
                        output.append(" ").append(stack.pop())
                    }
                    stack.push(char)
                }
                char == '(' -> stack.push(char)
                char == ')' -> {
                    while (stack.isNotEmpty() && stack.peek() != '(') {
                        output.append(" ").append(stack.pop())
                    }
                    stack.pop()
                }
            }
            prevChar = char // 이전 문자 업데이트
        }

        while (stack.isNotEmpty()) {
            output.append(" ").append(stack.pop())
        }

        return output.toString().trim() // 결과 문자열 양쪽의 공백 제거
    }

    fun calcPostfix(postfixExpression: String): String {
        val stack = Stack<Double>()

        for (char in postfixExpression.split(" ")) {
            when {
                char.isNumeric() -> stack.push(char.toDouble())
                char in listOf("+", "-", "x", "÷") -> {
                    val operand2 = stack.pop()
                    val operand1 = stack.pop()
                    val result = when (char) {
                        "+" -> operand1 + operand2
                        "-" -> operand1 - operand2
                        "x" -> operand1 * operand2
                        "÷" -> operand1 / operand2
                        else -> throw IllegalArgumentException("Invalid operator")
                    }
                    stack.push(result)
                }
            }
        }

        var result = stack.pop().toString()

        // 소수인 경우
        if (result.contains('.')) {
            val decimalParts = result.split(".")
            val integerPart = decimalParts[0]
            var decimalPart = decimalParts.getOrElse(1) { "" }

            if (result.toDouble() >= 1E7) {
                result = result.toDouble().toLong().toString() // 지수 형식이 아닌 정수로 변환
            } else {
                // 소수점 이하 자릿수 제한
                if (decimalPart.length > 12) {
                    decimalPart = decimalPart.substring(0, 12)
                }
                result = "$integerPart.$decimalPart"
                // 소수점 이하 0 제거
                result = result.trimEnd('0').trimEnd('.')
            }
        }

        // 천 단위마다 쉼표 추가
        val parts = result.split(".")
        val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
        result = if (parts.size > 1) "$integerPart.${parts[1]}" else integerPart

        return result
    }

    fun String.isNumeric(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun numClick(num: Int){
        val lastElement = inputDisplayList.lastOrNull()
        when {
            inputDisplayList != null -> binding.Result.text = ""
        }

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
        val lastElement = inputDisplayList.lastOrNull()
        when {
            inputDisplayList != null -> binding.Result.text = ""
        }
        if (lastElement != null) {
            if (lastElement is String) {
                inputDisplayList = inputDisplayList.dropLast(1)
                inputDisplayList = inputDisplayList.plus(oper)
                binding.Input.text = inputDisplayList.joinToString("")
//                showToast(inputDisplayList.size.toString())

            } else if (lastElement is Int) {
                binding.Input.text = binding.Input.text.toString() + oper
                inputDisplayList = inputDisplayList.plus(oper)
//                showToast(inputDisplayList.size.toString())
            } else {
                showToast("error")
            }
        } else {
            inputDisplayList = inputDisplayList.plus(0).plus(oper)
            binding.Input.text = inputDisplayList.joinToString("")
//            showToast(inputDisplayList.size.toString())
        }
    }

    fun bracketClick(bracket: String){

    }

    fun equalClick(){
        val postfix = infixToPostfix(inputDisplayList.joinToString(""))
        val result = calcPostfix(postfix)

        binding.Result.text = result
    }

    fun deleteClick(){
        val lastElement = inputDisplayList.lastOrNull()
        if (lastElement != null) {
            inputDisplayList = inputDisplayList.dropLast(1)
            binding.Input.text = inputDisplayList.joinToString("")
            binding.Result.text = ""
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