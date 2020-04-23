package com.asiantech.summer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.summer.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.`at-quynhho`.activity_screen_color.*

class MainActivity : AppCompatActivity() {

    private var pagerAdapter: ViewPagerAdapter? = null

    companion object {
        private const val LAST_POSITION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_color)
        pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        ciIndicator.setViewPager(viewPager)
        tvAction.text = getString(R.string.next)
        tvAction.setOnClickListener {
            if (viewPager.currentItem < LAST_POSITION) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                val intent = Intent(this@MainActivity, ScreenImageActivity::class.java)
                startActivity(intent)
            }
        }
        init()
    }

    private fun init() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == LAST_POSITION) {
                    tvAction.text = getString(R.string.skip)
                }
                else{
                    tvAction.text = getString(R.string.next)
                }
            }
        })
    }
}


