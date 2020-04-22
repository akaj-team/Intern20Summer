package com.asiantech.summer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    companion object {
        fun newInstance(position: Int): Fragment = Fragment()
        private const val NUMBER_PAGE = 3
    }

    override fun getItem(position: Int): Fragment = ViewPagerAdapter.newInstance(position)

    override fun getCount(): Int = NUMBER_PAGE
}