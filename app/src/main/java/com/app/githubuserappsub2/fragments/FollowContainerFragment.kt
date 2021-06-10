package com.app.githubuserappsub2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.app.githubuserappsub2.R
import com.app.githubuserappsub2.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class FollowContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_follow_container, container, false)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.adapter = sectionPagerAdapter
        val tabsLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabsLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        return view
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower_text,
            R.string.tab_following_text
        )
    }
}