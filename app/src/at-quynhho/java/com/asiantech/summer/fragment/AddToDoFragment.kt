package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.data.ToDo
import com.asiantech.summer.data.User
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.recyclerview_item.*

class AddToDoFragment : Fragment() {
    private var toDo: ToDo? = null
    private var user: User? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_add_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addToDo()
    }

    private fun addToDo() {
        val title = tvItemTodo.text.toString()
        val toDo = ToDo(id = 0, todoTitle = title, isDone = true, uid = 0)
        context?.let {
            val db = NoteDatabase.newInstance(it)
            db?.toDoDao()?.insertTask(toDo)
            db?.toDoDao()?.getAllTask().let {
                Log.d("TAG11", "" + it?.get(it.size - 1)?.todoTitle)
            }
        }
    }
}