package com.bangkit.aplikasigithubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.aplikasigithubuser.databinding.ItemRowUsersBinding
import com.bangkit.aplikasigithubuser.model.FollowingResponseItem
import com.bumptech.glide.Glide

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {
    private val listFollowing = ArrayList<FollowingResponseItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setFollowing(following:List<FollowingResponseItem>){
        listFollowing.clear()
        listFollowing.addAll(following)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size

    inner class ListViewHolder(private var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(followings: FollowingResponseItem){
            binding.apply{
                Glide.with(root)
                    .load(followings.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemUsername.text = followings.login
            }
        }
    }
}