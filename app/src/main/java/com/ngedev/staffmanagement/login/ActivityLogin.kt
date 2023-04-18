package com.ngedev.staffmanagement.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import com.ngedev.staffmanagement.DialogLoading
import com.ngedev.staffmanagement.api.model.ResponseLogin
import com.ngedev.staffmanagement.databinding.ActivityLoginBinding
import com.ngedev.staffmanagement.register.ActivityRegister
import com.ngedev.staffmanagement.users.ActivityUsers

class ActivityLogin : AppCompatActivity() {
    val TAG = "ActivityLogin"

    val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val vmLogin: VMLogin by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        vmLogin.responseLogin.observe({ lifecycle }, ::onLogin)
        vmLogin.error.observe({ lifecycle }, ::onError)

        b.etPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
                true
            } else false
        }

        b.bLogin.setOnClickListener {
            login()
        }

        b.bRegister.setOnClickListener {
            startActivity(Intent(this, ActivityRegister::class.java))
        }
    }

    fun login(){
        mDialogLoading.show()
        val email = b.etEmail.text.toString()
        val password = b.etPassword.text.toString()
        vmLogin.login(email, password)
    }

    fun onLogin(response: ResponseLogin) {
        mDialogLoading.cancel()

        if(response.token.isBlank()){
            Toast.makeText(this, response.error, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Logged in with token ${response.token}", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, ActivityUsers::class.java))
                finish()
            }, 3000)
        }
    }

    fun onError(response: String){
        mDialogLoading.cancel()
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
    }
}