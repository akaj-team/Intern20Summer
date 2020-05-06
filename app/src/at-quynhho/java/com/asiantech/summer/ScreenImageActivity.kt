package com.asiantech.summer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.adapter.TabLayoutAdapter
import com.asiantech.summer.fragment.AnotherFragment
import com.asiantech.summer.fragment.HomeFragment
import com.asiantech.summer.fragment.InformationFragment
import kotlinx.android.synthetic.`at-quynhho`.activity_home_screen_image.*

class ScreenImageActivity : AppCompatActivity() {

    var tabAdapter: TabLayoutAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen_image)
        val pages = mutableListOf<Page>()
        pages.add(Page(HomeFragment(), "HOME"))
        pages.add(Page(InformationFragment(), "INFO"))
        pages.add(Page(AnotherFragment(), "ANOTHER"))

        tabAdapter = TabLayoutAdapter(pages, supportFragmentManager)
        vpScreen.adapter = tabAdapter
        tlScreen.setupWithViewPager(vpScreen)
    }
}

