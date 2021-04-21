package com.ahmettekin.chatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_kullanici_ayarlari2.*

class KullaniciAyarlariActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici_ayarlari2)
        initViews()
        configureListener()
    }

    private fun initViews() {
        etDetayName.setText(FirebaseAuth.getInstance().currentUser?.displayName)
    }

    private fun configureListener() {
        btnSifreGonder.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.email?.let { email->
                FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) { Toast.makeText(this, "Şifre sıfırlama maili gönderildi.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Hata oluştu: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        btnYeniIsimKaydet.setOnClickListener {

            if(etDetayName.text.isNotEmpty()){

                if (etDetayName.text.toString() != FirebaseAuth.getInstance().currentUser?.displayName){
                    val updateUserName = UserProfileChangeRequest.Builder()
                        .setDisplayName(etDetayName.text.toString())
                        .build()
                    FirebaseAuth.getInstance().currentUser?.updateProfile(updateUserName)
                        ?.addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(this,"Değişiklikler yapıldı.",Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }else{
                Toast.makeText(this,"Boş alanları doldurunuz.",Toast.LENGTH_SHORT).show()
            }
        }
        btnSifreveyaMailGuncelle.setOnClickListener {

            if(etDetaySifre.text.toString().isNotEmpty()){
                val credential=EmailAuthProvider.getCredential(FirebaseAuth.getInstance().currentUser?.email!!,etDetaySifre.text.toString())
                FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)
                    ?.addOnCompleteListener {
                        if(it.isSuccessful){
                            guncelleLayout.visibility = View.VISIBLE
                        }else {
                            Toast.makeText(this,"Şu anki şifrenizi yanlış girdiniz.",Toast.LENGTH_SHORT).show()
                            guncelleLayout.visibility = View.INVISIBLE
                        }
                    }
            }else{
                Toast.makeText(this,"Güncellemeler için geçerli şifrenizi yazmalısınız.",Toast.LENGTH_SHORT).show()
            }
        }

        btnMailGuncelle.setOnClickListener {
            if(etYaniEmail.text.isNotEmpty()){
                FirebaseAuth.getInstance().currentUser?.updateEmail(etYaniEmail.text.toString())
                    ?.addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Mail adresiniz değiştirildi tekrar giriş yapın",Toast.LENGTH_SHORT).show()
                            FirebaseAuth.getInstance().signOut()
                            goToLoginActivity()
                        }else{
                            Toast.makeText(this,"Hata: ${it.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"Bir E-mail giriniz.",Toast.LENGTH_SHORT).show()
            }
        }

        btnSifreGuncelle.setOnClickListener {
            if(etYeniSifre2.text.isNotEmpty()){
                FirebaseAuth.getInstance().currentUser?.updatePassword(etYeniSifre2.text.toString())
                    ?.addOnCompleteListener {
                        Toast.makeText(this,"Şifreniz değiştirildi tekrar giriş yapın",Toast.LENGTH_SHORT).show()
                        FirebaseAuth.getInstance().signOut()
                        goToLoginActivity()
                    }
            }else{
                Toast.makeText(this,"Yeni Şifreyi Giriniz",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToLoginActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

}