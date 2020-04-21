package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.UserOnline
import com.asiantech.summer.recyclerView.SearchAdapter
import kotlinx.android.synthetic.`at-quynhho`.item_list_user.*

class ListUserFragment : Fragment() {

    lateinit var adapterUser: SearchAdapter
    var listUserMessOn = ArrayList<UserOnline>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()
        adapterUser.userListClicked = {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.flMainMessage, HomePageFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    fun initAdapter() {
        rvListUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterUser = SearchAdapter(listUserMessOn)
            adapter = adapterUser
        }
    }

    fun initData() {
        listUserMessOn.add(UserOnline(R.drawable.ic_person1, "Christoph"))
        listUserMessOn.add(UserOnline(R.drawable.ic_person2, "Eugenia"))
        listUserMessOn.add(UserOnline(R.drawable.ic_person3, "Jeffrey"))
        listUserMessOn.add(UserOnline(R.drawable.ic_person4, "Laura"))
        listUserMessOn.add(UserOnline(R.drawable.ic_person5, "Violet"))
        listUserMessOn.add(UserOnline(R.drawable.ic_person6, "Selena"))
        listUserMessOn.add(UserOnline(R.drawable.ic_person7, "Violet"))
    }
}