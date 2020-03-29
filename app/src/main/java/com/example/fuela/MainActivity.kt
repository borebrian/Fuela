package com.example.fuela

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressbar1: ProgressBar
        val progressbar2: ProgressBar
        val progressbar3: ProgressBar
        val progressbar4: ProgressBar
        val progressbar5: ProgressBar

        var intValue = 0
        val handler = Handler()

        progressbar1 = findViewById(R.id.progressBar) as ProgressBar
        progressbar1.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)

        fun run() {
            // TODO Auto-generated method stub
            while (intValue < 100)
            {
                intValue++
                handler.post(object:Runnable {
                    public override fun run() {
                        progressbar1.setProgress(intValue)

                    }
                })
                try
                {
                    Thread.sleep(300)
                }
                catch (e:InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
