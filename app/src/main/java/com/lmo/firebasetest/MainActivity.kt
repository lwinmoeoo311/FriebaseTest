package com.lmo.firebasetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var base: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        base = FirebaseAuth(FirebaseApp.getInstance())

        out_btn.setOnClickListener { logout() }
    }

    private fun logout() {
        base.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        onStop()
    }
}
