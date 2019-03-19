package net.yanzm.daggersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val decorator = SharpSurroundDecorator()

        val text = "hello"

        textView.text = decorator.decorate(text)
    }
}
