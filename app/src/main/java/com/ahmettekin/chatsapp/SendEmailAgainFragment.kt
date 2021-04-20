package com.ahmettekin.chatsapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_send_email_again.*

class SendEmailAgainFragment : DialogFragment() {

    lateinit var mContext: Context
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_send_email_again, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext=view.context
        btnDialogGonder.setOnClickListener {
            if (etDialogMail.text.isNotEmpty() && etDialogSifre.text.isNotEmpty()) {
                girisYapveOnayMailiniTakrarGonder(etDialogMail.text.toString(),etDialogSifre.text.toString())
            }else{
                Toast.makeText(mContext,"Lütfen boşlukları doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }
        btnDialogIptal.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun girisYapveOnayMailiniTakrarGonder(email:String,sifre:String){
        val credential = EmailAuthProvider.getCredential(email, sifre)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onayMailiniTekrarGonder()
                    dialog?.dismiss()
                } else {
                    Toast.makeText(mContext, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onayMailiniTekrarGonder(){
        val kullanici = FirebaseAuth.getInstance().currentUser
        kullanici?.sendEmailVerification()?.addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(mContext,"Mail kutunuzu kontrol edin, mailinizi onaylayın",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(mContext,"Mail göderilirken sorun oluştu: ${it.exception?.message}",Toast.LENGTH_SHORT).show()
            }
        }
    }
}