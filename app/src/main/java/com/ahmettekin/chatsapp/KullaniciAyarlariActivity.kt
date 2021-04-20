package com.ahmettekin.chatsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_kullanici_ayarlari2.*

class KullaniciAyarlariActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_ayarlari2)

        etDetayName.setText(FirebaseAuth.getInstance().currentUser?.displayName)
        etDetayMail.setText(FirebaseAuth.getInstance().currentUser?.email)

        btnSifreGonder.setOnClickListener {
                FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().currentUser.email)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Şifre sıfırlama maili gönderildi.", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"Hata oluştu: ${it.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }
}