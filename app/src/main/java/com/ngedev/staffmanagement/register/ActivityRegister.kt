package com.ngedev.staffmanagement.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import com.ngedev.staffmanagement.DialogLoading
import com.ngedev.staffmanagement.api.model.ResponseRegister
import com.ngedev.staffmanagement.databinding.ActivityRegisterBinding
import com.ngedev.staffmanagement.login.VMRegister

class ActivityRegister : AppCompatActivity() {
    val TAG = "ActivityLogin"

    val b by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    val vmRegister: VMRegister by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        vmRegister.responseRegister.observe({ lifecycle }, ::onRegister)
        vmRegister.error.observe({ lifecycle }, ::onError)

        b.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        b.etPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register()
                true
            } else false
        }

        b.bRegister.setOnClickListener {
            register()
        }
    }

    fun register(){
        mDialogLoading.show()
        val email = b.etEmail.text.toString()
        val password = b.etPassword.text.toString()
        vmRegister.login(email, password)
    }

    fun onRegister(response: ResponseRegister) {
        mDialogLoading.cancel()

        if(response.token.isBlank()){
            Toast.makeText(this, response.error, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Registered with id ${response.id} and token ${response.token}", Toast.LENGTH_LONG).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 3000)
        }
    }

    fun onError(response: String){
        mDialogLoading.cancel()
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
    }
}