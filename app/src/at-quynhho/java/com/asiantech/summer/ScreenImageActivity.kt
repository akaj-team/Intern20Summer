package com.asiantech.summer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.summer.adapter.TabLayoutAdapter
import com.asiantech.summer.fragment.HomeFragment
import com.asiantech.summer.fragment.InformationFragment
import com.asiantech.summer.fragment.AnotherFragment
import kotlinx.android.synthetic.`at-quynhho`.activity_main.*

class ScreenImageActivity : AppCompatActivity() {

    var pagerAdapter: TabLayoutAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = TabLayoutAdapter(supportFragmentManager)
        pagerAdapter?.addFragment(HomeFragment(), "HOME")
        pagerAdapter?.addFragment(InformationFragment(), "INFO")
        pagerAdapter?.addFragment(AnotherFragment(), "ANOTHER")

        vpScreen.adapter = pagerAdapter
        tlScreen.setupWithViewPager(vpScreen)
    }
}
