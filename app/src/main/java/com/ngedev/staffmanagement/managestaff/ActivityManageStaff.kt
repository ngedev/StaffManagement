package com.ngedev.staffmanagement.managestaff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ngedev.staffmanagement.DialogLoading
import com.ngedev.staffmanagement.api.model.Staff
import com.ngedev.staffmanagement.databinding.ActivityManageStaffBinding
import com.ngedev.staffmanagement.databinding.SheetManageStaffBinding

class ActivityManageStaff : AppCompatActivity() {
    val TAG = "ActivityManageStaff"

    val b by lazy { ActivityManageStaffBinding.inflate(layoutInflater) }
    val vmManageStaff: VMManageStaff by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }
    val mListStaff = mutableListOf<Staff>()
    val mAdapter by lazy { RecyclerStaff(mListStaff, ::onClickStaff) }
    var mSelectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        vmManageStaff.responseStaff.observe({ lifecycle }, ::onResponseStaff)
        vmManageStaff.message.observe({ lifecycle }, ::handleMessage)

        b.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        b.rvStaff.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mAdapter
        }

        b.fabAdd.setOnClickListener {
            showSheetManageStaff(Staff("","","","",""))
        }
    }

    fun showSheetManageStaff(dataStaff: Staff) {
        val dialog = BottomSheetDialog(this)
        val b = SheetManageStaffBinding.inflate(LayoutInflater.from(this))

        if(dataStaff.id.isNullOrEmpty()){
            b.tvId.visibility = View.GONE
            b.bUpdate.visibility = View.GONE
            b.bDelete.visibility = View.GONE
        } else {
            b.tvId.text = "Staff ID "+dataStaff.id
            b.bAdd.visibility = View.GONE
        }

        b.etName.setText(dataStaff.name)
        b.etJob.setText(dataStaff.job)

        if(dataStaff.createdAt.isNullOrEmpty()){
            b.tvCreated.visibility = View.GONE
        } else {
            b.tvCreated.text = "Created at "+dataStaff.createdAt
        }

        if(dataStaff.updatedAt.isNullOrEmpty()){
            b.tvUpdated.visibility = View.GONE
        } else {
            b.tvUpdated.text = "Updated at "+dataStaff.updatedAt
        }

        b.ivClose.setOnClickListener {
            dialog.cancel()
        }

        b.bAdd.setOnClickListener {
            if(b.etName.text.isNullOrEmpty()){
                b.tilName.error = "please fill this field"
            } else if(b.etJob.text.isNullOrEmpty()){
                b.tilJob.error = "please fill this field"
            } else {
                mDialogLoading.show()
                dialog.cancel()

                val name = b.etName.text.toString()
                val job = b.etJob.text.toString()
                vmManageStaff.addStaff(name, job)
            }
        }

        b.bUpdate.setOnClickListener {
            if(b.etName.text.isNullOrEmpty()){
                b.tilName.error = "please fill this field"
            } else if(b.etJob.text.isNullOrEmpty()){
                b.tilJob.error = "please fill this field"
            } else {
                mDialogLoading.show()
                dialog.cancel()

                val name = b.etName.text.toString()
                val job = b.etJob.text.toString()
                vmManageStaff.updateStaff(dataStaff.id!!, name, job)
            }
        }

        b.bDelete.setOnClickListener {
            mDialogLoading.show()
            dialog.cancel()

            vmManageStaff.deleteStaff(dataStaff.id!!)
        }

        dialog.setContentView(b.root)
        dialog.show()
    }

    fun onResponseStaff(response: Staff) {
        mDialogLoading.cancel()

        //INSERT
        if(!response.createdAt.isNullOrEmpty()){
            mListStaff.add(response)
            mAdapter.notifyItemInserted(mListStaff.size-1)
            b.tvEmptyList.visibility = View.GONE
        }

        //UPDATE
        if(!response.updatedAt.isNullOrEmpty()){
            mListStaff[mSelectedIndex].name = response.name
            mListStaff[mSelectedIndex].job = response.job
            mListStaff[mSelectedIndex].updatedAt = response.updatedAt
            mAdapter.notifyItemChanged(mSelectedIndex)
        }
    }

    fun handleMessage(response: String){
        mDialogLoading.cancel()

        if(response=="204"){
            //DELETE
            mListStaff.removeAt(mSelectedIndex)
            mAdapter.notifyDataSetChanged()

            if(mListStaff.size==0){
                b.tvEmptyList.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickStaff(position: Int) {
        mSelectedIndex = position
        showSheetManageStaff(mListStaff[mSelectedIndex])
    }
}