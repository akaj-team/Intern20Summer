package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.User
import com.asiantech.summer.recyclerView.SearchAdapter
import kotlinx.android.synthetic.`at-quynhho`.item_list_user.*

class ListUserFragment : Fragment() {

    lateinit var adapterUser: SearchAdapter
    var listUser = ArrayList<User>()

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

    private fun initAdapter() {
        rvListUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterUser = SearchAdapter(listUser)
            adapter = adapterUser
        }
    }

    private fun initData() {
        listUser.add(User(R.drawable.ic_person1, "Christoph"))
        listUser.add(User(R.drawable.ic_person2, "Eugenia"))
        listUser.add(User(R.drawable.ic_person3, "Jeffrey"))
        listUser.add(User(R.drawable.ic_person4, "Laura"))
        listUser.add(User(R.drawable.ic_person5, "Violet"))
        listUser.add(User(R.drawable.ic_person6, "Selena"))
        listUser.add(User(R.drawable.ic_person7, "Violet"))
    }
}