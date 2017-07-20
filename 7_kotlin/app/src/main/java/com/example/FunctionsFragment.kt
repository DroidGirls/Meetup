package com.example

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

class FunctionsFragment : Fragment() {

    private fun EditText.getTextAsInt(): Int = text.toString().toInt()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_functions, container, false).apply {
                val inputEditText = findViewById(R.id.input_edit_text) as EditText
                val outputEditText = findViewById(R.id.output_edit_text) as EditText

                findViewById(R.id.square_button).setOnClickListener {
                    val got = square(inputEditText.getTextAsInt())
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.re_lu_button).setOnClickListener {
                    val got = reLu(inputEditText.getTextAsInt())
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.is_even_button).setOnClickListener {
                    val got = isEven(inputEditText.getTextAsInt())
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.factorial_button).setOnClickListener {
                    val got = factorial(inputEditText.getTextAsInt())
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.match_odd_button).setOnClickListener {
                    val got = match(inputEditText.getTextAsInt()) {
                        TODO()
                    }
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.match_negative_button).setOnClickListener {
                    val got = match(inputEditText.getTextAsInt()) {
                        TODO()
                    }
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.double_id_button).setOnClickListener {
                    val f = double { TODO() }
                    val got = f(inputEditText.getTextAsInt())
                    outputEditText.setText(got.toString())
                }
                findViewById(R.id.double_square_button).setOnClickListener {
                    val f = double { TODO() }
                    val got = f(inputEditText.getTextAsInt())
                    outputEditText.setText(got.toString())
                }
            }

    fun square(n: Int): Int {
        TODO()
    }

    fun reLu(n: Int): Int {
        TODO()
    }

    fun isEven(n: Int): Boolean {
        TODO()
    }

    fun factorial(n: Int): Int {
        TODO()
    }

    fun match(n: Int, f: (Int) -> Boolean): Boolean {
        TODO()
    }

    fun double(f: (Int) -> Int): (Int) -> Int {
        TODO()
    }
}