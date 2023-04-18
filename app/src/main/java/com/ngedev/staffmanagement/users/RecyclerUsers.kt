package com.ngedev.staffmanagement.users

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngedev.staffmanagement.api.model.User
import com.ngedev.staffmanagement.databinding.RowUserBinding

class RecyclerUsers(
    private val listUser: List<User>
) :
    RecyclerView.Adapter<RecyclerUsers.ViewHolderUser>() {
    val TAG = "RecyclerUsers"
    lateinit var mContext: Context
    lateinit var binding: RowUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        mContext = parent.context
        binding = RowUserBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolderUser(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val dataUser = listUser[position]

        Glide.with(mContext).load(dataUser.avatar).circleCrop().into(binding.ivUser)
        holder.binding.tvName.text = "${dataUser.first_name} ${dataUser.last_name}"
    }

    class ViewHolderUser(b: RowUserBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }
}