package com.bangkit.aplikasigithubuser.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.aplikasigithubuser.R
import com.bangkit.aplikasigithubuser.User
import com.bangkit.aplikasigithubuser.adapter.ListUserAdapter
import com.bangkit.aplikasigithubuser.adapter.SearchAdapter
import com.bangkit.aplikasigithubuser.databinding.ActivityMainBinding
import com.bangkit.aplikasigithubuser.favorite.FavoriteActivity
import com.bangkit.aplikasigithubuser.model.ItemsUser
import com.bangkit.aplikasigithubuser.theme.PrefHelper
import com.bangkit.aplikasigithubuser.theme.Settings
import com.bangkit.aplikasigithubuser.theme.SettingsModelFactory
import com.bangkit.aplikasigithubuser.theme.SettingsViewModel
import com.bangkit.aplikasigithubuser.viewmodel.SearchViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity()  {
    private lateinit var rvUsers: RecyclerView
    private val list = ArrayList<User>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : SearchViewModel
    private lateinit var adapter: SearchAdapter

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tema gelap
        val pref = PrefHelper.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsModelFactory(pref))[SettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        //favorite
        adapter = SearchAdapter()
        rvUsers = binding.rvUsers
        rvUsers.setHasFixedSize(true)
        rvUsers.adapter
        rvUsers.layoutManager
        rvUsers.adapter = adapter

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = adapter
        }

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        viewModel.getSearchUsers().observe(this){
            if (it != null){
                adapter.setData(it)
                adapter.notifyDataSetChanged()
                showLoading(false)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        rvUsers = findViewById(R.id.rv_users)
        rvUsers.setHasFixedSize(true)

        list.addAll(listUser)
        showRecyclerList()

        supportActionBar?.title = "Github User's"

    }

    private val listUser: ArrayList<User>
    @SuppressLint("Recycle")
    get(){
        val dataName = resources.getStringArray(R.array.name)
        val dataUsername = resources.getStringArray(R.array.username)
        val dataLocation = resources.getStringArray(R.array.location)
        val dataCompany = resources.getStringArray(R.array.company)
        val dataRepository = resources.getStringArray(R.array.repository)
        val dataFollowing = resources.getStringArray(R.array.following)
        val dataFollowers = resources.getStringArray(R.array.followers)
        val dataAvatar = resources.obtainTypedArray(R.array.avatar)
        val listUser = ArrayList<User>()
        for (i in dataName.indices) {
            val user = User(dataName[i], dataUsername[i],dataLocation[i],dataCompany[i],dataRepository[i],dataFollowing[i],dataFollowers[i],dataAvatar.getResourceId(i, -1))
            listUser.add(user)
        }
        return listUser
    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.setHasFixedSize(true)

        val listUserAdapter = ListUserAdapter(list)
        rvUsers.adapter = listUserAdapter
        showLoading(false)

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedHero(data)
            }
        })

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback{
            override fun onItemClicked(user: ItemsUser) {
                showSearch(user)
            }
        })
    }

    private fun showSearch(people: ItemsUser) {
        val move = Intent(this, UserDetail::class.java)
        move.putExtra(UserDetail.USER_PARCELABLE, people.login)
        move.putExtra(UserDetail.EXTRA_ID, people.id)
        move.putExtra(UserDetail.EXTRA_AVATAR, people.avatarUrl)
        startActivity(move)
        Toast.makeText(this, "Kamu memilih " + people.login, Toast.LENGTH_SHORT).show()
    }

    private fun showSelectedHero(people: User) {
        val move = Intent(this, UserDetail::class.java)
        move.putExtra(UserDetail.USER_PARCELABLE, people.name)
        startActivity(move)
        Toast.makeText(this, "Kamu memilih " + people.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.rvUsers.adapter = adapter
                if (query.isEmpty()){
                    showLoading(false)
                    binding.rvUsers
                    viewModel.searchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                binding.rvUsers.adapter
                if (newText.isEmpty()){
                    showLoading(false)
                }else{
                    showLoading(true)
                    binding.rvUsers.adapter = adapter
                    viewModel.searchUser(newText)
                }
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.settings -> {
                Intent(this, Settings::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}