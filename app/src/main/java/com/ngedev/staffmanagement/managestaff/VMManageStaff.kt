package com.ngedev.staffmanagement.managestaff

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ngedev.staffmanagement.Cons
import com.ngedev.staffmanagement.api.ApiUtils
import com.ngedev.staffmanagement.api.model.ResponseLogin
import com.ngedev.staffmanagement.api.model.Staff
import kotlinx.coroutines.launch

class VMManageStaff : ViewModel() {
    val TAG = "VMManageStaff"
    val mApi = ApiUtils().getApiService()

    private val mResponseStaff = MutableLiveData<Staff>()
    val responseStaff: LiveData<Staff> = mResponseStaff

    private val mMessage = MutableLiveData<String>()
    val message: LiveData<String> = mMessage

    fun addStaff(name: String, job: String) {
        viewModelScope.launch {

            mApi.addStaff(name, job).onSuccess {
                mResponseStaff.postValue(it)
            }.onFailure {
                mMessage.postValue(it.localizedMessage)
            }

        }
    }

    fun updateStaff(id: String, name: String, job: String) {
        viewModelScope.launch {
            val url = Cons.BASE_URL+"/api/users/$id"
            mApi.updateStaff(url, name, job).onSuccess {
                mResponseStaff.postValue(it)
            }.onFailure {
                mMessage.postValue(it.localizedMessage)
            }

        }
    }

    fun deleteStaff(id: String) {
        viewModelScope.launch {
            val url = Cons.BASE_URL+"/api/users/$id"
            mApi.deleteStaff(url).onSuccess {
                mResponseStaff.postValue(it)
            }.onFailure {
                mMessage.postValue(it.localizedMessage)
            }

        }
    }

}