package com.bangkit.aplikasigithubuser.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.aplikasigithubuser.R
import com.bangkit.aplikasigithubuser.ui.main.UserDetail
import com.bangkit.aplikasigithubuser.adapter.FollowersAdapter
import com.bangkit.aplikasigithubuser.databinding.FragmentFollowerBinding
import com.bangkit.aplikasigithubuser.viewmodel.FollowersViewModels

class FollowerFragment : Fragment(R.layout.fragment_follower) {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var followersModel: FollowersViewModels
    private lateinit var adapter: FollowersAdapter
    private lateinit var username: String
    private lateinit var rvFollowing : RecyclerView



    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = requireActivity().intent.getStringExtra((UserDetail.USER_PARCELABLE)).toString()
        _binding = FragmentFollowerBinding.bind(view)

        adapter = FollowersAdapter()

        rvFollowing = binding.rvFollower
        rvFollowing.setHasFixedSize(true)
        rvFollowing.adapter
        rvFollowing.layoutManager

        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }

        followersModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())[FollowersViewModels::class.java]
        followersModel.setFollowers(username)

        followersModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        followersModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setFollowers(it)
                adapter.notifyDataSetChanged()
                showLoading(false)
            }
        }
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}