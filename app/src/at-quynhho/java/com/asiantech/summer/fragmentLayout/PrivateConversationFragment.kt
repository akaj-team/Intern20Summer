package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.DataMessage
import com.asiantech.summer.data.UserOnline
import com.asiantech.summer.recyclerView.ContentAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_user_message.*
import kotlinx.android.synthetic.`at-quynhho`.item_person_conversation.view.*


class PrivateConversationFragment : Fragment() {

    private lateinit var viewModel: ChatView
    lateinit var adapterContent: ContentAdapter
    private var listMyMessage = arrayListOf<DataMessage>()
    private var listOtherMessage = arrayListOf<DataMessage>()


    companion object {

        fun newInstance(): PrivateConversationFragment {
            return PrivateConversationFragment()
        }

//        private fun initCon(): ArrayList<DataMessage> {
//            val list = ArrayList<DataMessage>()
//            list.add(DataMessage("Anna", "Hello!!", 12))
//            return list
//        }
//
//        private fun initUser(): ArrayList<UserOnline> {
//            val list = ArrayList<UserOnline>()
//            list.add(UserOnline(R.drawable.ic_person1, "Anna"))
//            return list
//        }

    }

    fun initData(){
        listMyMessage.add(DataMessage("uyen", "i love you"))
        listOtherMessage.add(DataMessage("quynh", "i love u tooo"))
        listMyMessage.add(DataMessage("uyen", "i love you"))
        listOtherMessage.add(DataMessage("quynh", "i love u tooo"))
        listMyMessage.add(DataMessage("uyen", "i love you"))
        listOtherMessage.add(DataMessage("quynh", "i love u tooo"))
        listMyMessage.add(DataMessage("uyen", "i love you"))
        listOtherMessage.add(DataMessage("quynh", "i love u tooo"))
    }

    fun initAdapter(){
        rvContent.run {
            adapter = adapterContent
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapterContent = ContentAdapter(listMyMessage, listOtherMessage)
        return inflater.inflate(R.layout.fragment_user_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()

//        cvNavBar.apply {
//            adapterContent = ContentAdapter(conversation)
//            tvUserConver
//        }

        imgSend.setOnClickListener {

        }
        imgExit.setOnClickListener {

        }
    }

    class ChatView() {

    }
}