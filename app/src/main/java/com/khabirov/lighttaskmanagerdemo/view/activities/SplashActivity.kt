package com.niww.lighttaskmanager.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAuth()
    }

    private fun checkAuth() {
        if (auth.currentUser == null) {
            val authorizationIntent = Intent(this, AuthorizationActivity::class.java)
            startActivity(authorizationIntent)
            finish()
        } else {
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}