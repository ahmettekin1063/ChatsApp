package com.ahmettekin.chatsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnKayitOl.setOnClickListener {
            if (etMail.text.isNotEmpty() && etSifre.text.isNotEmpty() && etSifreTekrar.text.isNotEmpty()) {
                if (etSifre.text.toString() == etSifreTekrar.text.toString()){
                    progressBarRegister.visibility= View.VISIBLE
                    yeniUyeKayit(etMail.text.toString(),etSifre.text.toString())
                }else{
                    Toast.makeText(this,"Şifreler Aynı Değil",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Boş Alanları Doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun yeniUyeKayit(mail:String,sifre:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, sifre)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    progressBarRegister.visibility=View.INVISIBLE
                    Toast.makeText(this, "Üye kaydedildi", Toast.LENGTH_SHORT).show()
                    onayMailiGonder()
                    FirebaseAuth.getInstance().signOut()
                } else {
                    progressBarRegister.visibility=View.INVISIBLE
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onayMailiGonder() {
        FirebaseAuth.getInstance().currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) Toast.makeText(this, "Mail kutunuzu kontrol edin", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Mail gönderilemedi: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}