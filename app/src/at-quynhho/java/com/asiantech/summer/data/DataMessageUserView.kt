package com.asiantech.summer.data

import com.asiantech.summer.R
import com.asiantech.summer.recyclerView.InformationUserMessage

class DataMessageUserView {
    companion object{
        fun initDataInfoMess() : ArrayList<InformationUserMessage>{
            var listInformationMessUser = ArrayList<InformationUserMessage>()
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person1,
                    "Chirstoph",
                    "Hey! How it's doing", "3"
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person2,
                    "Chirstoph",
                    "What kind of music do you like?", "1"
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person3,
                    "Chirstoph",
                    "Sounds good to me!", null
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person4,
                    "Chirstoph",
                    "Hi Tina. How's you night going", null
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person5,
                    "Chirstoph",
                    "Hey girl!", null
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person6,
                    "Chirstoph",
                    "Where does it come from?", null
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person7,
                    "Chirstoph",
                    "Where can I get some?",  null
                )
            )
            listInformationMessUser.add(
                InformationUserMessage(
                    R.drawable.ic_person1,
                    "Chirstoph",
                    "It is a long established fact that",  "2"
                )
            )
        return listInformationMessUser
        }

    }

}

