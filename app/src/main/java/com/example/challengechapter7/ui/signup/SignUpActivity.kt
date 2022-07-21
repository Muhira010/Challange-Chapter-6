package com.example.challengechapter7.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.challengechapter7.R
import com.example.challengechapter7.databinding.ActivitySignUpBinding
import com.example.challengechapter7.ui.MainActivity
import com.example.challengechapter7.ui.signin.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var refUsers : DatabaseReference
    private var  uid : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnContinue.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val fullName = binding.etName.text.toString()
        val email = binding.etEmailSignUp.text.toString()
        val password = binding.etPassSignUp.text.toString()
        val confirmPassword = binding.etConfirmPassSignUp.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
                if(it.isSuccessful){
                    uid = auth.currentUser!!.uid
                    refUsers = FirebaseDatabase.getInstance().reference.child("Users").child(uid)
                    val hashMap: HashMap<String, Any> = HashMap()
                    hashMap["uid"] = uid
                    hashMap["fullName"] = fullName
                    hashMap["email"] = email
                    hashMap["photo"] = ""

                    refUsers.updateChildren(hashMap).addOnCompleteListener {
                        if (it.isSuccessful){
                            Intent(this@SignUpActivity, SignInActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(it)
                            }
                        }
                    }
                }else{
                    Toast.makeText(this, "error" + it.exception!!
                        .message.toString(), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
    }
}