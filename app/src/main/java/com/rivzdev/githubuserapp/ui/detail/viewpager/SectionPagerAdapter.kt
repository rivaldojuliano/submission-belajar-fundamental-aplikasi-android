package com.rivzdev.githubuserapp.ui.detail.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rivzdev.githubuserapp.ui.follow.followers.FollowersFragment
import com.rivzdev.githubuserapp.ui.follow.following.FollowingFragment

class SectionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)  {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        return fragment as Fragment
    }

}