package com.uma.trailsmartapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.uma.trailsmartapp.models.BattingDataModel
import com.uma.trailsmartapp.models.BowlingDataModel
import com.uma.trailsmartapp.models.MatchDataModel
import kotlinx.android.synthetic.main.activity_save_data.*

/**
 * Created by Umapathi on 05/03/19.
 * Copyright Indyzen Inc, @2019
 */
class SaveDataActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var fDatabase: FirebaseDatabase
    lateinit var dReference: DatabaseReference
    lateinit var mAuth: FirebaseAuth

    lateinit var umpire: String
    lateinit var venue: String
    lateinit var teamA: String
    lateinit var teamB: String
    lateinit var tossTeam: String
    lateinit var tossDecision: String
    lateinit var overs: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_data)

        mAuth = FirebaseAuth.getInstance()




        btnSaveData.setOnClickListener(this)

//        btnViewData.setOnClickListener {
//            startActivity(Intent(this, MatchsListActivity::class.java))
//        }
    }


    override fun onClick(v: View?) {
        when (v) {
            btnSaveData -> {
                saveDataButtonClick()
            }
//            btnViewData -> {
//            }
            else -> {
            }
        }
    }


    /**
     * This method for btnSaveData click....
     */
    private fun saveDataButtonClick() {

        umpire = etxtUmpire.text.toString()
        venue = etxtMatchPlace.text.toString()
        tossTeam = etxtTossTeam.text.toString()
        tossDecision = etxtTossDecision.text.toString()
        teamA = etxtTeamA.text.toString()
        teamB = etxtTeamB.text.toString()
        overs = etxtOvers.text.toString()

        if (umpire.isNotEmpty() && teamA.isNotEmpty() && teamB.isNotEmpty() &&
            venue.isNotEmpty() && tossTeam.isNotEmpty() && tossDecision.isNotEmpty()
        ) {
            if (tossTeam == teamA || tossTeam == teamB) {

                if(tossDecision == "Batting" || tossDecision == "Bowling"){
                    saveDataToFirebase()
                    startActivity(Intent(this,MatchsListActivity::class.java))
                }else etxtTossDecision.error="Type Batting or Bowling"

            } else {
                etxtTossTeam.error="Enter valid team name"
//                Toast.makeText(this, "Toss winning team not matched with our teams..", Toast.LENGTH_LONG).show()
            }

        } else Toast.makeText(this, "Fill all fields first", Toast.LENGTH_LONG).show()
    }


    /**
     * This method for write data into firebase...
     */
    private fun saveDataToFirebase() {

        val preference = this.getSharedPreferences("phone number", Context.MODE_PRIVATE)
        val phno = preference.getString("phone number", "")

        fDatabase = FirebaseDatabase.getInstance()
//        dReference = fDatabase.getReference(phno)
//        dReference.setValue(message)

        val battingDataModel = BattingDataModel(
            etxtTeamA.text.trim().toString(),
            etxtTeamB.text.trim().toString()
        )
        val bowlingDataModel = BowlingDataModel(
//            Bowler("Uma", "4", "8", "3", "2.0")
            etxtTeamA.text.trim().toString(),
            etxtTeamB.text.trim().toString()
        )


        val matchDataModel = MatchDataModel(umpire, venue, teamA, teamB,overs,tossTeam, tossDecision)

        dReference = fDatabase.getReference(phno)
        dReference.setValue(matchDataModel)
//        dReference.setValue("Uma")


        when {
            tossTeam == teamA && tossDecision == "Batting" -> {
                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Batting").child(teamA)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Bowling").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Batting").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Bowling").child(teamA)
                dReference.setValue("Nothing")

            }
            tossTeam == teamA && tossDecision == "Bowling" -> {
                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Batting").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Bowling").child(teamA)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Batting").child(teamA)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Bowling").child(teamB)
                dReference.setValue("Nothing")
            }
            tossTeam == teamB && tossDecision == "Bowling" -> {
                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Batting").child(teamA)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Bowling").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Batting").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Bowling").child(teamA)
                dReference.setValue("Nothing")

            }
            tossTeam == teamB && tossDecision == "Batting" -> {
                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Batting").child(teamA)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings2").child("Bowling").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Batting").child(teamB)
                dReference.setValue("Nothing")

                dReference =
                    fDatabase.getReference(phno).child("Innings1").child("Bowling").child(teamA)
                dReference.setValue("Nothing")

            }
        }

    }
}