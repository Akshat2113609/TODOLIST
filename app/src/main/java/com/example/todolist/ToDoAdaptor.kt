 import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import com.example.todolist.R
import com.example.todolist.ToDoModel
import com.example.todolist.UpdateandDelete

 class ToDoAdaptor(context: Context, toDoList:MutableList<ToDoModel>) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = toDoList
    private var updateandDelete: UpdateandDelete = context as UpdateandDelete
     override fun getCount(): Int {
         return itemList.size
     }

     override fun getItem(position: Int): Any {
         return itemList[position]
     }


         override fun getItemId(position: Int): Long {
             return 0L
         }



     override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val UID: String = itemList[p0].UID as String
        val itemTextData = itemList[p0].itemDataText as String
        val done: Boolean = itemList[p0].done as Boolean

        val view: View = inflater.inflate(R.layout.row_itemlayout, p2, false)
        val viewHolder: ListViewHolder = ListViewHolder(view)
        view.tag = viewHolder


        viewHolder.textLabel.setText(itemTextData)
        viewHolder.isDone.setChecked(done)
        viewHolder.isDone.setOnClickListener {
            updateandDelete.modifyItem(UID, !done)
        }
        viewHolder.isDeleted.setOnClickListener {
            updateandDelete.onItemDelete(UID)
        }
        return view
    }
}

class ListViewHolder(row:View?) {
    val textLabel: TextView = row!!.findViewById(R.id.item_textView) as TextView
    val isDone: CheckBox = row!!.findViewById(R.id.checkbox) as CheckBox
    val isDeleted: ImageButton = row!!.findViewById(R.id.close) as ImageButton

}

