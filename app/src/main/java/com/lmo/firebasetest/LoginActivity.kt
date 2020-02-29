package com.lmo.firebasetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity: AppCompatActivity() {

    private lateinit var base : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        base = FirebaseAuth.getInstance()

//        base.signInWithEmailAndPassword(mail_tv.toString(), password_tv.toString())
//            .addOnCompleteListener(this) {
//                if (it.isSuccessful) {
//                    base.currentUser
//                    chgActivity()
//                }
//            }

        login_btn.setOnClickListener { login() }

        logout_btn.setOnClickListener { signUp() }
    }

    override fun onStart() {
        super.onStart()

        base.currentUser

        onStop()
    }

    private fun login() {
        if (mail_tv.text.isNullOrBlank() || password_tv.text.isNullOrBlank()) {
            Toast.makeText(this, "* marked fields are required to fill", Toast.LENGTH_SHORT).show()
        }
        else {
            base.signInWithEmailAndPassword(mail_tv.text.toString(), password_tv.text.toString())
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        base.currentUser
                        Toast.makeText(this, "${mail_tv.text}", Toast.LENGTH_SHORT).show()
                        chgActivity()
                    }
                }
        }
    }

    private fun signUp() {
        if (mail_tv.text.isNullOrBlank() || password_tv.text.isNullOrBlank()) {
            Toast.makeText(this, "* marked fields are required to fill", Toast.LENGTH_SHORT).show()
        }
        else {
            base.createUserWithEmailAndPassword(mail_tv.text.toString(), password_tv.text.toString())
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        base.currentUser
                        chgActivity()
                    } else {
                        Log.e(TAG, "${it.exception?.message}")
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "${it.message}")
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun chgActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("mail", "${mail_tv.text}")
        startActivity(intent)
    }

    companion object {
        private var TAG = LoginActivity::class.java.simpleName
    }
}