package com.ahmettekin.chatsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sifremi_unuttum_dialog.*


class SifremiUnuttumDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sifremi_unuttum_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSifreyiUnuttumIptal.setOnClickListener {
            dialog?.dismiss()
        }
        btnSifreyiUnuttumTekrarGonder.setOnClickListener {
            if(etSifreyiTekrarGonderEmail.text.isNotEmpty()){
            FirebaseAuth.getInstance().sendPasswordResetEmail(etSifreyiTekrarGonderEmail.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(view.context,"Şifre sıfırlama maili gönderildi.",Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                    }else{
                        Toast.makeText(view.context,"Hata oluştu: ${it.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                        dialog?.dismiss()
                    }
                }
            }else{
                Toast.makeText(view.context,"Lütfen bir e-mail adresi giriniz.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}