package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.DataConversation
import com.asiantech.summer.data.DataUserOnline
import com.asiantech.summer.recyclerView.ConversationAdapter
import com.asiantech.summer.recyclerView.UserOnlineAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_menu_message.*

class HomePageFragment : Fragment() {

    lateinit var adapterUserOn: UserOnlineAdapter
    lateinit var adapterConversation: ConversationAdapter
    private var conversations = DataConversation.initDataInfoMess()

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
            adapterUserOn = UserOnlineAdapter()
            adapter = adapterUserOn
        }
        addDataUserOnline()
        rvMenuUserChat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterConversation = ConversationAdapter(conversations)
            adapter = adapterConversation
        }
        adapterConversation.userChatClicked = {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.flMainMessage, PrivateConversationFragment.newInstance()
                )
                ?.addToBackStack(null)
                ?.commit()
        }
        adapterConversation.onStartClick = {
            conversations[it].isLike = !conversations[it].isLike
            adapterConversation.notifyDataSetChanged()
        }

        svSearch.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(
                    R.id.flMainMessage, ListUserFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }

    }

    private fun addDataUserOnline() {
        val data = DataUserOnline.initDataOnline()
        adapterUserOn.submitList(data)
    }

}
