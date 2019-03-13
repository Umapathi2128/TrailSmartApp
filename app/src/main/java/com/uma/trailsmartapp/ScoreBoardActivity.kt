package com.uma.trailsmartapp

import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uma.trailsmartapp.fragments.BattingDetailsFragment
import com.uma.trailsmartapp.fragments.BowlingDetailsFragment
import com.uma.trailsmartapp.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_score_board.*

/**
 * Created by Umapathi on 05/03/19.
 * Copyright Indyzen Inc, @2019
 */
class ScoreBoardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)

        loadFragments(HomeFragment())
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }


    private fun loadFragments(fragment: Fragment) {
        val fmTransition = supportFragmentManager.beginTransaction()
        fmTransition.replace(R.id.framelayout, fragment)
//        fmTransition.addToBackStack(null)
        fmTransition.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome -> {
                loadFragments(HomeFragment())
            }
            R.id.menuBatting -> {
                loadFragments(BattingDetailsFragment())
            }
            R.id.menuBowling -> {
                loadFragments(BowlingDetailsFragment())
            }
            else -> {
            }
        }
        return true
    }

}
