package com.asiantech.summer.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.Items
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.database.NoteDatabase
import com.asiantech.summer.recyclerview.MenuAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_menu_to_do.*


class MenuFragment : Fragment() {

    private lateinit var menuAdapter: MenuAdapter
    private var listItem = ArrayList<Items>()

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

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgDrawer.setOnClickListener {
            val navDrawer: DrawerLayout = view.findViewById(R.id.drawer_layout)
            if (!navDrawer.isDrawerOpen(Gravity.LEFT)) {
                navDrawer.openDrawer(Gravity.LEFT)
            } else {
                navDrawer.closeDrawer(Gravity.LEFT)
            }
        }
        initData()
        // Get user sql
        listItem[0]
        flToDo.apply {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flToDo, ToDoFragment())?.commit()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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
