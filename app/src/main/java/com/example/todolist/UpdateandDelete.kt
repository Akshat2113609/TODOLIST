package com.example.todolist

interface UpdateandDelete{
    fun modifyItem(itemUID: String,isDone:Boolean)
    fun onItemDelete(itemUID: String)


}