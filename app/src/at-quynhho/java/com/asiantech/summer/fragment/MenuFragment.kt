package com.asiantech.summer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.Items
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.data.User
import com.asiantech.summer.recyclerview.MenuAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_menu_to_do.*

class MenuFragment(private var user: User) : Fragment() {

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var sharePrefer: SharePrefer
    private var listItem = ArrayList<Items>()

    companion object {
        private val USER_PROFILE = "user"
        fun newInstance(user: User): EditProfileFragment {
            return EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_PROFILE, getParcelable(user.toString()))
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()

        flToDo.apply {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flToDo, ToDoFragment())?.commit()
        }

    }

    private fun initData() {
        rvMenuDrawer.apply {
            layoutManager =
                LinearLayoutManager(this@MenuFragment.context, LinearLayoutManager.VERTICAL, false)
            menuAdapter = MenuAdapter(listItem, user)
            adapter = menuAdapter
            menuAdapter.setOnItemClick = {
                if (it == 0) {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.flSignIn, EditProfileFragment.newInstance(user))
                        ?.addToBackStack(null)?.commit()
                }
                if (it == 1) {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.flSignIn, AddToDoFragment())?.addToBackStack(null)?.commit()
                }
                if (it == 2) {
                    sharePrefer.isUserLogOut()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.flSignIn, LoginFragment())?.addToBackStack(null)?.commit()
                }
                menuAdapter.notifyDataSetChanged()
            }
        }
        listItem.add(Items(R.drawable.ic_profile, "Edit profile"))
        listItem.add(Items(R.drawable.ic_add, "Add To Do"))
        listItem.add(Items(R.drawable.ic_speak, "Logout"))

    }

    private fun initUser() {

    }
}
