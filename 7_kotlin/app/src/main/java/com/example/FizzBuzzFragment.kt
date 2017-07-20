package com.example

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FizzBuzzFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_fizz_buzz, container, false).apply {
                val fizzBuzzTextView = findViewById(R.id.fizz_buzz_text_view) as TextView
                fizzBuzzTextView.text = fizzBuzz()
            }

    private fun fizzBuzz(): String {
        val builder = StringBuilder()
        builder.appendln(1)
        builder.appendln(2)
        builder.appendln("Fizz")
        builder.appendln(4)
        builder.appendln("Buzz")
        return builder.toString()
    }
}