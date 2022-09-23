package com.bangkit.aplikasigithubuser.ui.main

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.aplikasigithubuser.R
import com.bangkit.aplikasigithubuser.adapter.SectionsPagerAdapter
import com.bangkit.aplikasigithubuser.databinding.ActivityUserDetailBinding
import com.bangkit.aplikasigithubuser.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserDetail : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    companion object {
        const val USER_PARCELABLE = "user_parcelable"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

    private lateinit var viewModel : DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USER_PARCELABLE)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(USER_PARCELABLE, username)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        if (username != null) {
            viewModel.setDetail(username)
        }

        viewModel.getDetailUsers().observe(this) {
            if (it != null) {
                binding.apply {
                    detailName.text = it.name
                    detailUsername.text = it.login
                    detailFollowers.text = "${it.followers}"
                    detailFollowing.text = "${it.following}"
                    detailCompany.text = it.company
                    detailRepository.text = "${it.public_repos}"
                    detailLocation.text = it.location
                    Glide.with(this@UserDetail)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(detailAvatar)
                }
            }
        }

        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = viewModel.findById(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count>0){
                        binding.btnFavorite.changeIconColor(R.color.red)
                        isFavorite = true
                    }else {
                        binding.btnFavorite.changeIconColor(R.color.white)
                        isFavorite = false
                    }

                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite){
                viewModel.findFavorite(username.toString(), id, avatarUrl.toString())
                binding.btnFavorite.changeIconColor(R.color.red)
            }else {
                viewModel.removeFavorite(id)
                binding.btnFavorite.changeIconColor(R.color.white)
            }
            binding.btnFavorite
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        supportActionBar?.title = "Detail User's"
    }

    private fun FloatingActionButton.changeIconColor(@ColorRes color: Int){
        val color = ContextCompat.getColor(this.context, color)
        imageTintList = ColorStateList.valueOf(color)
    }
}

