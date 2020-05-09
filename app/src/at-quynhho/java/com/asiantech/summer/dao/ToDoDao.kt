package com.asiantech.summer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.summer.data.ToDo

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    fun getAllTask(): List<ToDo>

    @Query("SELECT * FROM todo WHERE user_id=:useId")
    fun getAllTodoByUseId(useId: Int): List<ToDo>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun findById(id: Int): ToDo

    @Query("SELECT * FROM todo WHERE todoTitle = :title")
    fun findTitle(title: String): ToDo

    @Query("SELECT todo.user_id FROM todo WHERE user_id = :mUserId ")
    fun findByUserId(mUserId: Int): List<Int>?

    @Query("UPDATE todo SET todoTitle =:status WHERE id =:id")
    fun updateTask(id: Int, status: String)

    @Insert
    fun insertTask(todoTitle: ToDo)

    @Delete
    fun deleteAll(vararg toDo: ToDo)

}