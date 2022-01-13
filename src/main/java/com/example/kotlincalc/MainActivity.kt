package com.example.kotlincalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    //Initialize global variables
    private var fn:Double = 0.0
    private var sn:Double = 0.0
    private var op:Int = 0
    private val df = DecimalFormat() //decimal formatter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //formatter
        df.isDecimalSeparatorAlwaysShown = false
        //operations_main
        val btnPlus = findViewById<Button>(R.id.btn_plus)
        val btnMinus = findViewById<Button>(R.id.btn_minus)
        val btnTimes = findViewById<Button>(R.id.btn_times)
        val btnDivide = findViewById<Button>(R.id.btn_divide)
        val btnEquals = findViewById<Button>(R.id.btn_equals)
        //operations_sub
        val btnPercent = findViewById<Button>(R.id.btn_percent)
        val btnClearEntry = findViewById<Button>(R.id.btn_clear_entry)
        val btnClearAll = findViewById<Button>(R.id.btn_clear_all)
        val btnBackspace = findViewById<Button>(R.id.btn_backspace)
        val btnFraction = findViewById<Button>(R.id.btn_fraction)
        val btnExponent = findViewById<Button>(R.id.btn_exponent)
        val btnRoot = findViewById<Button>(R.id.btn_root)
        //numbers
        val btn1 = findViewById<Button>(R.id.btn_1)
        val btn2 = findViewById<Button>(R.id.btn_2)
        val btn3 = findViewById<Button>(R.id.btn_3)
        val btn4 = findViewById<Button>(R.id.btn_4)
        val btn5 = findViewById<Button>(R.id.btn_5)
        val btn6 = findViewById<Button>(R.id.btn_6)
        val btn7 = findViewById<Button>(R.id.btn_7)
        val btn8 = findViewById<Button>(R.id.btn_8)
        val btn9 = findViewById<Button>(R.id.btn_9)
        val btn0 = findViewById<Button>(R.id.btn_0)
        //extra buttons for numbers
        val btnPlusMinus = findViewById<Button>(R.id.btn_plus_minus)
        val btnDot = findViewById<Button>(R.id.btn_dot)
        //texts
        val txtEntry = findViewById<TextView>(R.id.txt_entry)
        val txtHistory = findViewById<TextView>(R.id.txt_history)
        val txtError = findViewById<TextView>(R.id.txt_error)
        //listeners
        btn0.setOnClickListener {
            txtError.text = ""
            txtEntry.append("0")
        }
        btn1.setOnClickListener {
            txtError.text = ""
            txtEntry.append("1")
        }
        btn1.setOnClickListener {
            txtError.text = ""
            txtEntry.append("1")
        }
        btn2.setOnClickListener {
            txtError.text = ""
            txtEntry.append("2")
        }
        btn3.setOnClickListener {
            txtError.text = ""
            txtEntry.append("3")
        }
        btn4.setOnClickListener {
            txtError.text = ""
            txtEntry.append("4")
        }
        btn5.setOnClickListener {
            txtError.text = ""
            txtEntry.append("5")
        }
        btn6.setOnClickListener {
            txtError.text = ""
            txtEntry.append("6")
        }
        btn7.setOnClickListener {
            txtError.text = ""
            txtEntry.append("7")
        }
        btn8.setOnClickListener {
            txtError.text = ""
            txtEntry.append("8")
        }
        btn9.setOnClickListener {
            txtError.text = ""
            txtEntry.append("9")
        }
        btnDot.setOnClickListener {
            txtError.text = ""
            val entry = txtEntry.text.toString()
            if (op == 0 && !txtEntry.text.contains('.')) {
                txtEntry.append(".")
            } else if (op != 0) {
                //for second
                var entry1 = entry
                var second = entry1.replace(df.format(fn) + displayOperator(), "")
                if(!second.contains('.')){
                    txtEntry.append(".")
                }
            }
        }
        btnPlusMinus.setOnClickListener {
            txtError.text = ""
            //for first number
            val entry = txtEntry.text.toString()
            val entry1 = entry
            val entry2 = entry
            var second = entry1.replace(df.format(fn) + displayOperator(), "")
            var noSecond = entry2.replace(second, "")
            if(entry.isEmpty()){
                txtEntry.text = "-"
            }
            else if(op == 0 && entry[0] == '-'){
                txtEntry.text = txtEntry.text.substring(1)
            }
            else if(op == 0 && entry[0] != '-'){
                txtEntry.text = "-${txtEntry.text}"
            }
            else if(op != 0 && second.trim().isEmpty()){
                txtEntry.append("-")
            }
            else if(op != 0 && second[0] == '-'){
                txtEntry.text = noSecond + second[1]
            }
            else if(op != 0 && second[0] != '-'){
                txtEntry.text = noSecond + "-"+second
            }
        }
        btnPlus.setOnClickListener {
            txtError.text = ""
            operatorInput(1, txtEntry, txtHistory, txtError)
        }
        btnMinus.setOnClickListener {
            txtError.text = ""
            operatorInput(2, txtEntry, txtHistory, txtError)
        }
        btnTimes.setOnClickListener {
            txtError.text = ""
            operatorInput(3, txtEntry, txtHistory, txtError)
        }
        btnDivide.setOnClickListener {
            txtError.text = ""
            operatorInput(4, txtEntry, txtHistory, txtError)
        }
        btnEquals.setOnClickListener {
            txtError.text = ""
            handleEquals(txtEntry, txtHistory, txtError)
        }
        btnClearAll.setOnClickListener {
            txtEntry.text = ""
            txtHistory.text = ""
            txtError.text = ""
            op = 0
            fn = 0.0
            sn = 0.0
        }
        btnClearEntry.setOnClickListener {
            txtError.text = ""
            txtEntry.text = ""
            txtEntry.text = ""
            op = 0
            fn = 0.0
            sn = 0.0
        }
        btnBackspace.setOnClickListener {
            txtError.text = ""
            val entry = txtEntry.text.toString()
            val lastChar = entry[entry.length-1]
            if(lastChar == '+' || lastChar == '-' || lastChar == 'x' || lastChar == '÷'){
                op = 0
                txtEntry.text = txtEntry.text.toString().dropLast(1)
                fn = txtEntry.text.toString().toDouble()
            }
            else{
                txtEntry.text = txtEntry.text.toString().dropLast(1)
            }
        }
        btnPercent.setOnClickListener {
            txtError.text = ""
            handlePercentage(txtEntry)
        }
        btnFraction.setOnClickListener {
            txtError.text = ""
            handleFraction(txtEntry, txtHistory)
        }
        btnExponent.setOnClickListener {
            txtError.text = ""
            handleExponent(txtEntry, txtHistory)
        }
        btnRoot.setOnClickListener {
            txtError.text = ""
            handleRoot(txtEntry, txtHistory)
        }
    }
    private fun handleRoot(txtEntry: TextView, txtHistory: TextView){
        val entry = txtEntry.text.toString()
        if(op != 0) return
        fn = entry.toDouble()
        val totalRoot = Math.sqrt(fn)
        txtEntry.text = df.format(totalRoot).toString()
        txtHistory.text = "√${df.format(fn)} = ${df.format(totalRoot)}"
        fn = totalRoot
    }
    private fun handleExponent(txtEntry: TextView, txtHistory: TextView){
        val entry = txtEntry.text.toString()
        if(op != 0) return
        fn = entry.toDouble()
        val totalExponent = fn*fn
        txtEntry.text = df.format(totalExponent).toString()
        txtHistory.text = "${df.format(fn)}² = ${df.format(totalExponent)}"
        fn = totalExponent
    }
    private fun handleFraction(txtEntry: TextView, txtHistory: TextView){
        val entry = txtEntry.text.toString()
        if(op != 0) return
        fn = entry.toDouble()
        val totalFraction = 1/fn
        txtEntry.text = df.format(totalFraction).toString()
        txtHistory.text = "1/${df.format(fn)} = ${df.format(totalFraction)}"
        fn = totalFraction
    }
    private fun handlePercentage(txtEntry: TextView){
        var percentageValue = 0.0
        val entry = txtEntry.text.toString()
        val entry1 = entry
        val second =  entry1.replace(df.format(fn) + displayOperator(), "")
        if(entry.isEmpty()) return
        if(op == 0)return
        if(second.isNotEmpty() && second.toDouble() != 0.0){
            val percent = (second.toDouble()/100.0)
            percentageValue = fn*percent
        }
        txtEntry.text = fn.toString() + displayOperator() + percentageValue.toString()
    }
    private fun operatorInput(userInput:Int, txtEntry: TextView, txtHistory: TextView, txtError: TextView){
        val entry = txtEntry.text.toString()
        if(entry.isEmpty()) return
        if(op == 0){
            //set op first
            op = userInput
            fn = entry.toDouble()
            txtEntry.text = df.format(fn) + displayOperator()
        }
        else{
            //either change op or calculate
            val entry1 = entry
            val second =  entry1.replace(df.format(fn) + displayOperator(), "")
            if(second.trim().isEmpty()){
                op = userInput
                txtEntry.text = df.format(fn) + displayOperator()
            }
            else{
                sn = second.toDouble()
                calculate(txtHistory, txtError)
                op = userInput //for latest op
                txtEntry.text = "${df.format(fn)}${displayOperator()}"
            }
        }
    }
    private fun handleEquals(txtEntry: TextView, txtHistory: TextView, txtError: TextView){
        if(op == 0){
            txtError.text = "Please use an operator."
            return
        }

        val entry = txtEntry.text.toString()
        val entry1 = entry
        val second =  entry1.replace(df.format(fn) + displayOperator(), "")
        if(second.isEmpty()){
            txtError.text = "Please enter your second number."
            return
        }
        if(second.trim().isNotEmpty()){
            sn = second.toDouble()
            calculate(txtHistory, txtError)
            op = 0
            txtEntry.text = "${df.format(fn)}${displayOperator()}"
        }
    }
    private fun calculate(txtHistory: TextView, txtError: TextView){
        var total = 0.0
        txtError.text = ""
        when(op){
            1 -> {
                total = fn + sn
            }
            2 -> {
                total = fn - sn
            }
            3 -> {
                total = fn * sn
            }
            4 -> {
                if(sn == 0.0){
                    txtError.text = "Cannot divide a number by zero."
                    return
                }
                else{
                    total = fn / sn
                }
            }
            else -> {
                txtError.text = "Please choose an operator."
                return
            }
        }
        txtHistory.text = "${df.format(fn)}${displayOperator()}${df.format(sn)} = ${df.format(total)}"
        fn = total
        sn = 0.0
    }
    private fun displayOperator(): String{
        var flag = ""
        flag = when(op){
            1 -> "+"
            2 -> "-"
            3 -> "x"
            4 -> "÷"
            else -> ""
        }
        return flag
    }
}
