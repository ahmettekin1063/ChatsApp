package com.ahmettekin.chatsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var myAuthStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAuthStateListener()
        setUserInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ana_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cikis_yap -> {
                signOutFromMyAccount()
                return true
            }
            R.id.menu_hesap_ayarlari -> {
                startActivity(Intent(this,KullaniciAyarlariActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOutFromMyAccount() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    private fun setUserInfo() {
        FirebaseAuth.getInstance().currentUser?.let {
            tvKullaniciAdi.text=if (it.displayName.isNullOrEmpty()) "Tanımlanmadı" else it.displayName
            tvKullaniciEmail.text=it.email
            tvKullaniciUid.text=it.uid
        }
    }

    private fun initAuthStateListener() {
        myAuthStateListener= FirebaseAuth.AuthStateListener {
            it.currentUser?.let {}
            if (FirebaseAuth.getInstance().currentUser==null){
                startActivity(Intent(this,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
            }
        }
    }

    private fun userControl() {
        if(FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onResume() {
        super.onResume()
        userControl()
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
    }

}