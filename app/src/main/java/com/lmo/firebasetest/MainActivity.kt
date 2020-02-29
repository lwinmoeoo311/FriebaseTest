package com.lmo.firebasetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.opencensus.tags.Tag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var base: FirebaseAuth

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        base = FirebaseAuth(FirebaseApp.getInstance())

        save_btn.setOnClickListener { submittinDb() }

        out_btn.setOnClickListener { logout() }
    }

    private fun logout() {
        base.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        onStop()
    }

    private fun submittinDb() {

        val user = hashMapOf("name" to "${name_tv.text}",
            "age" to "${age_tv.text}",
            "bio" to "${bio_tv.text}",
            "gmail" to "${base.currentUser?.email.toString()}")

        db.collection("users").add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Success", "Added with ID")
            }
            .addOnFailureListener { exception ->  Log.w("Error", "Error adding document", exception)}

        info_tv.text = "${name_tv.text.toString() + '\n' + age_tv.text.toString() + "\n" + bio_tv.text.toString()}"

        name_tv.visibility = View.GONE
        age_tv.visibility = View.GONE
        bio_tv.visibility = View.GONE

        info_tv.visibility = View.VISIBLE
    }
}
