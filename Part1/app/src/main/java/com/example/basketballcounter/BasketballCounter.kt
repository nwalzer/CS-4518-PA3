package com.example.basketballcounter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "BasketballCounter"
private const val DIS_ACT = 0

class BasketballCounter : AppCompatActivity() {
    private val btViewModel: BasketballTeamViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballTeamViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "BasketballCounter instance created")

        updateBothTeams()

        findViewById<Button>(R.id.three_point_a)?.setOnClickListener {
            updatePoints( true, 3)
        }

        findViewById<Button>(R.id.three_point_b)?.setOnClickListener {
            updatePoints( false, 3)
        }

        findViewById<Button>(R.id.two_point_a)?.setOnClickListener {
            updatePoints( true, 2)
        }

        findViewById<Button>(R.id.two_point_b)?.setOnClickListener {
            updatePoints( false, 2)
        }

        findViewById<Button>(R.id.free_throw_a)?.setOnClickListener {
            updatePoints( true, 1)
        }

        findViewById<Button>(R.id.free_throw_b)?.setOnClickListener {
            updatePoints( false, 1)
        }

        findViewById<Button>(R.id.reset)?.setOnClickListener {
            resetPoints()
        }

        findViewById<Button>(R.id.next_act)?.setOnClickListener {
            nextActivity()
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

    private fun nextActivity(){
        val intent = DisplayActivity.newIntent(this@BasketballCounter, btViewModel.getPoints(true), btViewModel.getPoints(false))
        startActivityForResult(intent, DIS_ACT)
    }

    //increments the given team's points by the supplied amount, updates UI
    private fun updatePoints(isA: Boolean, points: Int){
        btViewModel.updatePoints( isA, points)
        updateTeamStanding(isA)
    }

    //sets both team's points to 0, updates UI
    private fun resetPoints(){
        btViewModel.resetPoints()
        updateBothTeams()
    }

    //Changes one team's displayed points
    private fun updateTeamStanding(isA: Boolean){
        if(isA){
            val textView = findViewById<TextView>(R.id.point_label_a)
            textView.text = btViewModel.getPoints(isA).toString()
        } else {
            val textView = findViewById<TextView>(R.id.point_label_b)
            textView.text = btViewModel.getPoints(isA).toString()
        }
    }

    //Changes both team's displayed points
    private fun updateBothTeams(){
        updateTeamStanding(true)
        updateTeamStanding(false)
        findViewById<TextView>(R.id.game_label)?.text = "Game #${(btViewModel.getIndex() / 2) + 1}"
    }
}