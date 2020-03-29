package com.example.fuela

import android.content.Intent
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
    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null
    var secondaryHandler: Handler? = Handler()
    var primaryProgressStatus = 0
    var secondaryProgressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler(Handler.Callback {
            if (isStarted) {
                progressStatus++
            }
            progressBar.progress = progressStatus
            //textViewHorizontalProgress.text = "${progressStatus}/${progressBarHorizontal.max}"
            handler?.sendEmptyMessageDelayed(0, 100)

            true


        })
        startProgress()

    }

    fun startProgress(){
        primaryProgressStatus = 0
        secondaryProgressStatus = 0
        Thread(Runnable {
            while (primaryProgressStatus < 100) {
                primaryProgressStatus += 1

                try {

                    Thread.sleep(20)

                    if(primaryProgressStatus==100){



                        val intent = Intent(this, Dashboard::class.java)
                        startActivity(intent)
                        finish()

                    }



                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
progressBar.progress=primaryProgressStatus

                }


        }).start()
    }
}
