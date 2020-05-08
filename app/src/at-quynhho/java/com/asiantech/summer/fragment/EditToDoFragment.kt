package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.data.ToDo
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.item_edit_to_do.*

class EditToDoFragment : Fragment() {
    companion object {
        fun newInstance(toDoItem: ToDo): EditToDoFragment {
            return EditToDoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("KEY_ID", toDoItem)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_edit_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btEditToDo.setOnClickListener {
            saveToDo()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flSignIn, ToDoFragment.newInstance())?.addToBackStack(null)?.commit()
        }
    }

    private fun saveToDo() {
        val title = edtEditContent.text.toString()

        if (title.isEmpty()){
            return
        }

        context?.let {
            val db = NoteDatabase.newInstance(it)
            val todo =
                arguments?.getParcelable<ToDo>("KEY_ID") // Lay to do tu bundle cua EditToDoFragment
            val idTodo = todo?.id ?: 0
            db?.toDoDao()?.updateTask(idTodo, title)
        }

    }
}
