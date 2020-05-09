package com.asiantech.summer.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.R
import com.asiantech.summer.data.ToDo
import kotlinx.android.synthetic.`at-quynhho`.recyclerview_item.view.*

class ToDoAdapter(private val toDo: MutableList<ToDo>) : RecyclerView.Adapter<ToDoAdapter.RecyclerViewHolder?>() {

    internal val onItemClick: (position: Int) -> Unit = {} //goi 1 nut tai 1 vi vi do tra ve kieu do
    internal var onItemEditClick: (position: Int) -> Unit = {}
    internal var onItemDeleteClick: (position: Int) -> Unit = {}
    internal var onItemCheckBoxClick: (position: Int) -> Unit = {}

    inner class RecyclerViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemToDo: TextView? = null
        var removeToDo: ImageView? = null
        var editToDo: ImageView? = null
        var likeToDo: ImageView? = null

        init {
            itemToDo = itemView.tvItemTodo
            removeToDo = itemView.imgDelete
            editToDo = itemView.imgEdit
            likeToDo = itemView.imgYes
            itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition) //xet khi click nut do
            }
            removeToDo?.setOnClickListener {
                onItemDeleteClick.invoke(adapterPosition)
            }
            editToDo?.setOnClickListener {
                onItemEditClick.invoke(adapterPosition)
            }
            likeToDo?.setOnClickListener {
                onItemCheckBoxClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            toDo[adapterPosition].let {
                itemToDo?.text = it.todoTitle
                if (it.isDone){ //xet isDone true
                    likeToDo?.setImageResource(R.drawable.ic_false)
                }
                else{
                    likeToDo?.setImageResource(R.drawable.ic_true)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false))
    }

    override fun getItemCount(): Int = toDo.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        (holder as? RecyclerViewHolder)?.bind()
    }
}
