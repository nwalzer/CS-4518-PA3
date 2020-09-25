package com.example.basketballcounter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val TAG = "BasketballCounter"

class DisplayActivity : AppCompatActivity() {
    private var aPTS: Int = 0
    private var bPTS: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        aPTS = intent.getIntExtra("aPTS", 0)
        bPTS = intent.getIntExtra("bPTS", 0)
        Log.d(TAG, "DisplayActivity received A - $aPTS and B - $bPTS from BasketballCounter")

        val data = Intent().apply {
            putExtra("aPTS", aPTS)
            putExtra("bPTS", bPTS)
        }
        setResult(Activity.RESULT_OK, data)
        Log.d(TAG, "DisplayActivity set results A - $aPTS and B - $bPTS")
    }

    companion object {
        fun newIntent(packageContext: Context, aPoints: Int, bPoints: Int): Intent {
            return Intent(packageContext, DisplayActivity::class.java).apply {
                putExtra("aPTS", aPoints)
                putExtra("bPTS", bPoints)
            }
        }
    }
}