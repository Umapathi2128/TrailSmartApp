package com.uma.trailsmartapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


/**
 * Created by Umapathi on 05/03/19.
 * Copyright Indyzen Inc, @2019
 */

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    var mVerificationCode: String? = null
    var pnNo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser!=null) startActivity(Intent(this,MatchsListActivity::class.java))


        btnSendCode.setOnClickListener {
            if (etxtMobile.text.trim().toString().length == 10) {
//                if (mAuth.currentUser == null) {
                createNewUser()
                pnNo = etxtMobile.text.trim().toString()
//                }
//                else {
//                    startActivity(Intent(this,SaveDataActivity::class.java))
//                    finish()
//                    Toast.makeText(this@MainActivity, " verification already done", Toast.LENGTH_LONG).show()
//                }
            }else Toast.makeText(this@MainActivity, " verification success", Toast.LENGTH_LONG).show()
        }

        btnValidate.setOnClickListener {

            if (etxtOtp.text.trim().toString() != "" && etxtOtp.text.trim().toString().isNotEmpty())
                verifyVerificationCode(etxtOtp.text.trim().toString())
            else Toast.makeText(this@MainActivity, "Enter OTP please...", Toast.LENGTH_LONG).show()
        }
    }

    private fun createNewUser() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91" + etxtMobile.text.trim().toString(),
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
        )
    }

    private val mCallbacks = object : OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential?) {

            val code = p0?.smsCode
            if (code != null) {
                etxtOtp.setText(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException?) {
            Toast.makeText(this@MainActivity, e?.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(p0, p1)
            mVerificationCode = p0
        }
    }

    private fun verifyVerificationCode(code: String) {
        val phoneAuthCredential = mVerificationCode?.let { PhoneAuthProvider.getCredential(it, code) }
        phoneAuthCredential?.let { signInWithPhoneAuthCredential(it) }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val preference=this.getSharedPreferences("phone number", Context.MODE_PRIVATE)

                preference.edit().putString("phone number",pnNo).apply()

                startActivity(Intent(this, SaveDataActivity::class.java))
                finish()
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            } else
                Toast.makeText(this, "failure", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
