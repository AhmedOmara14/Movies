package com.illa.task.presentation.splach

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.illa.task.R
import com.illa.task.presentation.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalch)
    }

    override fun onResume() {
        super.onResume()
        val run: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    startActivity(Intent(this@SplashActivity,HomeActivity::class.java))
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        run.start()
    }
}