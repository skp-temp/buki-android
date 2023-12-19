package com.example.skptemp.common.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val _fragments = mutableListOf<Fragment>()
    private val fragments: List<Fragment>
        get()  = _fragments

    private val _titles = mutableListOf<String>()
    private val titles: List<String>
        get() = _titles

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun getTitle(position: Int) = titles[position]

    fun addFragment(fragment: Fragment, title: String) {
        _fragments.add(fragment)
        _titles.add(title)
    }
}