package com.uma.trailsmartapp.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.uma.trailsmartapp.R
import com.uma.trailsmartapp.models.Batsman
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.score_view_layout.*


/**
 * Created by Umapathi on 08/03/19.
 * Copyright Indyzen Inc, @2019
 */
class HomeFragment : Fragment(), View.OnClickListener {

    lateinit var fDatabase: FirebaseDatabase
    lateinit var dReference: DatabaseReference
    lateinit var scoreList: ArrayList<Batsman>
    lateinit var battingAdapter: BattingAdapter
    var registeredMobileNumber = ""
    var strikerScore = 0
    var nonStrikerScore = 0
    var totalScore : Int= 0
    var foursCount = 0
    var sixesCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onResume() {
        super.onResume()
        fDatabase = FirebaseDatabase.getInstance()

        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnFive.setOnClickListener(this)
        btnSix.setOnClickListener(this)
        btnZero.setOnClickListener(this)
        btnWide.setOnClickListener(this)
        btnNb.setOnClickListener(this)
        btnLb.setOnClickListener(this)
        btnByes.setOnClickListener(this)
        btnOut.setOnClickListener(this)

        txt1TeamName.text = "Bala123456789001"

        initilizeRecyclerView()

        retriveDataFromFirebase()
    }

    /**
     * Initilizing all variables...
     */
    private fun initilizeRecyclerView() {
        scoreList = ArrayList<Batsman>()
//        scoreList.add(Batsman("Uma12345678901234", "no", "102", "30", "20", "3", "310.20", false))
        scoreList.add(Batsman("Uma", "no", "102", "34", "20", "3", "310.20", false))

        battingAdapter = BattingAdapter(scoreList)
        val recyclerView: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recBattingTeamList.layoutManager = recyclerView
        recBattingTeamList.itemAnimator = DefaultItemAnimator()
        recBattingTeamList.adapter = battingAdapter
    }

    /**
     *  Retrive data from firebase using observables....
     */
    @SuppressLint("CheckResult")
    fun retriveDataFromFirebase() {

        val preference = context?.getSharedPreferences("phone number", Context.MODE_PRIVATE)
        registeredMobileNumber = preference?.getString("phone number", "")!!


        val fDatabase = FirebaseDatabase.getInstance()

        val query: DatabaseReference = registeredMobileNumber.let {
            fDatabase.reference.child(it).child("Innings1")

        }

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map: Map<*, *> = dataSnapshot.value as Map<*, *>

                txt1TeamScore.text = map["Total score"].toString()

                var batsman = map["Batting"]

                Log.e("batsamn dT","$batsman")
//                scoreList.add(
//                    Batsman(
//                        batsman.name, batsman.status,
//                        batsman.runs, batsman.balls, batsman.fours, batsman.sixes, batsman.strieRate, batsman.isStriking
//                    )
//                )
                battingAdapter.notifyDataSetChanged()
            }

        })

//        RxFirebaseDatabase.observeSingleValueEvent<Batsman>(query, Batsman::class.java)
//            .subscribe({
//                scoreList.add(
//                    Batsman(
//                        it.name,
//                        it.status,
//                        it.runs,
//                        it.balls,
//                        it.fours,
//                        it.sixes,
//                        it.strieRate,
//                        it.isStriking
//                    )
//                )
//                battingAdapter.notifyDataSetChanged()
//            },
//                { throwable -> Log.e("Firebase Error", "$throwable") })
    }

    override fun onClick(v: View?) {
        when (v) {
            btnOne -> {
                setBatsmanData(1)
                retriveDataFromFirebase()

            }
            btnTwo -> {
                setBatsmanData(2)
                retriveDataFromFirebase ()
            }
            btnThree -> {
                setBatsmanData(3)
                retriveDataFromFirebase ()
            }
            btnFour -> {
                foursCount += 1
                setBatsmanData(4)
                retriveDataFromFirebase ()
            }
            btnFive -> {
                setBatsmanData(5)
                retriveDataFromFirebase ()
            }
            btnSix -> {
                sixesCount += 1
                setBatsmanData(6)
                retriveDataFromFirebase ()
            }
            btnZero -> {
                setBatsmanData(0)
                retriveDataFromFirebase ()
            }
            btnWide -> {
                setBatsmanData(1)
                retriveDataFromFirebase ()
            }
            btnNb -> {
                setBatsmanData(1)
                retriveDataFromFirebase ()
            }
            btnLb -> {
                setBatsmanData(1)
                retriveDataFromFirebase ()
            }
            btnByes -> {
                setBatsmanData(1)
                retriveDataFromFirebase ()
            }
            btnOut -> {
                Toast.makeText(context, "OUT", Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }
    }

    fun setBatsmanData(data: Int) {
        val query: DatabaseReference = registeredMobileNumber.let {
            fDatabase.reference.child(it).child("Innings1").child("Batting").child("Uma")
        }
        when (data) {
            0 -> {

                setTeamTotal(totalScore + 0)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )
            }
            1 -> {
                setTeamTotal(totalScore + 1)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            2 -> {
                setTeamTotal(totalScore + 2)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            3 -> {
                setTeamTotal(totalScore + 3)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            4 -> {
                setTeamTotal(totalScore + 4)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            5 -> {
                setTeamTotal(totalScore + 5)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            6 -> {
                setTeamTotal(totalScore + 6)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            7 -> {
                setTeamTotal(totalScore + 1)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            8 -> {
                setTeamTotal(totalScore + 1)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            9 -> {
                setTeamTotal(totalScore + 1)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            10 -> {
                setTeamTotal(totalScore + 1)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )

            }
            11 -> {
                setTeamTotal(totalScore + 1)
                query.setValue(
                    Batsman(
                        "Uma",
                        "no",
                        "$data",
                        "$data",
                        foursCount.toString(),
                        sixesCount.toString(),
                        "123",
                        false
                    )
                )
            }
            else -> {
            }
        }
    }

    fun setTeamTotal(data: Int) {
        val query: DatabaseReference = registeredMobileNumber.let {
            fDatabase.reference.child(it).child("Innings1").child("Total score")
        }

        query.setValue(data)

    }


//    fun setScore()
}
