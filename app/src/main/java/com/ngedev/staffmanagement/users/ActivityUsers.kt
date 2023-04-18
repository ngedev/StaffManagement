package com.ngedev.staffmanagement.users

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ngedev.staffmanagement.DialogLoading
import com.ngedev.staffmanagement.R
import com.ngedev.staffmanagement.api.model.ResponseUsers
import com.ngedev.staffmanagement.api.model.User
import com.ngedev.staffmanagement.databinding.ActivityUsersBinding
import com.ngedev.staffmanagement.managestaff.ActivityManageStaff


class ActivityUsers : AppCompatActivity() {
    val TAG = "ActivityUsers"

    val b by lazy { ActivityUsersBinding.inflate(layoutInflater) }
    val vmUsers: VMUsers by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }
    val mListUser = mutableListOf<User>()
    val mAdapter by lazy { RecyclerUsers(mListUser) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)

        vmUsers.responseUsers.observe({ lifecycle }, ::onLoadUsers)
        vmUsers.error.observe({ lifecycle }, ::onError)

        mDialogLoading.show()

        b.rvUsers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_users, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuManageStaff -> {
                startActivity(Intent(this, ActivityManageStaff::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onLoadUsers(response: ResponseUsers){
        mDialogLoading.cancel()
        mListUser.addAll(response.data)
        mAdapter.notifyDataSetChanged()
    }

    fun onError(response: String){
        mDialogLoading.cancel()
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
    }
}