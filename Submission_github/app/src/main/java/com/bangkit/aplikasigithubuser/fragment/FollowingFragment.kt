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
import com.bangkit.aplikasigithubuser.adapter.FollowingAdapter
import com.bangkit.aplikasigithubuser.databinding.FragmentFollowingBinding
import com.bangkit.aplikasigithubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var followingModel: FollowingViewModel
    private lateinit var adapter: FollowingAdapter
    private lateinit var username: String
    private lateinit var rvFollowing : RecyclerView



    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = requireActivity().intent.getStringExtra((UserDetail.USER_PARCELABLE)).toString()
        _binding = FragmentFollowingBinding.bind(view)

        adapter = FollowingAdapter()

        rvFollowing = binding.rvFollowing
        rvFollowing.setHasFixedSize(true)
        rvFollowing.adapter
        rvFollowing.layoutManager

        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }

        followingModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        followingModel.setFollowing(username)

        followingModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        followingModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setFollowing(it)
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
