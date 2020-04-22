package com.asiantech.summer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabLayoutAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var mFm = fm
    var mFragmentItem: ArrayList<Fragment> = ArrayList()
    var mFragmentTitle: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment{
        return mFragmentItem[position]
    }

    fun addFragment(fragmentItem: Fragment, fragmentTitle:String){
        mFragmentItem.add(fragmentItem)
        mFragmentTitle.add(fragmentTitle)
    }
    override fun getCount() = mFragmentItem.size
}