package com.ahmettekin.chatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initMyAuthStateListener()
        configureListener()
    }

    private fun configureListener() {
        btnLogin.setOnClickListener {

            if (etLoginMail.text.isNotEmpty() && etLoginPassword.text.isNotEmpty()) {
                progressBarLogin.visibility = View.VISIBLE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(etLoginMail.text.toString(), etLoginPassword.text.toString())
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            progressBarLogin.visibility = View.INVISIBLE
                            task.result?.user?.let {

                                if (!it.isEmailVerified) {
                                    FirebaseAuth.getInstance().signOut()
                                }
                            }
                        } else {
                            progressBarLogin.visibility = View.INVISIBLE
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else {
                Toast.makeText(this, "Boş Alanları Doldurunuz", Toast.LENGTH_SHORT).show()
            }
        }
        tvSendEmailAgain.setOnClickListener {
            SendEmailAgainFragment().show(supportFragmentManager, "gosterdialog")
        }
        tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        tvSifreTekrarYolla.setOnClickListener {
            SifremiUnuttumDialogFragment().show(supportFragmentManager, "gosterdialogsifre")
        }
    }

    private fun initMyAuthStateListener() {
        mAuthStateListener = FirebaseAuth.AuthStateListener {
            val currentUser = it.currentUser
            currentUser?.let {
                if (currentUser.isEmailVerified) {
                    Toast.makeText(this, "Mail onaylanmış giriş başarılı.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Mail Adresinizi Onaylayınız", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener)
    }
}