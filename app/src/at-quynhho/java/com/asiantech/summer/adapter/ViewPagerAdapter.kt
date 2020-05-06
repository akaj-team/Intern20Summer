package com.asiantech.summer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.summer.fragment.ScreenColorFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    companion object {
        private const val NUMBER_PAGE = 3
    }

    override fun getItem(position: Int): Fragment = ScreenColorFragment.newInstance(position)

    override fun getCount(): Int = NUMBER_PAGE
}
