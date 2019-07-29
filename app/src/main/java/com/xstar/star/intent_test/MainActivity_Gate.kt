package com.xstar.star.intent_test

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View

class MainActivity_Gate : AppCompatActivity() {
    private val dtimer = Handler()
    internal var timer = 0
    internal var Tostart = Intent()
    private val runnable = Runnable {
        timer_hiter()
        timer += 1000
        timer_start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__gate)
        Tostart.setClass(this, MainActivity::class.java)
        timer_start()
    }

    fun mainStart(view: View) {
        Toast.makeText(this, "正在进入主界面", Toast.LENGTH_SHORT).show()
        startActivity(Tostart)
        finish()
    }

    private fun timer_start() {
        dtimer.postDelayed(runnable, 1000)
    }

    private fun timer_hiter() {
        Toast.makeText(this, "程序启动了一个线程，将在" + Math.abs(timer / 1000 - 3) + "s内自动跳转至主界面\n也可触摸任意位置以跳转至主界面w", Toast.LENGTH_SHORT).show()
        if (timer == 3000) {
            startActivity(Tostart)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dtimer.removeCallbacks(runnable)
    }

}
