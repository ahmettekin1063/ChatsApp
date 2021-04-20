package com.ahmettekin.chatsapp

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

internal fun yeniUyeKayit3(mail: String, sifre: String, context: Context) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, sifre)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Üye kaydedildi", Toast.LENGTH_SHORT).show()
                //onayMailiGonder(context)
                FirebaseAuth.getInstance().signOut()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
}

internal fun sendVerifyMail(mail: String, sifre: String, context: Context) {
    val credential = EmailAuthProvider.getCredential(mail, sifre)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                //onayMailiGonder(context)
                FirebaseAuth.getInstance().signOut()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
}

internal fun loginOperations(mail: String, sifre: String, activity: Context?, progressBarLogin: ProgressBar) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(mail, sifre)
        .addOnCompleteListener { task ->

            if (task.isSuccessful) {
                progressBarLogin.visibility= View.INVISIBLE
                task.result?.user?.let {

                    if (it.isEmailVerified) {

                    } else {

                    }
                }
            } else {
                Toast.makeText(activity, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
}

private fun onayMailiGonder2(context: Context) {
    FirebaseAuth.getInstance().currentUser?.let {
        it.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) Toast.makeText(context, "Mail kutunuzu kontrol edin", Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, "Mail gönderilemedi: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}