package com.ngedev.staffmanagement.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ngedev.staffmanagement.api.ApiUtils
import com.ngedev.staffmanagement.api.model.ResponseLogin
import com.ngedev.staffmanagement.api.model.ResponseUsers
import kotlinx.coroutines.launch

class VMUsers : ViewModel() {
    val TAG = "VMLogin"
    val mApi = ApiUtils().getApiService()

    private val mResponseUsers = MutableLiveData<ResponseUsers>()
    val responseUsers: LiveData<ResponseUsers> = mResponseUsers

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {

            mApi.users().onSuccess {
                mResponseUsers.postValue(it)
            }.onFailure {
                mError.postValue(it.localizedMessage)
            }

        }
    }

}