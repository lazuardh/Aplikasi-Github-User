package com.bangkit.aplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.aplikasigithubuser.databinding.ItemRowUsersBinding
import com.bangkit.aplikasigithubuser.viewmodel.FollowersResponseItem
import com.bumptech.glide.Glide

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ListViewHolder>() {
    private val listFollowers = ArrayList<FollowersResponseItem>()

    fun setFollowers(followers:List<FollowersResponseItem>){
        listFollowers.clear()
        listFollowers.addAll(followers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    override fun getItemCount(): Int = listFollowers.size

    inner class ListViewHolder(private var binding: ItemRowUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(followers: FollowersResponseItem){
            binding.apply{
                Glide.with(root)
                    .load(followers.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)
                tvItemUsername.text = followers.login
            }
        }
    }
}
