package com.uma.trailsmartapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.uma.trailsmartapp.models.Batsman
import com.uma.trailsmartapp.models.Bowler
import com.uma.trailsmartapp.models.MatchDataModel
import kotlinx.android.synthetic.main.activity_matchs_list.*


/**
 * Created by Umapathi on 05/03/19.
 * Copyright Indyzen Inc, @2019
 */
class MatchsListActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var phno: String
    lateinit var matchList: ArrayList<MatchDataModel>
    lateinit var batsmanData: ArrayList<Batsman>
    lateinit var bowlerData: ArrayList<Bowler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matchs_list)

        matchList = ArrayList()

        val preference = this.getSharedPreferences("phone number", Context.MODE_PRIVATE)
        phno = preference.getString("phone number", "")

        getAllDataFromFirebase()

        crdScoreCardView.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v)
        {
           crdScoreCardView->{
            startActivity(Intent(this,ScoreBoardActivity::class.java))
           }
            else->{}
        }
    }


    private fun getAllDataFromFirebase() {
//        val preference = this.getSharedPreferences("phone number", Context.MODE_PRIVATE)
//        val phno = preference.getString("phone number", "")

        val fDatabase = FirebaseDatabase.getInstance()
        var databaseReference = fDatabase.getReference(phno).child("Innings1").child("Total score")
        databaseReference.setValue("122")
        databaseReference = fDatabase.getReference(phno).child("Innings1").child("Total overs")
        databaseReference.setValue("1")


        val databaseReference2 = fDatabase.getReference(phno)
        databaseReference2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MatchsListActivity, "Error$p0", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map: Map<*, *> = dataSnapshot.value as Map<*, *>


//                for (ds in dataSnapshot.children) {
                matchList.add(
                    MatchDataModel(
                        map["umpire"].toString(),
                        map["venue"].toString(),
                        map["teamA"].toString(),
                        map["teamB"].toString(),
                        map["overs"].toString(),
                        map["toss"].toString(),
                        map["decision"].toString()
                    )
                )

                val teams = matchList[0].teamA + " vs " + matchList[0].teamB
                val toss = "Toss : "+matchList[0].toss + " opt to " + matchList[0].decision
                val venue="Venue : "+matchList[0].venue
                val umpires="Umpires : "+matchList[0].umpire

                txtTeams.text = teams
                txtTossDetails.text = toss
                txtVenue.text = venue
                txtUmpires.text = umpires


                val batsman: Map<String, Any> = map["Innings1"] as Map<String, Any>

//                batsmanData.add(batsman[""])
//                    val nodes = ds.getValue(String::class.java)!!
                Log.e("Batsman DataModel", "Data : ${batsman["Total score"]}")

                Log.e("Batsman DataModel", "Nodes : $batsman")
//                }
            }
        })
    }
}
