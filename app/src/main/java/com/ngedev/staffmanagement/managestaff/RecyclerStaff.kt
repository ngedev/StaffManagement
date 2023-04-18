package com.ngedev.staffmanagement.managestaff

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngedev.staffmanagement.api.model.Staff
import com.ngedev.staffmanagement.databinding.RowStaffBinding

class RecyclerStaff(
    private val listStaff: List<Staff>,
    val onClickStaff: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerStaff.ViewHolderStaff>() {
    val TAG = "RecyclerStaff"
    lateinit var mContext: Context
    lateinit var binding: RowStaffBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStaff {
        mContext = parent.context
        binding = RowStaffBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolderStaff(binding)
    }

    override fun getItemCount(): Int = listStaff.size

    override fun onBindViewHolder(holder: ViewHolderStaff, position: Int) {
        val dataStaff = listStaff[position]

        holder.binding.tvName.text = dataStaff.name
        holder.binding.tvJob.text = dataStaff.job

        holder.binding.root.setOnClickListener { onClickStaff(position) }
    }

    class ViewHolderStaff(b: RowStaffBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }
}