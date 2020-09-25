package com.example.basketballcounter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "BasketballCounter"
private const val DIS_ACT = 0

class BasketballCounter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "BasketballCounter instance created")

        var currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_a)
        if (currentFragment == null) {
            val fragment =
                BasketballCounterFragment()
            fragment.setIsA(true)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_a, fragment)
                .commit()
        }

        currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_b)
        if (currentFragment == null) {
            val fragment =
                BasketballCounterFragment()
            fragment.setIsA(false)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_b, fragment)
                .commit()
        }

        findViewById<Button>(R.id.reset)?.setOnClickListener {
            resetPoints()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            Log.d(TAG, "BasketballCounter received non-OK result from DisplayActivity")
            return
        }

        if (requestCode == DIS_ACT) {
            val aPTS = data?.getIntExtra("aPTS", 0)
            val bPTS = data?.getIntExtra("bPTS", 0)
            Log.d(TAG, "BasketballCounter received A - $aPTS and B - $bPTS from DisplayActivity")
        }
    }

    //sets both team's points to 0, updates UI
    private fun resetPoints(){
        var currentFragment: BasketballCounterFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_a) as BasketballCounterFragment
        currentFragment.reset()

        currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_b) as BasketballCounterFragment
        currentFragment.reset()
    }
}