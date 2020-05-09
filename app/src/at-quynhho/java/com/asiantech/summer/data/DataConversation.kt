package com.asiantech.summer.data

import com.asiantech.summer.R

class DataConversation {
    companion object {
        fun initDataConversation(): ArrayList<Conversation> {
            val conversation = ArrayList<Conversation>()
            conversation.add(
                Conversation(
                    R.drawable.ic_person1,
                    "Chirstoph",
                    "Hey! How it's doing", 3,
                    isLike = true
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person2,
                    "Chirstoph",
                    "What kind of music do you like?", 1,
                    isLike = false
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person3,
                    "Chirstoph",
                    "Sounds good to me!", 0, isLike = false
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person4,
                    "Chirstoph",
                    "Hi Tina. How's you night going", 0, isLike = true
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person5,
                    "Chirstoph",
                    "Hey girl!", 0, isLike = false
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person6,
                    "Chirstoph",
                    "Where does it come from?", 0, isLike = true
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person7,
                    "Chirstoph",
                    "Where can I get some?", 0, isLike = true
                )
            )
            conversation.add(
                Conversation(
                    R.drawable.ic_person1,
                    "Chirstoph",
                    "It is a long established fact that", 2, isLike = false
                )
            )
            return conversation
        }

    }

}

