package com.ngedev.staffmanagement.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ngedev.staffmanagement.api.ApiUtils
import com.ngedev.staffmanagement.api.model.ResponseLogin
import kotlinx.coroutines.launch

class VMLogin : ViewModel() {
    val TAG = "VMLogin"
    val mApi = ApiUtils().getApiService()

    private val mResponseLogin = MutableLiveData<ResponseLogin>()
    val responseLogin: LiveData<ResponseLogin> = mResponseLogin

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    fun login(email: String, password: String) {
        viewModelScope.launch {

            mApi.login(email, password).onSuccess {
                mResponseLogin.postValue(it)
            }.onFailure {
                val errorMessage = it.localizedMessage
                if(errorMessage.contains("{")){
                    val responseLogin = Gson().fromJson(it.localizedMessage, ResponseLogin::class.java)
                    mError.postValue(responseLogin.error)
                } else {
                    mError.postValue(errorMessage)
                }
            }

        }
    }

}