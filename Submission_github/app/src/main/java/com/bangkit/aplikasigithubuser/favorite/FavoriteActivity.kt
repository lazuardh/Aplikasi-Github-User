package com.bangkit.aplikasigithubuser.favorite

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.aplikasigithubuser.adapter.SearchAdapter
import com.bangkit.aplikasigithubuser.data.entity.UserFavorite
import com.bangkit.aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.bangkit.aplikasigithubuser.model.ItemsUser
import com.bangkit.aplikasigithubuser.ui.main.UserDetail
import java.util.ArrayList

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ItemsUser) {
                showSearch(user)
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                //mapping
                val list = mapList(it)
                adapter.setData(list)
            }
        }

        supportActionBar?.title = "Favourite User's"
    }

    private fun mapList(userFavorite: List<UserFavorite>): ArrayList<ItemsUser> {
        val listFavorite = ArrayList<ItemsUser>()
        for (user in userFavorite){
            val userMapped = ItemsUser(
                user.id,
                user.login,
                user.avatar_url
            )
            listFavorite.add(userMapped)
        }
        return listFavorite
    }

    private fun showSearch(people: ItemsUser) {
        val move = Intent(this@FavoriteActivity, UserDetail::class.java)
        move.putExtra(UserDetail.USER_PARCELABLE, people.login)
        move.putExtra(UserDetail.EXTRA_ID, people.id)
        move.putExtra(UserDetail.EXTRA_AVATAR, people.avatarUrl)
        startActivity(move)
        Toast.makeText(this, "Kamu memilih " + people.login, Toast.LENGTH_SHORT).show()
    }
}