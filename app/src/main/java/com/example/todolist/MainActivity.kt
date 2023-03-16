package com.example.todolist

import ToDoAdaptor
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), UpdateandDelete {
    lateinit var database: DatabaseReference
    var toDOList:MutableList<ToDoModel>?=null
    lateinit var adapter: ToDoAdaptor
    private var listViewItem :ListView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
val fab=findViewById<View>(R.id.fab) as FloatingActionButton
        listViewItem=findViewById(R.id.item_listView) as ListView



     database=FirebaseDatabase.getInstance().reference


fab.setOnClickListener{
    val alertDialog=AlertDialog.Builder(this)
val textEditText = EditText(this)
    alertDialog.setMessage("Add the item")
    alertDialog.setTitle("Enter item")
    alertDialog.setView(textEditText)
    alertDialog.setPositiveButton("Add"){dialog,i ->
val todoItemData= ToDoModel.createList()
todoItemData.itemDataText=textEditText.text.toString()
        todoItemData.done=false

        val newItemData=database.child("todo").push()
        todoItemData.UID=newItemData.key

        newItemData.setValue(todoItemData)
        dialog.dismiss()
        Toast.makeText(this,"item saved!",Toast.LENGTH_LONG).show()


    }
    alertDialog.show()
}
this.toDOList = mutableListOf<ToDoModel>()
        adapter=ToDoAdaptor(this,toDOList!!)
        listViewItem!!.adapter=adapter
        database.addValueEventListener( object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"No item Added",Toast.LENGTH_LONG).show()
            }
            override fun onDataChange(snapshot: DataSnapshot) {
              toDOList!!.clear()
                addItemToList(snapshot)
            }


        })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun addItemToList(snapshot: DataSnapshot) {
        val items=snapshot.children.iterator()
        if(items.hasNext()){
val toDoIndexValue=items.next()
            val itemIterator=toDoIndexValue.children.iterator()
            while (itemIterator.hasNext()){
            }

val currentItem= itemIterator.next()
            val toDoItemData=ToDoModel.createList()
            val map=currentItem.getValue() as HashMap<String,Any>
toDoItemData.UID=currentItem.key
 toDoItemData.done=map.get("done") as Boolean?
  toDoItemData.itemDataText=map.get("itemDataText") as String?
            toDOList!!.add(toDoItemData)



            }
        adapter.notifyDataSetChanged()
    }

    override fun modifyItem(itemUID: String, isDone: Boolean) {
       val itemReference=database.child("todo").child(itemUID)
       itemReference.child("done").setValue(isDone)
    }

    override fun onItemDelete(itemUID: String) {
        val itemReference=database.child("todo").child(itemUID)
        itemReference.removeValue()
        adapter.notifyDataSetChanged()

    }

}