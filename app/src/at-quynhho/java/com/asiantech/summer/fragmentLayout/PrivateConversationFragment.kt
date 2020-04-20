package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.DataMessage
import com.asiantech.summer.data.DataUserOnline
import com.asiantech.summer.data.UserOnline
import com.asiantech.summer.recyclerView.ContentAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_user_message.*


class PrivateConversationFragment : Fragment() {

    lateinit var adapterContent: ContentAdapter
    private var content: ArrayList<UserOnline> = initUser()
    private var conversation = initCon()


    companion object {

        fun newInstance(): PrivateConversationFragment {
            return PrivateConversationFragment()
        }

        private fun initCon(): ArrayList<DataMessage> {
            val list = ArrayList<DataMessage>()
            list.add(DataMessage("Anna", "Hello!!", 12))
            return list
        }

        private fun initUser(): ArrayList<UserOnline> {
            val list = ArrayList<UserOnline>()
            list.add(UserOnline(R.drawable.ic_person1, "Anna"))
            return list
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvContent.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterContent = ContentAdapter(arrayListOf())
            adapter = adapterContent

        }
        clContent.apply {
            adapterContent = ContentAdapter(conversation)
        }
        imgSend.setOnClickListener {

        }
        imgExit.setOnClickListener {
            sendMessages()
        }
    }
    fun sendMessages() {
    }

//    private fun resetInput() {
//        edtMessage.text.clear()

    // Hide keyboard
//        val inputManager =
//            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.hideSoftInputFromWindow(
//            currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
//        )
//    }

}
