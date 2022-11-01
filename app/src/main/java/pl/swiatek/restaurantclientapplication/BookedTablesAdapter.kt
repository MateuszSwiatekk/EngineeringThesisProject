package pl.swiatek.restaurantclientapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookedTablesAdapter(private val tableList : ArrayList<BookedTable>) : RecyclerView.Adapter<BookedTablesAdapter.TablesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booked_item,
            parent,false)
        return TablesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TablesViewHolder, position: Int) {

        val currentItem = tableList[position]

        holder.table.text = currentItem.tableNumber
        if(currentItem.tableNumber.toInt()<=5) {
            holder.tableSize.text = "4"
        }else {
            holder.tableSize.text = "2"
        }
        holder.startDate.text = currentItem.startDate
        holder.endDate.text=currentItem.endDate
    }

    override fun getItemCount(): Int {
        return tableList.size
    }

    class TablesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val table : TextView = itemView.findViewById(R.id.tableTextView)
        val tableSize : TextView = itemView.findViewById(R.id.tableSizeTextView)
        val startDate : TextView = itemView.findViewById(R.id.startDateTextView)
        val endDate : TextView = itemView.findViewById(R.id.endDateTextView)
    }
}
