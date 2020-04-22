package com.asiantech.summer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.item_viewpager_page_color.*

class ScreenColorFragment : Fragment() {

  private var position = 0
    companion object{
        private const val KEY = "items"
        fun newInstance(position: Int): ScreenColorFragment {
            var mfragment = ScreenColorFragment()
            mfragment.arguments = Bundle().apply{
                putInt(KEY, position)
            }
            return mfragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.item_viewpager_page_color, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments!!.getInt(KEY)
        when(position){
            0 -> {
                llMyHome.setBackgroundResource(R.color.colorYellow)
                tvNextPage.text = getString(R.string.step_1, position + 1)
            }
            1 -> {
                llMyHome.setBackgroundResource(R.color.colorPink)
                tvNextPage.text = getString(R.string.step_2, position + 1)
            }
            2 ->{
                llMyHome.setBackgroundResource(R.color.colorRed)
                tvNextPage.text = getString(R.string.step_3, position + 1)
            }
        }
    }
}