package com.asiantech.summer.fragmentLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.R
import com.asiantech.summer.data.DataMessage
import com.asiantech.summer.data.UserOnline
import com.asiantech.summer.recyclerView.ContentAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_user_message.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class PrivateConversationFragment : Fragment() {

    lateinit var adapterContent: ContentAdapter
    private val messageContent = mutableListOf<DataMessage>()
    private val navBarUser = mutableListOf<UserOnline>()

    companion object {
        fun newInstance(): PrivateConversationFragment {
            return PrivateConversationFragment()
        }

    }

    fun initData() {
        messageContent.add(DataMessage(true, "i love you", 12))
        messageContent.add(DataMessage(false, "i love you", 7))
        messageContent.add(DataMessage(true, "i love you", 8))
        messageContent.add(DataMessage(false, "i love you", 8))
        messageContent.add(DataMessage(false, "i love you", 9))
    }

    fun initNavBar() {
        navBarUser.add(UserOnline((R.drawable.ic_person1), "Violet"))
        navBarUser.add(UserOnline((R.drawable.ic_person2), "Violet"))
        navBarUser.add(UserOnline((R.drawable.ic_person3), "Violet"))
        navBarUser.add(UserOnline((R.drawable.ic_person4), "Violet"))
    }

    fun initAdapter() {
        rvContent.run {
            adapter = adapterContent
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterContent = ContentAdapter(messageContent)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()
        initNavBar()
        var random = ThreadLocalRandom.current().nextInt(0, navBarUser.size - 1)
        tvUserConver.text = navBarUser[random].userNameOn
        imgPersonConver.setImageResource(navBarUser[random].pictureUserOn)
        imgSend.setOnClickListener {
            var newMessage = edtMessage.text
            //            var newTime = tvOtherMessageTime.text
            messageContent.add(
                DataMessage(
                    getRandomBoolean(),
                    newMessage.toString(),
                    Calendar.getInstance().timeInMillis
                )
            )
            adapterContent.notifyDataSetChanged()

            newMessage.clear()
        }
        imgExit.setOnClickListener {
            parentFragment?.let {
                if (it is HomePageFragment) {
                    it.childFragmentManager.popBackStack()
                }
            } ?: activity?.supportFragmentManager?.popBackStack()
        }
    }

    fun getRandomBoolean(): Boolean {
        val random = Random
        return random.nextBoolean()
    }

}

