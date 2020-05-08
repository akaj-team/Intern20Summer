package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.data.ToDo
import com.asiantech.summer.data.User
import com.asiantech.summer.database.NoteDatabase
import com.asiantech.summer.recyclerview.ToDoAdapter
import kotlinx.android.synthetic.`at-quynhho`.fragment_to_do.*

class ToDoFragment : Fragment() {

    private var toDoAdapter: ToDoAdapter? = null
    private var db: NoteDatabase? = null
    private var toDoList = mutableListOf<ToDo>()
    private var user: User? = null

    companion object {
        fun newInstance(): ToDoFragment {
            return ToDoFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initData()
        toDoAdapter?.run {
            onItemCheckBoxClick = {
                toDoList.get(it).isDone = !(toDoList.get(it).isDone)
                //toDoList?.get(it)?.isDone = !(toDoList?.get(it)?.isDone ?: false)
                notifyItemChanged(it)
            }
            onItemEditClick = {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.flSignIn, EditToDoFragment.newInstance(toDoList.get(it)))
                    ?.commit()
            }
            onItemDeleteClick = {
                db?.toDoDao()?.deleteAll(toDoList[it]) //get tu cai id trong db ->delete
                toDoList.removeAt(it) //xoa id
                notifyDataSetChanged()
            }
        }
        imgAdd.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flSignIn, AddToDoFragment())?.addToBackStack(null)?.commit()
        }
    }

    fun initData() {
        rvTodo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            toDoAdapter = ToDoAdapter(toDoList)
            adapter = toDoAdapter

        }
    }

    fun loadData() {
        context?.let {
            val sharePrefer = SharePrefer(it)
            db = NoteDatabase.newInstance(it)
                toDoList.addAll(
                    db?.toDoDao()?.getAllTodoByUseId(sharePrefer.getLogin()) ?: mutableListOf()
                )
        }

        toDoAdapter?.notifyDataSetChanged()
    }
}



