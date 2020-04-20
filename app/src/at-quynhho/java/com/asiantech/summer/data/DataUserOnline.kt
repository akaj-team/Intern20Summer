package com.asiantech.summer.data

import com.asiantech.summer.R

class DataUserOnline {

    companion object {
        fun initDataOnline(): ArrayList<UserOnline> {
            var listUserMessOn = ArrayList<UserOnline>()
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person1,
                    "Christoph"
                )
            )
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person2,
                    "Eugenia"
                )
            )
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person3,
                    "Jeffrey"
                )
            )
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person4,
                    "Laura"
                )
            )
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person5,
                    "Violet"
                )
            )
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person6,
                    "Selena"
                )
            )
            listUserMessOn.add(
                UserOnline(
                    R.drawable.ic_person7,
                    "Lindemann"
                )
            )
            return listUserMessOn
        }
    }
}