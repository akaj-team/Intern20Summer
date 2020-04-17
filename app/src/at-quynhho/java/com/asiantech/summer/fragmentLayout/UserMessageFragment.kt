package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.DataMessageUserView
import com.asiantech.summer.data.DataUserOnlineView
import com.asiantech.summer.recyclerView.TabBarInforUserMessageAdapter
import com.asiantech.summer.recyclerView.UserMessageOnlineViewAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_menu_message.*
import kotlinx.android.synthetic.`at-quynhho`.user_vertical_message.*


class UserMessageFragment : Fragment() {

    lateinit var adapterUserOn: UserMessageOnlineViewAdapter
    lateinit var adapterInfoMess : TabBarInforUserMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvListChat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapterUserOn = UserMessageOnlineViewAdapter()
            adapter = adapterUserOn
        }
        addDataUserOnline()
        rvMenuUserChat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterInfoMess = TabBarInforUserMessageAdapter()
            adapter = adapterInfoMess
        }
        addDataInforUserMess()
    }

    private fun addDataUserOnline(){
        var data = DataUserOnlineView.initDataOnline()
        adapterUserOn.submitList(data)
    }

    private fun addDataInforUserMess(){
        var dataInfo  = DataMessageUserView.initDataInfoMess()
        adapterInfoMess.submitInforMess(dataInfo)
    }
}
