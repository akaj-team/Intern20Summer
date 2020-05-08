package com.asiantech.summer.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.Items
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.data.ToDo
import com.asiantech.summer.data.User
import com.asiantech.summer.database.NoteDatabase
import com.asiantech.summer.recyclerview.MenuAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_menu_to_do.*
import kotlinx.android.synthetic.`at-quynhho`.item_header_edit_profile.*
import kotlinx.android.synthetic.`at-quynhho`.nav_header_to_do_main.view.*

class MenuFragment : Fragment() {

    private lateinit var menuAdapter: MenuAdapter
    private var listItem = ArrayList<Items>()
//    private var listToDo = mutableListOf<ToDo>()

    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
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
        // Get user sql
        listItem[0]
        flToDo.apply {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flToDo, ToDoFragment())?.commit()
        }

    }

    private fun initData() {
        // Get id user
        val sharePrefer = SharePrefer(this.context!!)
        val userId = sharePrefer.getLogin()
        rvMenuDrawer.apply {
            val db = NoteDatabase.newInstance(context = context)
            val user = db?.userDao()?.findUserId(userId)
            Log.d("TAG11", "" + this)
            layoutManager =
                LinearLayoutManager(this@MenuFragment.context, LinearLayoutManager.VERTICAL, false)
            listItem.apply {
                clear()
                add(Items(user, null, null))
                add(Items(null, R.drawable.ic_profile, "Edit profile"))
                add(Items(null, R.drawable.ic_add, "Add To Do"))
                add(Items(null, R.drawable.ic_speak, "Logout"))
            }

            menuAdapter = MenuAdapter(listItem)
            adapter = menuAdapter
            menuAdapter.setOnItemClick = {
                if (it == 1) {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.flSignIn, EditProfileFragment.newInstance(user!!))
                        ?.addToBackStack(null)?.commit()
                }
                if (it == 2) {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.flSignIn, AddToDoFragment())?.addToBackStack(null)?.commit()
                }
                if (it == 3) {
                    sharePrefer.isUserLogOut()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.flSignIn, LoginFragment())?.addToBackStack(null)?.commit()
                }
                menuAdapter.notifyDataSetChanged()
            }
        }

    }
}
