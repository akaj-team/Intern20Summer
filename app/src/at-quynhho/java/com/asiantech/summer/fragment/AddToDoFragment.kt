package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.data.ToDo
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.item_add_to_do.*

class AddToDoFragment : Fragment() {
//    companion object{
//        fun newInstance(toDo: ToDo) : AddToDoFragment{
//            return AddToDoFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable("KEY_ID_TODO", toDo )
//                }
//            }
//        }
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_add_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btAddToDo.setOnClickListener {
            addToDo()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun addToDo() {
        val title = edtAddToDo.text.toString()

        context?.let {
            val sharePrefer = SharePrefer(it)
            val userId = sharePrefer.getLogin()
            val toDo = ToDo(id = 0, todoTitle = title, isDone = true, uid = userId)
            val db = NoteDatabase.newInstance(it)
            db?.toDoDao()?.insertTask(toDo)
            db?.toDoDao()?.getAllTask().let {
                Log.d("OOO", "" + it?.get(it.size - 1)?.todoTitle)
            }
        }
        when {
            title.isEmpty() -> {
                edtAddToDo.requestFocus()
                return
            }
        }
    }

}