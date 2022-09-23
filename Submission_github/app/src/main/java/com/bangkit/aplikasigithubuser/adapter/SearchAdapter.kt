package com.bangkit.aplikasigithubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.aplikasigithubuser.databinding.ItemRowUsersBinding
import com.bangkit.aplikasigithubuser.model.ItemsUser
import com.bumptech.glide.Glide

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val listSearch = ArrayList<ItemsUser>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users:List<ItemsUser>){
        listSearch.clear()
        listSearch.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: ItemsUser)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listSearch[position])
    }

    override fun getItemCount(): Int = listSearch.size

    inner class ListViewHolder(private var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsUser){
            binding.apply{
                Glide.with(root)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemUsername.text = user.login
                binding.root.setOnClickListener { onItemClickCallback.onItemClicked(user) }
            }
        }
    }
}