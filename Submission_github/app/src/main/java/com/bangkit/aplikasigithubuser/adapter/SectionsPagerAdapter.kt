package com.bangkit.aplikasigithubuser.adapter


import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bangkit.aplikasigithubuser.R
import com.bangkit.aplikasigithubuser.fragment.FollowerFragment
import com.bangkit.aplikasigithubuser.fragment.FollowingFragment

class SectionsPagerAdapter(private val context: Context, fragmentManager: FragmentManager, data: Bundle) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
   private var fragmentBundle: Bundle = data

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_following,
        R.string.tab_followers,

        )

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowerFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
//    }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.getString(TAB_TITLES[position])
    }
}