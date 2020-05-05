package com.asiantech.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.ToDo
import kotlinx.android.synthetic.`at-quynhho`.recyclerview_item.view.*

class ItemToDoAdapter(private val toDo: MutableList<ToDo>) :
    RecyclerView.Adapter<ItemToDoAdapter.RecyclerViewHolder?>() {

    internal val onItemClick: (position: Int) -> Unit = {}
    internal var onItemEditClick: (position: Int) -> Unit = {}
    internal var onItemDeleteClick: (position: Int) -> Unit = {}
    internal var onItemCheckBoxClick: (position: Int) -> Unit = {}

    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemToDo = view.tvItemTodo
        val removeToDo = view.imgDelete
        val editToDo = view.imgEdit
        val likeToDo = view.imgYes

        init {
            view.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
            removeToDo.setOnClickListener {
                onItemDeleteClick.invoke(adapterPosition)
            }
            editToDo.setOnClickListener {
                onItemEditClick.invoke(adapterPosition)
            }
            likeToDo.setOnClickListener {
                onItemCheckBoxClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            toDo[adapterPosition].let {
                itemToDo.text = it.todoTitle
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = toDo.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bind()
    }


}