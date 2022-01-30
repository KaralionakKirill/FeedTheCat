package ru.bsuir.feedthecat

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        feedButton.setOnClickListener {
            val scoreValue = score.text.toString().toInt()
            score.text = (scoreValue + 1).toString()
        }
    }
}