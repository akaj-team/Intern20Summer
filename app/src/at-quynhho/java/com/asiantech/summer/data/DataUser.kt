package com.asiantech.summer.data

import com.asiantech.summer.R

class DataUser {
    companion object {
        fun initDataUser(): ArrayList<User> {
            val listUser = ArrayList<User>()
            listUser.add(
                User(
                    R.drawable.ic_person1,
                    "Christoph"
                )
            )
            listUser.add(
                User(
                    R.drawable.ic_person2,
                    "Eugenia"
                )
            )
            listUser.add(
                User(
                    R.drawable.ic_person3,
                    "Jeffrey"
                )
            )
            listUser.add(
                User(
                    R.drawable.ic_person4,
                    "Laura"
                )
            )
            listUser.add(
                User(
                    R.drawable.ic_person5,
                    "Violet"
                )
            )
            listUser.add(
                User(
                    R.drawable.ic_person6,
                    "Selena"
                )
            )
            listUser.add(
                User(
                    R.drawable.ic_person7,
                    "Lindemann"
                )
            )
            return listUser
        }
    }
}
