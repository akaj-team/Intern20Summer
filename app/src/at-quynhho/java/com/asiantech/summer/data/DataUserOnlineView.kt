package com.asiantech.summer.data

import com.asiantech.summer.R
import com.asiantech.summer.recyclerView.UserOnlineView

class DataUserOnlineView {

    companion object {
        fun initDataOnline(): ArrayList<UserOnlineView> {
            var listUserMessOn = ArrayList<UserOnlineView>()
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person1,
                    "Christoph"
                )
            )
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person2,
                    "Eugenia"
                )
            )
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person3,
                    "Jeffrey"
                )
            )
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person4,
                    "Laura"
                )
            )
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person5,
                    "Violet"
                )
            )
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person6,
                    "Selena"
                )
            )
            listUserMessOn.add(
                UserOnlineView(
                    R.drawable.ic_person7,
                    "Lindemann"
                )
            )
            return listUserMessOn
        }
    }
}