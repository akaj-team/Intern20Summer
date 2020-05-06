package com.asiantech.summer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.summer.Page

class TabLayoutAdapter(private val pages:List<Page>,fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment{
        return pages[position].fragment
    }
    override fun getCount() = pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].title
    }
}
