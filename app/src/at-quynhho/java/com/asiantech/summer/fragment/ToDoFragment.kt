package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.fragment_to_do.*

class ToDoFragment : Fragment() {

//    private var toDoAdapter: ItemToDoAdapter? = null
//    private var db: NoteDatabase? = null
//    private var toDoList: MutableList<ToDo>? = null
//    private var user: User? = null
//    private var position: Int = 0
//
//    companion object {
//        private const val ARG_USER_ID = "userId"
//        private const val ARG_PREFERENCES = "MyPref"
//        private const val DEFAULT_VALUE = ""
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        db = NoteDatabase.newInstance(requireContext())
//        user = db?.userDao()?.loadAllById()

//        Log.d("AAA", "abc")
//        imgAdd.setOnClickListener {
//            Log.d("AAA", "abc")
//            childFragmentManager.beginTransaction().replace(R.id.flToDo, AddToDoFragment()).commit()
//        }
    }


//    private fun initAdapter() {
//        toDoList = user?.userId?.let {
//            Log.d("TAG11", "" + it)
//            db?.toDoDao()?.findByUserId(it) as MutableList<ToDo>?
//            //  mutableListOf()
//        }
//        toDoAdapter = toDoList?.let { ItemToDoAdapter(it) }
//        rvTodo.adapter = toDoAdapter
//        toDoAdapter?.onItemEditClick = {
//            position = it
//          //  showDialog(getString(R.string.edit_todo))
//        }
//        toDoAdapter?.onItemDeleteClick = {
//            deleteToDo(it)
//        }
//        toDoAdapter?.onItemCheckBoxClick = {
//            toDoList?.get(it)?.isDone = !toDoList?.get(it)?.isDone!!
//            toDoAdapter?.notifyDataSetChanged()
//        }
//
//    }
////
////    private fun initListener() {
////        imgAdd.setOnClickListener {
////            showDialog(getString(R.string.add_todo))
////        }
////    }
//
////    private fun showDialog(action: String) {
////        val editText = EditText(context)
////        editText.inputType = InputType.TYPE_CLASS_TEXT
////        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
////        dialogOption.setView(editText)
////        dialogOption.apply {
////            setTitle(action)
////            if (action == "Add To Do") {
////                setPositiveButton(android.R.string.ok) { _, _ ->
////                    if (editText.text.toString().isBlank()) {
////                        Toast.makeText(
////                            context,
////                            getString(R.string.toast_please_insert),
////                            Toast.LENGTH_SHORT
////                        ).show()
////                    } else {
////                        Toast.makeText(context, getString(R.string.toast_added), Toast.LENGTH_SHORT)
////                            .show()
////                        addToDo(editText.text.toString())
////                    }
////                }
////            } else {
////                editText.setText(toDoList?.get(position)?.todoTitle)
////                setPositiveButton(android.R.string.ok) { _, _ ->
////                    Toast.makeText(context, getString(R.string.toast_edited), Toast.LENGTH_SHORT)
////                        .show()
////                    toDoList?.get(position)?.id?.let { editToDo(it, editText.text.toString()) }
////                }
////            }
////
////            setNegativeButton(android.R.string.cancel) { _, _ ->
////                Toast.makeText(context, R.string.toast_cancel, Toast.LENGTH_SHORT).show()
////            }
////            show()
////        }
////    }
//
//
//    private fun editToDo(id: Int, title: String) {
//        db?.toDoDao()?.updateTask(id, title)
//        toDoList?.get(position)?.todoTitle = title
//        toDoAdapter?.notifyDataSetChanged()
//    }
//
//    private fun deleteToDo(id: Int) {
//        toDoList?.get(id)?.todoTitle?.let { db?.toDoDao()?.deleteAll() }
//        toDoList?.removeAt(id)
//        toDoAdapter?.notifyDataSetChanged()
//    }
//


}



